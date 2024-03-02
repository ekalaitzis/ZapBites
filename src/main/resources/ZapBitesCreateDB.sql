---------------------------------------
--  creation of the Database Tables  --
---------------------------------------

DROP TABLE IF EXISTS order_product;
DROP TABLE IF EXISTS order_status;
DROP TABLE IF EXISTS "order";
DROP TABLE IF EXISTS customer_address;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS ingredient;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS business_schedule;
DROP TABLE IF EXISTS business;

DROP TYPE IF EXISTS order_status_enum;


CREATE TABLE business (
    id                  SERIAL          PRIMARY KEY,
    company_name        VARCHAR(255)    NOT NULL,
    email               VARCHAR(255)    NOT NULL,
    password            VARCHAR(255)    NOT NULL,
    telephone           VARCHAR(15)    NOT NULL,
    tax_id_number       VARCHAR(255)    NOT NULL
);
comment on table business is 'This is the business';
comment on column business.company_name is 'The name of the business ex. Flavors of Curry';
comment on column business.email is 'The email of the business Eg. flavor@curry.al';
comment on column business.password is 'The password of the business account on ZapBites';
comment on column business.telephone is 'The telephone of the business Eg.1234567890';
comment on column business.tax_id_number is 'The unique tax identification number of the business';

CREATE TABLE business_schedule (
    id                  SERIAL          PRIMARY KEY,
    weekday             VARCHAR(255)    NOT NULL,
    opening             time            NOT NULL,
    closing             time            NOT NULL,
    business_id         INTEGER         NOT NULL,
        CONSTRAINT fk_business_schedule
        FOREIGN KEY(business_id)
        REFERENCES business(id)
        ON DELETE CASCADE
);
comment on table business_schedule is ' this is the business schedule';
comment on column business_schedule.opening is ' this is the opening business schedule';
comment on column business_schedule.closing is ' this is the closing business schedule';

CREATE TABLE menu (
    id                  SERIAL          PRIMARY KEY,
    name                VARCHAR(255)    NOT NULL,
    business_id         INTEGER         NOT NULL,
        CONSTRAINT fk_business_menu
        FOREIGN KEY(business_id)
        REFERENCES business(id)
        ON DELETE CASCADE
);
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
comment on table product is 'The product of each category';
comment on column product.name is 'The name of the product Eg spicy burger';
comment on column product.description is 'A short description of the product';
comment on column product.category_id is 'The category that the product belongs to';

CREATE TABLE ingredient (
    id                  SERIAL          PRIMARY KEY,
    name                VARCHAR(255)    NOT NULL,
    vegan               BOOLEAN,
    spicy               BOOLEAN,
    gluten_free         BOOLEAN,
    product_id          INTEGER         NOT NULL,
        CONSTRAINT fk_product_ingredient
        FOREIGN KEY(product_id)
        REFERENCES product(id)
        ON DELETE CASCADE
);
comment on table ingredient is 'One of the ingredients of the product';
comment on column ingredient.name is 'The name of the ingredient';
comment on column ingredient.vegan is 'If every ingerdient is vegan, then the product is vegan';
comment on column ingredient.spicy is 'if one ingredient on the product is spicy, then the product is spicy';
comment on column ingredient.gluten_free is 'If every ingerdient is gluten free, then the product is gluten free';
comment on column ingredient.product_id is 'The id of the product that the ingredient belongs to';

CREATE TABLE product_ingredient (
    id            SERIAL          PRIMARY KEY,
    product_id    INTEGER         NOT NULL,
    ingredient_id INTEGER         NOT NULL,
    CONSTRAINT fk_product_ingredient_product
        FOREIGN KEY(product_id)
        REFERENCES product(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_product_ingredient_ingredient
        FOREIGN KEY(ingredient_id)
        REFERENCES ingredient(id)
        ON DELETE CASCADE
);
COMMENT ON TABLE product_ingredient IS 'The association table for the many-to-many relationship between products and ingredients.';
COMMENT ON COLUMN product_ingredient.product_id IS 'The ID of the product in the relationship.';
COMMENT ON COLUMN product_ingredient.ingredient_id IS 'The ID of the ingredient in the relationship.';


CREATE TABLE customer (
    id                  SERIAL          PRIMARY KEY,
    first_name          VARCHAR(255)    NOT NULL,
    last_name           VARCHAR(255)    NOT NULL,
    email               VARCHAR(255)    NOT NULL,
    password            VARCHAR(255)    NOT NULL,
    telephone           VARCHAR(15)     NOT NULL
);
comment on table customer is 'The user of the ZapBites application';
comment on column customer.first_name is 'The first name of the user';
comment on column customer.last_name is 'The last name of the user';
comment on column customer.email is 'The email of the user';
comment on column customer.password is 'The password of the user account on ZapBites';
comment on column customer.telephone is 'The telephone of the user Eg.1234567890';

CREATE TABLE customer_address (
    id                  SERIAL          PRIMARY KEY,
    address             VARCHAR(65535)  NOT NULL,
    geolocation         POINT           NOT NULL,
    customer_id         INTEGER         NOT NULL,
        CONSTRAINT fk_customer
        FOREIGN KEY(customer_id)
        REFERENCES customer(id)
        ON DELETE CASCADE
);
comment on table customer_address is 'The address of the user. It is possible for the user to have multiple addresses';
comment on column customer_address.address is 'The address info of the user';
comment on column customer_address.geolocation is 'The coordinates of the address location';
comment on column customer_address.customer_id is 'The id of the customer that the address belongs to';



CREATE TABLE "order" (
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
comment on table "order" is 'The "order" details when a new "order" is being made';
comment on column "order".business_id is 'The id of the business about the "order"';
comment on column "order".customer_id is 'The id of the customer about the "order"';
comment on column "order".customer_address_id is 'The address of the customer in the "order"';
comment on column "order".total_price is 'The total price of the "order"';
comment on column "order".created_at is 'the timestamp of the "order"';


CREATE TABLE order_product (
    id                  SERIAL          PRIMARY KEY,
    order_id            INTEGER         NOT NULL,
    quantity            INTEGER         DEFAULT 1,
    product_id          INTEGER         NOT NULL,
        CONSTRAINT fk_order
        FOREIGN KEY(order_id)
        REFERENCES "order"(id)
        ON DELETE CASCADE,
        CONSTRAINT fk_product
        FOREIGN KEY(product_id)
        REFERENCES product(id)
        ON DELETE CASCADE
);
comment on table order_product is 'The product details when a new "order" is being made';
comment on column order_product.order_id is 'The id of the "order"';
comment on column order_product.quantity is 'the quantity of product in the "order"';
comment on column order_product.product_id is 'The details of the product in the "order"';

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
    status           order_status_enum  DEFAULT 'CART',
    session          VARCHAR(255)       NOT NULL,
    status_changed_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT fk_order_status
        FOREIGN KEY(order_id)
        REFERENCES "order"(id)
        ON DELETE CASCADE
);
comment on table order_status is 'The status details when a new "order" is being made';
comment on column order_status.order_id is 'The id of the "order"';
comment on column order_status.status_changed_at is 'The timestamp of each status in the "order"';
comment on column order_status.status is 'The details of the status in the "order"';
comment on column order_status.session is 'A session identifier for the order status';

