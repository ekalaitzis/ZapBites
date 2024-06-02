---------------------------------------
--  creation of the Database Tables  --
---------------------------------------
DROP TABLE IF EXISTS order_product CASCADE;
DROP TABLE IF EXISTS order_status CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS customer_address CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS product_ingredient CASCADE;
DROP TABLE IF EXISTS ingredient CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS menu CASCADE;
DROP TABLE IF EXISTS business_schedule CASCADE;
DROP TABLE IF EXISTS business CASCADE;
DROP TABLE IF EXISTS role CASCADE;




DROP TYPE IF EXISTS order_status_enum;
DROP TYPE IF EXISTS day_of_week_enum;


DROP SEQUENCE IF EXISTS business_seq;
DROP SEQUENCE IF EXISTS business_schedule_seq;
DROP SEQUENCE IF EXISTS category_seq;
DROP SEQUENCE IF EXISTS menu_seq;
DROP SEQUENCE IF EXISTS ingredient_seq;
DROP SEQUENCE IF EXISTS customer_address_seq;
DROP SEQUENCE IF EXISTS order_product_seq;
DROP SEQUENCE IF EXISTS order_status_seq;
DROP SEQUENCE IF EXISTS customer_seq;
DROP SEQUENCE IF EXISTS order_seq;
DROP SEQUENCE IF EXISTS product_seq;
DROP SEQUENCE IF EXISTS role_seq;



CREATE TABLE role (
                      id              SERIAL               PRIMARY KEY,
                      name            VARCHAR(50)          NOT NULL UNIQUE
);

CREATE SEQUENCE role_seq;



CREATE TABLE business (
                          id                  SERIAL          PRIMARY KEY,
                          company_name        VARCHAR(255)    NOT NULL,
                          email               VARCHAR(255)    NOT NULL UNIQUE,
                          password            VARCHAR(255)    NOT NULL,
                          telephone           VARCHAR(15)     NOT NULL,
                          tax_id_number       VARCHAR(255)    NOT NULL,
                          role                VARCHAR(25)     NOT NULL DEFAULT 'BUSINESS'
);

CREATE SEQUENCE business_seq;


comment on table business is 'This is the business';
comment on column business.company_name is 'The name of the business ex. Flavors of Curry';
comment on column business.email is 'The email of the business Eg. flavor@curry.al';
comment on column business.password is 'The password of the business account on ZapBites';
comment on column business.telephone is 'The telephone of the business Eg.1234567890';
comment on column business.tax_id_number is 'The unique tax identification number of the business';



CREATE TYPE day_of_week_enum AS ENUM (
    'SUNDAY',
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY'
    );

CREATE TABLE business_schedule (
                                   id          SERIAL          PRIMARY KEY,
                                   weekday     VARCHAR(10)     NOT NULL,
                                   opening     TIME,
                                   closing     TIME,
                                   business_id INTEGER         NOT NULL,
                                   CONSTRAINT fk_business_schedule_business_id
                                       FOREIGN KEY(business_id)
                                           REFERENCES business(id)
                                           ON DELETE CASCADE
);


CREATE SEQUENCE business_schedule_seq;


COMMENT ON TABLE business_schedule IS 'The business schedule for each day.';
COMMENT ON COLUMN business_schedule.weekday IS 'The day of the week.';
COMMENT ON COLUMN business_schedule.opening IS 'The opening time of the business for the specific day.';
COMMENT ON COLUMN business_schedule.closing IS 'The closing time of the business for the specific day.';
COMMENT ON COLUMN business_schedule.business_id IS 'The ID of the business associated with the schedule.';

CREATE TABLE menu (
                      id                  SERIAL          PRIMARY KEY,
                      name                VARCHAR(255)    NOT NULL,
                      business_id         INTEGER         NOT NULL,
                      CONSTRAINT fk_business_menu
                          FOREIGN KEY(business_id)
                              REFERENCES business(id)
                              ON DELETE CASCADE
);
CREATE SEQUENCE menu_seq;

