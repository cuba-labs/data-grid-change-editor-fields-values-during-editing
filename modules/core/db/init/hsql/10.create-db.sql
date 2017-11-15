-- begin SAMPLE_ORDER_ITEM
create table SAMPLE_ORDER_ITEM (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRODUCT varchar(255) not null,
    PRICE decimal(19, 2) not null,
    QUANTITY integer not null,
    TOTAL decimal(19, 2),
    --
    primary key (ID)
)^
-- end SAMPLE_ORDER_ITEM
