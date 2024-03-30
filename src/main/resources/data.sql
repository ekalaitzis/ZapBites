-- Dummy data for business
INSERT INTO business (id, company_name, email, password, telephone, tax_id_number)
VALUES
    (1, 'Flavors of Curry', 'flavor@curry.al', 'password1', '1234567890', 'TAX123'),
    (2, 'Crispy Bites', 'crispy@bites.com', 'password2', '9876543210', 'TAX456'),
    (3, 'Green Delight', 'green@delight.com', 'password3', '5556667777', 'TAX789'),
    (4, 'Vegan Vibes', 'vegan@vibes.com', 'password4', '1112223333', 'TAX999'),
    (5, 'Sizzling Grill', 'sizzle@grill.com', 'password5', '9998887777', 'TAX000'),
    (6, 'Taste of Mexico', 'taste@mexico.com', 'password6', '3332221111', 'TAX111'),
    (7, 'Asian Fusion', 'asian@fusion.com', 'password7', '4445556666', 'TAX222'),
    (8, 'Mediterranean Delights', 'med@delights.com', 'password8', '7778889999', 'TAX333'),
    (9, 'Burger Haven', 'burger@haven.com', 'password9', '6667778888', 'TAX444'),
    (10, 'Pizza Paradise', 'pizza@paradise.com', 'password10', '2223334444', 'TAX555');

-- Dummy data for business_schedule
INSERT INTO business_schedule (id, weekday, opening, closing, business_id)
VALUES
    (1, 'MONDAY', '08:00:00', '20:00:00', 1),
    (2, 'TUESDAY', '08:00:00', '20:00:00', 1),
    (3, 'WEDNESDAY', '08:00:00', '20:00:00', 1),
    (4, 'THURSDAY', '08:00:00', '20:00:00', 1),
    (5, 'FRIDAY', '08:00:00', '22:00:00', 1),
    (6, 'SATURDAY', '10:00:00', '22:00:00', 1),
    (7, 'SUNDAY', '10:00:00', '20:00:00', 1),
    (8, 'MONDAY', '09:00:00', '21:00:00', 2),
    (9, 'TUESDAY', '09:00:00', '21:00:00', 2),
    (10, 'WEDNESDAY', '09:00:00', '21:00:00', 2),
    (11, 'THURSDAY', '09:00:00', '21:00:00', 2),
    (12, 'FRIDAY', '09:00:00', '21:00:00', 2),
    (13, 'SATURDAY', '10:00:00', '22:00:00', 2),
    (14, 'SUNDAY', '10:00:00', '20:00:00', 2),
    (15, 'MONDAY', '07:00:00', '19:00:00', 3),
    (16, 'TUESDAY', '07:00:00', '19:00:00', 3),
    (17, 'WEDNESDAY', '07:00:00', '19:00:00', 3),
    (18, 'THURSDAY', '07:00:00', '19:00:00', 3),
    (19, 'FRIDAY', '07:00:00', '19:00:00', 3),
    (20, 'SATURDAY', '09:00:00', '20:00:00', 3);

-- Dummy data for menu
INSERT INTO menu (id, name, business_id)
VALUES
    (1, 'Flavors of Curry Menu', 1),
    (2, 'Crispy Bites Menu', 2),
    (3, 'Green Delight Menu', 3),
    (4, 'Vegan Vibes Menu', 4),
    (5, 'Sizzling Grill Menu', 5),
    (6, 'Taste of Mexico Menu', 6),
    (7, 'Asian Fusion Menu', 7),
    (8, 'Mediterranean Delights Menu', 8),
    (9, 'Burger Haven Menu', 9),
    (10, 'Pizza Paradise Menu', 10);

-- Dummy data for the category table
INSERT INTO category (id, name, menu_id)
VALUES
    (16, 'Tacos', 5),
    (17, 'Spring Rolls', 1),
    (18, 'Fried Rice', 5),
    (19, 'Salads', 3),
    (20, 'Smoothies', 3),
    (21, 'Desserts', 3),
    (22, 'Burgers', 4),
    (23, 'Pizza', 5),
    (24, 'Wraps', 4),
    (25, 'Seafood', 5);

-- Dummy data for additional products with category IDs
INSERT INTO product (id, name, description, price, category_id)
VALUES
    (26, 'Chicken Tacos', 'Delicious tacos with grilled chicken', 8.99, 16),
    (27, 'Vegetable Spring Rolls', 'Crispy rolls filled with fresh vegetables', 5.50, 17),
    (28, 'Shrimp Fried Rice', 'Rice stir-fried with shrimp and vegetables', 11.50, 18),
    (29, 'Greek Salad', 'Traditional Greek salad with olives and feta', 8.75, 19),
    (30, 'Berry Blast Smoothie', 'Mixed berry smoothie bursting with flavor', 6.25, 20),
    (31, 'Cheesecake', 'Creamy cheesecake topped with strawberries', 7.99, 21),
    (32, 'Classic Cheeseburger', 'Juicy beef patty with melted cheese', 9.99, 22),
    (33, 'Margherita Pizza', 'Classic pizza with tomato sauce and mozzarella', 10.99, 23),
    (34, 'Grilled Chicken Wrap', 'Wrap filled with grilled chicken and veggies', 8.50, 24),
    (35, 'Grilled Salmon', 'Perfectly grilled salmon fillet', 12.50, 25);

