import datetime
from datetime import time

from dse.cluster import GraphExecutionProfile, Cluster, EXEC_PROFILE_GRAPH_DEFAULT
from dse.graph.query import GraphOptions, GraphProtocol
from flask import Flask, escape, request, render_template, jsonify

from interaction import Interaction
from rest_model import process_model

ep = GraphExecutionProfile(graph_options=GraphOptions(graph_name="prodcat", graph_protocol=GraphProtocol.GRAPHSON_3_0))
cluster = Cluster(execution_profiles={EXEC_PROFILE_GRAPH_DEFAULT: ep})
session = cluster.connect()

app = Flask(__name__, static_url_path="", static_folder="web/public", template_folder="web/public")

def main():
    app.run(debug=True)

@app.route("/")
def index():
    return render_template("index.html")

@app.route("/product")
def product():
    prod_id = request.args.get('prod_id')
    model_name = request.args.get('model_name')
    high_pic = request.args.get('high_pic')


    results = session.execute_graph(
        'g.V().has("product", "prod_id", prod_id).out("product_cross_sell").limit(10).project("label", "high_pic", "model_name", "prod_id").by(label()).by(values("high_pic")).by(values("model_name")).by(values("prod_id"))',
        {'prod_id': prod_id}
    )

    vertices = {
        prod_id: prod_id
    }
    edges = {}

    graph = {
        "nodes": [{
            "id": prod_id,
            "label": "product",
            "high_pic": high_pic,
            "model_name": model_name
        }],
        "edges": []
    }

    for row in results:
        if row["prod_id"] not in vertices:
            vertices[row["prod_id"]] = row["prod_id"]

            graph["nodes"].append({
                "id": row["prod_id"],
                "label": row["label"],
                "high_pic": row["high_pic"],
                "model_name": row["model_name"]
            })
        if row["prod_id"] + prod_id not in edges:
            edges[row["prod_id"] + prod_id] = row["prod_id"] + prod_id

            graph["edges"].append({
                "id": row["prod_id"] + prod_id,
                "source": prod_id,
                "target": row["prod_id"],
                "label": "cross_sell"
            })

    return jsonify(graph)

@app.route("/eventGraph")
def event_graph():
    event = {
        "graph": {
            "nodes": [],
            "edges": []
        },
        "prediction_time": 0,
        "recommendation": -1,
        "recommends": False,

    }


    latest_events = session.execute(
        "SELECT * FROM prodcat.prediction WHERE user_id=%s LIMIT 1",
        (0, )
    )
    if len(latest_events.current_rows) > 0:
        products = {
            1: "PARENT-ROLL",
        }
        prod_id = products[latest_events.current_rows[0].recommendation]

        results = session.execute_graph(
            'g.V().has("product", "prod_id", prod_id).out("product_cross_sell").limit(10).project("label", "high_pic", "model_name", "prod_id").by(label()).by(values("high_pic")).by(values("model_name")).by(values("prod_id"))',
            {'prod_id': prod_id}
        )

        vertices = {
            prod_id: prod_id
        }
        edges = {}

        graph = {
            "nodes": [{
                "id": prod_id,
                "label": "product",
            }],
            "edges": []
        }

        for row in results:
            if row["prod_id"] not in vertices:
                vertices[row["prod_id"]] = row["prod_id"]

                graph["nodes"].append({
                    "id": row["prod_id"],
                    "label": row["model_name"],
                    "high_pic": row["high_pic"],
                    "model_name": row["model_name"]
                })
            if row["prod_id"] + prod_id not in edges:
                edges[row["prod_id"] + prod_id] = row["prod_id"] + prod_id

                graph["edges"].append({
                    "id": row["prod_id"] + prod_id,
                    "source": prod_id,
                    "target": row["prod_id"],
                    "label": "cross_sell"
                })
        event["graph"] = graph
        event["prediction_time"] = latest_events.current_rows[0].prediction_time
        event["recommendation"] = prod_id
        event["recommends"] = latest_events.current_rows[0].recommend

    return jsonify(event)


@app.route("/search")
def search():
    query = request.args.get('query')

    results = session.execute("SELECT * FROM prodcat.product WHERE model_name LIKE %s LIMIT 5", [query + "%"])
    searchResults = []

    for row in results:
        searchResults.append({
            "name": row.model_name,
            "prod_id": row.prod_id,
            "model_name": row.model_name,
            "high_pic": row.high_pic
        })

    return jsonify(searchResults)

@app.route("/trainingData")
def training_data():
    rows = session.execute("SELECT version, epoch, loss FROM prodcat.model WHERE version='0.1'")

    results = []
    for row in rows:
        results.append({
            "amt": row.loss,
            "pv": row.epoch,
            "uv": row.loss
        })

    return jsonify(results)

@app.route("/model/evaluate")
def evaluate_model():
    prod_id = request.args.get('prod_id')
    state = request.args.get('state')

    start_time = datetime.datetime.fromtimestamp(1564524000)
    now = datetime.datetime.now()

    session.execute(
        """
        INSERT INTO prodcat.user_interaction_product (user_id, product_id, interaction_time, type)
        VALUES (%s,%s,%s,%s)
        """,
        (0, prod_id, now, state)
    )

    session.execute(
        """
        INSERT INTO prodcat.user_interaction_product_history (year, month, day, hour, user_id, product_id, interaction_time, type)
        VALUES (%s,%s,%s,%s,%s,%s,%s,%s)
        """,
        (now.year, now.month, now.day, now.hour, 0, prod_id, now, state)
    )


    results = session.execute_graph(
        """
        g.V()
            .has('user', 'user_id', user_id)
            .sideEffect(out()
                .dedup()
                .filter(values('prod_id').is(prod_id))
                .out()
                .store('related_products'))
            .barrier()
            .outE()
                .where(inV().has('prod_id', within(['TOIL-1', 'TOIL-2', 'TOIL-3', 'PARENT-ROLL'])))
            .order()
                .by('interaction_time', decr)
            .project('interaction_time', 'state')
                .by(values('interaction_time'))
                .by(values('type'))
        """,
        {'prod_id': prod_id, 'user_id': 0}
    )



    interaction_count = 0

    for result in results:
        if not result["state"]:
            interaction_count += 1


    interaction = Interaction(
        0,
        0,
        prod_id,
        0,
        now,
        (now - start_time).seconds,
        int(state),
        [interaction_count]
    )

    predictions = process_model(interaction)

    session.execute(
        """
        INSERT INTO prodcat.prediction (user_id, prediction_time, recommendation, recommend)
        VALUES (%s, %s, %s, %s)
        """,
        (0, now, int(predictions[0]), bool(predictions[1]))
    )


    return jsonify({
      "recommendation": predictions[0],
      "recommend": predictions[1]
    })

main()