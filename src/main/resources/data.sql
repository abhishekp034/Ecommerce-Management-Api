INSERT INTO products (sku, name, category, price, quantity, status)
SELECT
    'SKU-' || LPAD(x, 4, '0'),
    CASE MOD(x, 5)
        WHEN 0 THEN 'Wireless Mouse '
        WHEN 1 THEN 'Mechanical Keyboard '
        WHEN 2 THEN 'USB-C Hub '
        WHEN 3 THEN 'Office Monitor '
        ELSE 'Laptop Stand '
    END || x,
    CASE MOD(x, 6)
        WHEN 0 THEN 'Accessories'
        WHEN 1 THEN 'Input Devices'
        WHEN 2 THEN 'Connectivity'
        WHEN 3 THEN 'Displays'
        WHEN 4 THEN 'Workspace'
        ELSE 'Essentials'
    END,
    499 + (x * 17.25),
    10 + MOD(x * 7, 95),
    CASE WHEN MOD(x, 13) = 0 THEN 'LOW_STOCK' ELSE 'ACTIVE' END
FROM SYSTEM_RANGE(1, 220);

INSERT INTO customers (name, email, phone, city, loyalty_tier) VALUES
('Aarav Sharma', 'aarav.sharma@example.com', '9876500011', 'Delhi', 'GOLD'),
('Maya Iyer', 'maya.iyer@example.com', '9876500012', 'Bengaluru', 'PLATINUM'),
('Rohan Mehta', 'rohan.mehta@example.com', '9876500013', 'Mumbai', 'SILVER'),
('Isha Nair', 'isha.nair@example.com', '9876500014', 'Kochi', 'GOLD'),
('Kabir Singh', 'kabir.singh@example.com', '9876500015', 'Pune', 'BRONZE'),
('Anika Rao', 'anika.rao@example.com', '9876500016', 'Hyderabad', 'SILVER'),
('Vihaan Das', 'vihaan.das@example.com', '9876500017', 'Kolkata', 'GOLD'),
('Sara Khan', 'sara.khan@example.com', '9876500018', 'Chennai', 'PLATINUM');

INSERT INTO customer_orders (customer_id, product_id, quantity, order_status, order_date) VALUES
(1, 3, 2, 'PLACED', CURRENT_DATE),
(1, 11, 1, 'SHIPPED', DATEADD('DAY', -2, CURRENT_DATE)),
(2, 8, 4, 'DELIVERED', DATEADD('DAY', -5, CURRENT_DATE)),
(3, 21, 1, 'PLACED', CURRENT_DATE),
(4, 45, 3, 'DELIVERED', DATEADD('DAY', -9, CURRENT_DATE)),
(5, 66, 1, 'CANCELLED', DATEADD('DAY', -1, CURRENT_DATE)),
(6, 88, 2, 'SHIPPED', DATEADD('DAY', -3, CURRENT_DATE)),
(7, 109, 5, 'PLACED', CURRENT_DATE),
(8, 151, 1, 'DELIVERED', DATEADD('DAY', -4, CURRENT_DATE));