INSERT INTO ingredient (id, name, vegan, spicy, gluten_free)
VALUES
    (1, 'Curry Sauce', false, true, false),
    (2, 'Chicken', false, false, true),
    (3, 'Mango Puree', true, false, true),
    (4, 'Vegetables', true, false, true),
    (5, 'White Fish', false, false, true),
    (6, 'Black Tea', true, false, true),
    (7, 'Feta Cheese', false, false, true),
    (8, 'Spinach', true, false, true),
    (9, 'Chocolate', true, false, true),
    (10, 'Vegan Patty', true, true, true),
    (11, 'Quinoa', true, false, true),
    (12, 'Vegan Chocolate', true, false, true),
    (13, 'Beef', false, true, true),
    (14, 'Salmon', false, false, true),
    (15, 'Potatoes', true, false, true);
-- Dummy data for product_ingredient
INSERT INTO product_ingredient (id, product_id, ingredient_id)
VALUES
    (1, 31, 1),
    (2, 32, 2),
    (3, 33, 3),
    (4, 34, 4),
    (5, 35, 5),
    (6, 26, 6),
    (7, 27, 7),
    (8, 28, 8);

-- Dummy data for additional customers
INSERT INTO customer (id, first_name, last_name, email, password, telephone)
VALUES
    (1, 'John', 'Doe', 'john.doe@email.com', 'password1', '5551112233'),
    (2, 'Jane', 'Smith', 'jane.smith@email.com', 'password2', '5554445566'),
    (3, 'Bob', 'Johnson', 'bob.johnson@email.com', 'password3', '5557778899'),
    (4, 'Alice', 'Johnson', 'alice.johnson@email.com', 'password4', '5551234567'),
    (5, 'Michael', 'Brown', 'michael.brown@email.com', 'password5', '5552345678'),
    (6, 'Emily', 'Martinez', 'emily.martinez@email.com', 'password6', '5553456789'),
    (7, 'Daniel', 'Garcia', 'daniel.garcia@email.com', 'password7', '5554567890'),
    (8, 'Olivia', 'Lopez', 'olivia.lopez@email.com', 'password8', '5555678901'),
    (9, 'William', 'Gonzalez', 'william.gonzalez@email.com', 'password9', '5556789012'),
    (10, 'Sophia', 'Hernandez', 'sophia.hernandez@email.com', 'password10', '5557890123'),
    (11, 'James', 'Smith', 'james.smith@email.com', 'password11', '5558901234'),
    (12, 'Emma', 'Johnson', 'emma.johnson@email.com', 'password12', '5559012345'),
    (13, 'Alexander', 'Miller', 'alexander.miller@email.com', 'password13', '5550123456');


-- Dummy data for additional addresses
INSERT INTO customer_address (id, address, geolocation, primary_address, customer_id)
VALUES
    (4, '789 Oak Street, Another Town, USA', '(37.7749, -122.4194)', false, 4),
    (5, '123 Maple Avenue, Different City, USA', '(34.0522, -118.2437)', false, 4),
    (6, '456 Pine Street, New Place, USA', '(40.7128, -74.0060)', true, 5),
    (7, '789 Elm Street, New City, USA', '(41.8781, -87.6298)', false, 5),
    (8, '234 Birch Lane, Nearby Town, USA', '(42.3601, -71.0589)', true, 5),
    (9, '567 Cedar Street, Next Door, USA', '(39.7392, -104.9903)', false, 6),
    (10, '890 Spruce Street, Faraway City, USA', '(37.7749, -122.4194)', false, 6),
    (11, '901 Pine Street, Downtown, USA', '(34.0522, -118.2437)', true, 7),
    (12, '345 Elm Street, Suburbia, USA', '(40.7128, -74.0060)', false, 7),
    (13, '678 Maple Lane, Quiet Town, USA', '(41.8781, -87.6298)', true, 8);


-- Dummy data for additional orders
INSERT INTO orders (id, business_id, customer_id, customer_address_id, total_price, created_at)
VALUES
    (6, 1, 11, 10, 28.50, '2023-06-01 11:30:00'),
    (7, 2, 4, 4, 20.75, '2023-07-01 16:45:00'),
    (8, 3, 5, 7, 16.99, '2023-08-01 21:00:00'),
    (9, 4, 8, 13, 35.25, '2023-09-01 10:15:00'),
    (10, 5, 4, 12, 40.00, '2023-10-01 12:30:00'),
    (11, 1, 12, 6, 22.99, '2023-11-01 14:45:00'),
    (12, 2, 13, 7, 18.50, '2023-12-01 18:00:00'),
    (13, 3, 10, 8, 32.75, '2024-01-01 09:15:00'),
    (14, 4, 11, 9, 19.99, '2024-02-01 11:30:00'),
    (15, 5, 9, 11, 27.00, '2024-03-01 13:45:00');

