create keyspace if not exists prodcat WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

use prodcat;


create table if not exists product(path text,
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
	model_name int,
	product_view int,
	high_pic text,
	high_pic_size int,
	high_pic_width int,
	high_pic_height int,
	m_supplier_id int,
	m_supplier_name text, 
	ean_upc_is_approved list<text>,
	Limited text,
	Date_Added timestamp,
	PRIMARY KEY(product_id)
) WITH compaction= { 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy' }
  AND compression = {'sstable_compression' : ''}
  AND caching = {'keys':'NONE', 'rows_per_partition':'NONE'};
