CREATE TABLE IF NOT EXISTS vehicles (
    id                  uuid                  primary key             default uuid_generate_v4()        ,
    brand               varchar(255)          not null                                                  ,
    model               varchar(255)          not null                                                  ,
    color               varchar(255)          not null                                                  ,
    price               numeric(15,2)         not null                                                  ,
    status              varchar(255)          not null                                                  ,
    vehicle_year        integer               not null                                                  ,
    created_at          timestamp                                                                       ,
    updated_at          timestamp
);