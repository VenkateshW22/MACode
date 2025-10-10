
-- Create Tables
CREATE TABLE departments (
    department_id SERIAL PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL
);

CREATE TABLE employees (
    employee_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    salary NUMERIC(10, 2),
    department_id INT,
    manager_id INT,
    email VARCHAR(100) UNIQUE,
    CONSTRAINT fk_department
        FOREIGN KEY(department_id)
        REFERENCES departments(department_id)
        ON DELETE SET NULL,
    CONSTRAINT fk_manager
        FOREIGN KEY(manager_id)
        REFERENCES employees(employee_id)
        ON DELETE SET NULL
);

CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    customer_name VARCHAR(100),
    city VARCHAR(50)
);

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(100),
    category VARCHAR(50),
    price NUMERIC(10, 2)
);

CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    order_date DATE,
    customer_id INT,
    CONSTRAINT fk_customer
        FOREIGN KEY(customer_id)
        REFERENCES customers(customer_id)
        ON DELETE CASCADE
);

CREATE TABLE order_items (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    CONSTRAINT fk_order
        FOREIGN KEY(order_id)
        REFERENCES orders(order_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_product
        FOREIGN KEY(product_id)
        REFERENCES products(product_id)
        ON DELETE SET NULL
);

-- Populate Data
INSERT INTO departments (department_name) VALUES
('Sales'), ('Engineering'), ('Marketing'), ('Human Resources');

INSERT INTO employees (first_name, last_name, salary, department_id, manager_id, email) VALUES
('James', 'Smith', 80000, 2, NULL, 'j.smith@example.com'),
('Maria', 'Garcia', 95000, 2, 1, 'm.garcia@example.com'),
('David', 'Johnson', 82000, 2, 1, 'd.johnson@example.com'),
('John', 'Williams', 75000, 1, NULL, 'j.williams@example.com'),
('Patricia', 'Brown', 68000, 1, 4, 'p.brown@example.com'),
('Robert', 'Jones', 71000, 1, 4, 'r.jones@example.com'),
('Jennifer', 'Miller', 110000, 3, NULL, 'j.miller@example.com'),
('Michael', 'Davis', 85000, 3, 7, 'm.davis@example.com'),
('Linda', 'Rodriguez', 60000, 4, NULL, 'l.rodriguez@example.com'),
('William', 'Martinez', 62000, 4, 9, 'w.martinez@example.com'),
('Elizabeth', 'Hernandez', 98000, 2, 1, 'e.hernandez@example.com'),
('Richard', 'Lopez', 73000, 1, 4, 'r.lopez@example.com');

INSERT INTO customers (customer_name, city) VALUES
('Alice Johnson', 'New York'), ('Bob Williams', 'Los Angeles'), ('Charlie Brown', 'Chicago'), ('Diana Miller', 'Houston'), ('Ethan Davis', 'Phoenix'), ('Fiona Garcia', 'New York');

INSERT INTO products (product_name, category, price) VALUES
('Laptop', 'Electronics', 1200.00), ('Mouse', 'Electronics', 25.00), ('Keyboard', 'Electronics', 75.00),
('SQL for Dummies', 'Books', 30.00), ('The Phoenix Project', 'Books', 22.00),
('Coffee Mug', 'Home Goods', 15.00), ('Desk Chair', 'Furniture', 250.00), ('Monitor', 'Electronics', 300.00);

-- Generate orders over a few months
INSERT INTO orders (order_date, customer_id) VALUES
('2025-08-10', 1), ('2025-08-12', 3), ('2025-09-05', 2), ('2025-09-08', 1),
('2025-09-15', 4), ('2025-10-01', 5), ('2025-10-02', 6), ('2025-10-04', 3);

INSERT INTO order_items (order_id, product_id, quantity) VALUES
(1, 1, 1), (1, 2, 1), (2, 4, 2), (2, 6, 1), (3, 7, 1), (4, 3, 1), (4, 8, 2),
(5, 5, 3), (6, 1, 1), (7, 2, 5), (8, 4, 1), (8, 5, 1);

-- Add indexes that will be useful later (especially for Hour 5)
CREATE INDEX idx_employees_department_id ON employees (department_id);
CREATE INDEX idx_orders_customer_id ON orders (customer_id);
CREATE INDEX idx_order_items_order_id ON order_items (order_id);
CREATE INDEX idx_order_items_product_id ON order_items (product_id);
CREATE INDEX idx_products_category_price ON products (category, price DESC);

-- INNER JOIN

-- SELECT
-- e.first_name,
-- e.last_name,
-- d.department_name
-- From
-- employees e
-- INNER JOIN
-- departments d ON e.department_id = d.department_id;

-- LEFT JOIN

-- SELECT 
-- d.department_name,
-- e.first_name,
-- e.last_name
-- FROM
-- departments d --left table
-- LEFT JOIN
-- employees e ON d.department_id = e.department_id;

-- RIGHT JOIN

-- SELECT
-- e.first_name,
-- e.last_name,
-- d.department_name
-- From
-- departments d
-- RIGHT JOIN
-- employees e ON e.department_id = d.department_id;

-- FULL OUTER JOIN / FULL JOIN

-- SELECT 
-- d.department_name,
-- e.first_name,
-- e.last_name
-- FROM
-- departments d 
-- FULL JOIN
-- employees e ON d.department_id = e.department_id;


-- SELF JOIN
-- SELECT 
-- e.first_name || ' ' || e.last_name AS employee_name,
-- m.first_name || ' ' || m.last_name AS manager_name
-- FROM 
-- employees e
-- LEFT JOIN
-- employees m ON e.manager_id = m.employee_id;

-- MULTI TABLE JOIN

-- SELECT
-- c.customer_name,
-- p.product_name
-- FROM
-- customers c
-- JOIN orders o ON c.customer_id = o.customer_id
-- JOIN order_items oi ON o.order_id = oi.order_id
-- JOIN products p ON oi.product_id = p.product_id
-- WHERE 
-- p.category = 'Electronics'

-- Practise question

-- Write a query to find all employees who work in the 'Sales' department. 
-- List their first name, last name, and department name.

-- SELECT 
--     e.first_name, 
--     e.last_name, 
--     d.department_name
-- FROM employees e
-- JOIN departments d 
--     ON e.department_id = d.department_id
-- WHERE d.department_name = 'Sales';

-- -- Products that have been ordered
-- SELECT product_id FROM order_items
-- EXCEPT
-- -- Products in the 'Electronics' category
-- SELECT product_id FROM products WHERE category = 'Electronics';

-- Types of Subqueries
-- Find employees earning more than the company average -- Scalar Subquery

-- SELECT employee_id, first_name, salary
-- FROM employees
-- WHERE salary > (SELECT AVG(salary) FROM employees); 

-- Find customers who placed an order in October 2025 - multi-row subquery

-- SELECT customer_name
-- FROM customers
-- WHERE customer_id IN (SELECT customer_id FROM orders WHERE order_date >= '2025-10-01');

-- Calculate the average salary for each department - dervied table
-- SELECT a.department_name, a.avg_salary
-- FROM (
--     SELECT d.department_name, AVG(e.salary) as avg_salary
--     FROM employees e
--     JOIN departments d ON e.department_id = d.department_id
--     GROUP BY d.department_name
-- ) AS a; -- 'a' is the required alias for the derived table


-- Correlated Subqueries
-- Find all products that are the most expensive in their own category.
-- SELECT product_name, price, category
-- FROM products p1
-- WHERE price = (
--     SELECT MAX(p2.price)
--     FROM products p2
--     WHERE p2.category = p1.category -- The correlation: p2.category depends on p1.category
-- );

-- window function
-- Show each employee's salary next to their department's average salary
-- SELECT
--     first_name,
--     salary,
--     d.department_name,
--     AVG(salary) OVER (PARTITION BY d.department_name) AS avg_dept_salary,
--     salary - (AVG(salary) OVER (PARTITION BY d.department_name)) AS diff_from_avg
-- FROM
--     employees e
-- JOIN
--     departments d ON e.department_id = d.department_id;

-- Rank employees by salary within each department
SELECT
    first_name,
    salary,
    d.department_name,
    ROW_NUMBER() OVER (PARTITION BY d.department_name ORDER BY salary DESC) as row_num,
    RANK() OVER (PARTITION BY d.department_name ORDER BY salary DESC) as rnk,
    DENSE_RANK() OVER (PARTITION BY d.department_name ORDER BY salary DESC) as dense_rnk
FROM
    employees e
JOIN
    departments d ON e.department_id = d.department_id;



