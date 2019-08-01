import random, datetime

from dse.cluster import Cluster

cluster = Cluster()
session = cluster.connect()

start_time = datetime.datetime.fromtimestamp(1564524000)
end_time = datetime.datetime.fromtimestamp(15646104000)

bucket_size = 60

product_ids = ["PARENT-ROLL","TOIL-1","TOIL-2","TOIL-3"]

current_time = start_time
while current_time <= end_time:
    interaction_count = 1
    product_count = 0
    for i in range(0, 60):
        time = current_time + datetime.timedelta(minutes=i)
        session.execute(
            """
            INSERT INTO prodcat.user_interaction_product_history (user_id, product_id, year, month, day, hour, interaction_time, type)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
            """,
            (0, product_ids[product_count], time.year, time.month, time.day, time.hour, time, str(interaction_count % 3 == 1))
        )

        session.execute(
            """
            INSERT INTO prodcat.user_interaction_product (user_id, product_id, interaction_time, type)
            VALUES (%s, %s, %s, %s)
            """,
            (0, product_ids[product_count], time, str(interaction_count % 3 == 1))
        )

        if interaction_count < 3:
            interaction_count += 1
        else:
            interaction_count = 1

            if product_count < 3:
                product_count += 1
            else:
                product_count = 0



    current_time += datetime.timedelta(hours=1)