comment on table menu is 'The menu of the restaurant';
comment on column menu.name is ' This is the name of the menu. Eg flavor of curry-2023';
comment on column menu.business_id is 'The business_id that the menu is made for';

CREATE TABLE category (
                          id                  SERIAL          PRIMARY KEY,
                          name                VARCHAR(255)    NOT NULL,
                          menu_id             INTEGER         NOT NULL,
                          CONSTRAINT fk_menu
                              FOREIGN KEY(menu_id)
                                  REFERENCES menu(id)
                                  ON DELETE CASCADE
);

CREATE SEQUENCE category_seq;

comment on table category is 'The category of the menu.';
comment on column category.name is 'The name of the category Eg starters, main etc';
comment on column category.menu_id is 'The menu id that the category belongs to';

CREATE TABLE product (
                         id                  SERIAL          PRIMARY KEY,
                         name                VARCHAR(255)    NOT NULL,
                         description         TEXT            NOT NULL,
                         price               NUMERIC(10, 2)  NOT NULL,
                         category_id         INTEGER         NOT NULL,
                         CONSTRAINT fk_category
                             FOREIGN KEY(category_id)
                                 REFERENCES category(id)
                                 ON DELETE CASCADE
);

CREATE SEQUENCE product_seq;

comment on table product is 'The product of each category';
comment on column product.name is 'The name of the product Eg spicy burger';
comment on column product.description is 'A short description of the product';
comment on column product.category_id is 'The category that the product belongs to';

CREATE TABLE ingredient (
                            id                  SERIAL          PRIMARY KEY,
                            name                VARCHAR(255)    NOT NULL,
                            vegan               BOOLEAN,
                            spicy               BOOLEAN,
                            gluten_free         BOOLEAN
);

CREATE SEQUENCE ingredient_seq;

comment on table ingredient is 'One of the ingredients of the product';
comment on column ingredient.name is 'The name of the ingredient';
comment on column ingredient.vegan is 'If every ingredient is vegan, then the product is vegan';
comment on column ingredient.spicy is 'if one ingredient on the product is spicy, then the product is spicy';
comment on column ingredient.gluten_free is 'If every ingredient is gluten free, then the product is gluten free';

CREATE TABLE product_ingredient (
                                    id            SERIAL          PRIMARY KEY,
                                    product_id    INTEGER         NOT NULL,
                                    ingredient_id INTEGER         NOT NULL,
                                    CONSTRAINT fk_product_ingredient_ingredient
                                        FOREIGN KEY(ingredient_id)
                                            REFERENCES ingredient(id)
                                            ON DELETE CASCADE
);
COMMENT ON TABLE product_ingredient IS 'The association table for the many-to-many relationship between products and ingredients.';
COMMENT ON COLUMN product_ingredient.ingredient_id IS 'The ID of the ingredient in the relationship.';


CREATE TABLE customer (
                          id                  SERIAL          PRIMARY KEY,
                          first_name          VARCHAR(255)    NOT NULL,
                          last_name           VARCHAR(255)    NOT NULL,
                          email               VARCHAR(255)    NOT NULL UNIQUE,
                          password            VARCHAR(255)    NOT NULL,
                          telephone           VARCHAR(15)     NOT NULL,
                          role                VARCHAR(25)     NOT NULL DEFAULT 'CUSTOMER'
);

CREATE SEQUENCE customer_seq;

comment on table customer is 'The user of the ZapBites application';
comment on column customer.first_name is 'The first name of the user';
comment on column customer.last_name is 'The last name of the user';
comment on column customer.email is 'The email of the user';
comment on column customer.password is 'The password of the user account on ZapBites';
comment on column customer.telephone is 'The telephone of the user Eg.1234567890';

CREATE TABLE customer_address (
                                  id                  SERIAL          PRIMARY KEY,
                                  address             VARCHAR(65535)  NOT NULL,
                                  geolocation         VARCHAR         NOT NULL,
                                  primary_address     BOOLEAN         NOT NULL,
                                  customer_id         INTEGER         NOT NULL,
                                  CONSTRAINT fk_customer
                                      FOREIGN KEY(customer_id)
                                          REFERENCES customer(id)
                                          ON DELETE CASCADE
);