-- Dummy data for additional order_product
INSERT INTO order_product (id, order_id, quantity, product_id)
VALUES
    (11, 6, 2, 26),
    (12, 6, 1, 27),
    (13, 7, 1, 28),
    (14, 7, 1, 29),
    (15, 8, 2, 30),
    (16, 8, 1, 31),
    (17, 9, 1, 32),
    (18, 9, 2, 33),
    (19, 10, 1, 34),
    (20, 10, 2, 35);

-- Dummy data for additional order_status
INSERT INTO order_status (id, order_id, status, session, status_changed_at)
VALUES
    (29, 6, 'CART', 'session6', '2023-06-01 11:30:00'),
    (30, 6, 'PROCESSING_ORDER', 'session6', '2023-06-01 11:31:00'),
    (31, 6, 'ACCEPTED', 'session6', '2023-06-01 11:35:00'),
    (32, 6, 'COOKING', 'session6', '2023-06-01 11:40:00'),
    (33, 6, 'PREPARING', 'session6', '2023-06-01 11:45:00'),
    (34, 6, 'DELIVERY', 'session6', '2023-06-01 12:00:00'),
    (35, 6, 'DELIVERED', 'session6', '2023-06-01 12:15:00'),
    (36, 7, 'CART', 'session7', '2023-07-01 16:45:00'),
    (37, 7, 'PROCESSING_ORDER', 'session7', '2023-07-01 16:46:00'),
    (38, 7, 'ACCEPTED', 'session7', '2023-07-01 16:50:00'),
    (39, 7, 'COOKING', 'session7', '2023-07-01 16:55:00'),
    (40, 7, 'PREPARING', 'session7', '2023-07-01 17:00:00'),
    (41, 7, 'DELIVERY', 'session7', '2023-07-01 17:15:00'),
    (42, 7, 'DELIVERED', 'session7', '2023-07-01 17:30:00'),
    (43, 8, 'CART', 'session8', '2023-08-01 21:00:00'),
    (44, 8, 'PROCESSING_ORDER', 'session8', '2023-08-01 21:01:00'),
    (45, 8, 'ACCEPTED', 'session8', '2023-08-01 21:05:00'),
    (46, 8, 'COOKING', 'session8', '2023-08-01 21:10:00'),
    (47, 8, 'PREPARING', 'session8', '2023-08-01 21:15:00'),
    (48, 8, 'DELIVERY', 'session8', '2023-08-01 21:30:00'),
    (49, 8, 'DELIVERED', 'session8', '2023-08-01 21:45:00'),
    (50, 9, 'CART', 'session9', '2023-09-01 10:15:00'),
    (51, 9, 'PROCESSING_ORDER', 'session9', '2023-09-01 10:16:00'),
    (52, 9, 'ACCEPTED', 'session9', '2023-09-01 10:20:00'),
    (53, 9, 'COOKING', 'session9', '2023-09-01 10:30:00'),
    (54, 9, 'PREPARING', 'session9', '2023-09-01 10:45:00'),
    (55, 9, 'DELIVERY', 'session9', '2023-09-01 11:00:00'),
    (56, 9, 'DELIVERED', 'session9', '2023-09-01 11:15:00'),
    (57, 10, 'CART', 'session10', '2023-10-01 12:30:00'),
    (58, 10, 'PROCESSING_ORDER', 'session10', '2023-10-01 12:31:00'),
    (59, 10, 'ACCEPTED', 'session10', '2023-10-01 12:35:00'),
    (60, 10, 'COOKING', 'session10', '2023-10-01 12:40:00'),
    (61, 10, 'PREPARING', 'session10', '2023-10-01 12:45:00'),
    (62, 10, 'DELIVERY', 'session10', '2023-10-01 13:00:00'),
    (63, 10, 'DELIVERED', 'session10', '2023-10-01 13:15:00');


ALTER SEQUENCE business_seq RESTART WITH 11;
ALTER SEQUENCE business_schedule_seq RESTART WITH 21;
ALTER SEQUENCE category_seq RESTART WITH 26;
ALTER SEQUENCE menu_seq RESTART WITH 11;
ALTER SEQUENCE ingredient_seq RESTART WITH 16;
ALTER SEQUENCE customer_address_seq RESTART WITH 14;
ALTER SEQUENCE order_product_seq RESTART WITH 21;
ALTER SEQUENCE order_status_seq RESTART WITH 64;
ALTER SEQUENCE customer_seq RESTART WITH 14;
ALTER SEQUENCE order_seq RESTART WITH 16;
ALTER SEQUENCE product_seq RESTART WITH 36;
