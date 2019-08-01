INSERT INTO prodcat.product (product_id, prod_id, model_name, high_pic) VALUES ('PARENT-ROLL', 'PARENT-ROLL', 'Parent Toilet Paper', 'https://i.redd.it/w40rzb7d0wqy.jpg');
INSERT INTO prodcat.product (product_id, prod_id, model_name, high_pic) VALUES ('TOIL-1', 'TOIL-1', 'Toilet Paper 1', 'https://i5.walmartimages.com/asr/24a0deee-674f-46b0-89a5-47b46f3e752c_1.b70fa5d73b3d7db172ca189f2acbdc7b.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF');
INSERT INTO prodcat.product (product_id, prod_id, model_name, high_pic) VALUES ('TOIL-2', 'TOIL-2', 'Toilet Paper 2', 'https://images-na.ssl-images-amazon.com/images/I/71-XE5LHlxL._SL1500_.jpg');
INSERT INTO prodcat.product (product_id, prod_id, model_name, high_pic) VALUES ('TOIL-3', 'TOIL-3', 'Toilet Paper 3', 'https://images-na.ssl-images-amazon.com/images/I/81eRYuHk2EL._SY355_.jpg');

INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('PARENT-ROLL', 'TOIL-1');
INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('PARENT-ROLL', 'TOIL-2');
INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('PARENT-ROLL', 'TOIL-3');

INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('TOIL-1', 'PARENT-ROLL');
INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('TOIL-1', 'TOIL-2');
INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('TOIL-1', 'TOIL-3');

INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('TOIL-2', 'TOIL-1');
INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('TOIL-2', 'PARENT-ROLL');
INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('TOIL-2', 'TOIL-3');

INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('TOIL-3', 'TOIL-1');
INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('TOIL-3', 'TOIL-2');
INSERT INTO prodcat.product_cross_sell(source_product_id, cross_sell_product_id) VALUES ('TOIL-3', 'PARENT-ROLL');
