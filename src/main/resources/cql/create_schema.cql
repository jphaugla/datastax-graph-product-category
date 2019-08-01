CREATE KEYSPACE prodcat WITH replication = {'class': 'NetworkTopologyStrategy', 'SearchGraphAnalytics': '1'}  AND durable_writes = true AND graph_engine = 'Core';

create table if not exists prodcat.user(
   user_id int,
   PRIMARY KEY(user_id)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
      AND compression = {'sstable_compression' : ''}
      AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'}
      AND VERTEX LABEL user;

create table if not exists prodcat.model(
    version text,
    epoch int,
    loss double,
    model blob,
    PRIMARY KEY(version, epoch)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
      AND compression = {'sstable_compression' : ''}
      AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'};

create table if not exists prodcat.prediction(
    user_id int,
    prediction_time timestamp,
    recommendation int,
    recommend boolean,
    PRIMARY KEY(user_id, prediction_time)
) WITH CLUSTERING ORDER BY (prediction_time DESC)
      AND compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
      AND compression = {'sstable_compression' : ''}
      AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'};

create table if not exists prodcat.product(
  path text,
  product_id text,
  updated timestamp,
  quality text,
  supplier_id int,
  prod_id text,
  catid int,
  m_prod_id text,
  ean_upc list<text>,
  on_market int,
  country_market list<text>,
  model_name text,
  product_view int,
  high_pic text,
  high_pic_size int,
  high_pic_width int,
  high_pic_height int,
  m_supplier_id int,
  m_supplier_name text,
  ean_upc_is_approved list<text>,
  limited text,
  date_added timestamp,
  PRIMARY KEY(prod_id)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
      AND compression = {'sstable_compression' : ''}
      AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'}
      AND VERTEX LABEL product;


create table if not exists prodcat.supplier(
   id int,

   logoPic text,
   logoPicHeight int,
   logoPicWidth int,
   logoPicSize int,

   logoLowPic text,
   logoLowPicHeight int,
   logoLowPicWidth int,
   LogoLowSize int,

   logoMediumPic text,
   logoMediumPicHeight int,
   logoMediumPicWidth int,
   logoMediumPicSize int,

   logoHighPic text,
   logoHighPicHeight int,
   logoHighPicWidth int,
   logoHighPicSize int,

   logoOriginal text,
   logoOriginalSize int,

   name text,
   sponsor text,
   PRIMARY KEY(id)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
      AND compression = {'sstable_compression' : ''}
      AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'}
      AND VERTEX LABEL supplier;

create table if not exists prodcat.product_cross_sell(
    source_product_id text,
    cross_sell_product_id text,
    PRIMARY KEY(source_product_id, cross_sell_product_id)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
      AND compression = {'sstable_compression' : ''}
      AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'}
      AND EDGE LABEL product_cross_sell FROM product(source_product_id) TO product(cross_sell_product_id);

create table if not exists prodcat.user_interaction_product(
   user_id int,
   product_id text,
   interaction_time timestamp,
   type text,
   PRIMARY KEY((user_id), product_id, interaction_time)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
      AND compression = {'sstable_compression' : ''}
      AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'}
      AND EDGE LABEL user_interaction_product FROM user(user_id) TO product(product_id);

create table if not exists prodcat.user_interaction_product_history(
   user_id int,
   product_id text,
   year int,
   month int,
   day int,
   hour int,
   interaction_time timestamp,
   type text,
   PRIMARY KEY((year, month, day, hour), user_id, product_id, interaction_time)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
      AND compression = {'sstable_compression' : ''}
      AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'};


CREATE SEARCH INDEX ON prodcat.product WITH COLUMNS model_name;