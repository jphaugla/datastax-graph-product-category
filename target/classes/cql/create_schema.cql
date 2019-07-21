create keyspace if not exists prodcat WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

create table if not exists prodcat.product(path text,
	product_id int,
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
	PRIMARY KEY(product_id)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
  AND compression = {'sstable_compression' : ''}
  AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'};


create table if not exists prodcat.supplier(
	id int,

	logoPic int,
    logoPicHeight int,
	logoPicWidth int,
	logoPicSize int,

	logoLowPic int,
    logoLowPicHeight int,
	logoLowPicWidth int,
	LogoLowSize int,

    logoMediumPic int,
    logoMediumPicHeight int,
	logoMediumPicWidth int,
	logoMediumPicSize int,

    logoHighPic int,
    logoHighPicHeight int,
	logoHighPicWidth int,
	logoHighPicSize int,

	logoOriginal text,
	logoOriginalSize int,

	name text,
	sponsor text
	PRIMARY KEY(id)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
  AND compression = {'sstable_compression' : ''}
  AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'};

create table if not exists prodcat.product_cross_sell(
	source_product_id int,
	cross_sell_product_id int,
	PRIMARY KEY(source_product_id, cross_sell_product_id)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
  AND compression = {'sstable_compression' : ''}
  AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'};