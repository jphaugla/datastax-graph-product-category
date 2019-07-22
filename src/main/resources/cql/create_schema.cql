create keyspace if not exists prodcat WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

create table if not exists prodcat.product(path text,
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