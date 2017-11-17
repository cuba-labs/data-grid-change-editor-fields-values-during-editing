-- alter table SAMPLE_ORDER_ITEM add column PRODUCT_ID varchar(36) ^
-- update SAMPLE_ORDER_ITEM set PRODUCT_ID = <default_value> ;
-- alter table SAMPLE_ORDER_ITEM alter column PRODUCT_ID set not null ;
alter table SAMPLE_ORDER_ITEM add column PRODUCT_ID varchar(36) not null ;
alter table SAMPLE_ORDER_ITEM drop column PRODUCT cascade ;
alter table SAMPLE_ORDER_ITEM drop column PRICE cascade ;
alter table SAMPLE_ORDER_ITEM drop column TOTAL cascade ;