CREATE SEQUENCE customer_address_seq;

comment on table customer_address is 'The address of the user. It is possible for the user to have multiple addresses';
comment on column customer_address.address is 'The address info of the user';
comment on column customer_address.geolocation is 'The coordinates of the address location';
comment on column customer_address.customer_id is 'The id of the customer that the address belongs to';



CREATE TABLE orders (
                        id                  SERIAL          PRIMARY KEY,
                        business_id         INTEGER         NOT NULL,
                        customer_id         INTEGER         NOT NULL,
                        customer_address_id INTEGER         NOT NULL,
                        total_price         NUMERIC(10, 2)  DEFAULT 0,
                        created_at          TIMESTAMP       NOT NULL DEFAULT now(),
                        CONSTRAINT fk_business_order_id
                            FOREIGN KEY(business_id)
                                REFERENCES business(id)
                                ON DELETE CASCADE,
                        CONSTRAINT fk_customer_order_id
                            FOREIGN KEY(customer_id)
                                REFERENCES customer(id)
                                ON DELETE CASCADE,
                        CONSTRAINT fk_customer_address
                            FOREIGN KEY(customer_address_id)
                                REFERENCES customer_address(id)
                                ON DELETE CASCADE
);

CREATE SEQUENCE order_seq;

comment on table orders is 'The "orders" details when a new "orders" is being made';
comment on column orders.business_id is 'The id of the business about the "orders"';
comment on column orders.customer_id is 'The id of the customer about the "orders"';
comment on column orders.customer_address_id is 'The address of the customer in the "orders"';
comment on column orders.total_price is 'The total price of the "orders"';
comment on column orders.created_at is 'the timestamp of the "orders"';


CREATE TABLE order_product (
                               id                  SERIAL          PRIMARY KEY,
                               order_id            INTEGER         NOT NULL,
                               quantity            INTEGER         DEFAULT 1,
                               product_id          INTEGER         NOT NULL,
                               CONSTRAINT fk_order
                                   FOREIGN KEY(order_id)
                                       REFERENCES orders(id)
                                       ON DELETE CASCADE,
                               CONSTRAINT fk_product
                                   FOREIGN KEY(product_id)
                                       REFERENCES product(id)
                                       ON DELETE CASCADE
);

CREATE SEQUENCE order_product_seq;

comment on table order_product is 'The product details when a new "orders" is being made';
comment on column order_product.order_id is 'The id of the "orders"';
comment on column order_product.quantity is 'the quantity of product in the "orders"';
comment on column order_product.product_id is 'The details of the product in the "orders"';

CREATE TYPE order_status_enum AS ENUM (
    'CART',
    'PROCESSING_ORDER',
    'ACCEPTED',
    'COOKING',
    'PREPARING',
    'DELIVERY',
    'DELIVERED',
    'DECLINED_BY_BUSINESS',
    'CANCELLED_BY_CUSTOMER',
    'REFUNDED'
    );

CREATE TABLE order_status (
                              id               SERIAL             PRIMARY KEY,
                              order_id         INTEGER            NOT NULL,
                              status           VARCHAR            DEFAULT 'CART',
                              session          VARCHAR(255)       NOT NULL,
                              status_changed_at TIMESTAMP NOT NULL DEFAULT now(),
                              CONSTRAINT fk_order_status
                                  FOREIGN KEY(order_id)
                                      REFERENCES orders(id)
                                      ON DELETE CASCADE
);

CREATE SEQUENCE order_status_seq;

comment on table order_status is 'The status details when a new "order" is being made';
comment on column order_status.order_id is 'The id of the "order"';
comment on column order_status.status_changed_at is 'The timestamp of each status in the "order"';
comment on column order_status.status is 'The details of the status in the "order"';
comment on column order_status.session is 'A session identifier for the order status';

