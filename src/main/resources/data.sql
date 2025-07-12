-- Sample data for bookstore application

-- Insert categories
INSERT INTO categories (name) VALUES 
('Fiction'),
('Non-Fiction'),
('Science Fiction'),
('Mystery'),
('Biography'),
('History'),
('Self-Help'),
('Children');

-- Insert publishers
INSERT INTO publishers (name, contact_email, phone) VALUES 
('Penguin Random House', 'contact@penguinrandomhouse.com', '123-456-7890'),
('HarperCollins', 'info@harpercollins.com', '234-567-8901'),
('Simon & Schuster', 'contact@simonschuster.com', '345-678-9012'),
('Macmillan Publishers', 'info@macmillan.com', '456-789-0123'),
('Hachette Book Group', 'contact@hachette.com', '567-890-1234');

-- Insert authors
INSERT INTO authors (full_name) VALUES 
('J.K. Rowling'),
('Stephen King'),
('George R.R. Martin'),
('J.R.R. Tolkien'),
('Agatha Christie'),
('Jane Austen'),
('Ernest Hemingway'),
('Mark Twain'),
('Charles Dickens'),
('Leo Tolstoy');

-- Insert users
INSERT INTO users (full_name, email, password, phone, role) VALUES 
('Admin User', 'admin@bookstore.com', 'admin123', '123-456-7890', 'ADMIN'),
('John Doe', 'john@example.com', 'password123', '234-567-8901', 'CUSTOMER'),
('Jane Smith', 'jane@example.com', 'password456', '345-678-9012', 'CUSTOMER'),
('Bob Johnson', 'bob@example.com', 'password789', '456-789-0123', 'CUSTOMER'),
('Alice Brown', 'alice@example.com', 'passwordabc', '567-890-1234', 'CUSTOMER');

-- Insert books
INSERT INTO books (title, isbn, price, stock, published_year, category_id, publisher_id) VALUES 
('Harry Potter and the Philosopher''s Stone', '9780747532743', 19.99, 100, 1997, 1, 1),
('The Shining', '9780385121675', 15.99, 75, 1977, 1, 2),
('A Game of Thrones', '9780553103540', 24.99, 50, 1996, 3, 3),
('The Lord of the Rings', '9780618640157', 29.99, 60, 1954, 3, 4),
('Murder on the Orient Express', '9780062073501', 12.99, 80, 1934, 4, 5),
('Pride and Prejudice', '9780141439518', 9.99, 90, 1813, 1, 1),
('The Old Man and the Sea', '9780684801223', 11.99, 70, 1952, 1, 2),
('The Adventures of Tom Sawyer', '9780143039563', 10.99, 85, 1876, 1, 3),
('A Tale of Two Cities', '9780141439600', 8.99, 95, 1859, 1, 4),
('War and Peace', '9781400079988', 14.99, 65, 1869, 1, 5);

-- Insert book_authors relationships
INSERT INTO book_authors (book_id, author_id) VALUES 
(1, 1), -- Harry Potter - J.K. Rowling
(2, 2), -- The Shining - Stephen King
(3, 3), -- A Game of Thrones - George R.R. Martin
(4, 4), -- The Lord of the Rings - J.R.R. Tolkien
(5, 5), -- Murder on the Orient Express - Agatha Christie
(6, 6), -- Pride and Prejudice - Jane Austen
(7, 7), -- The Old Man and the Sea - Ernest Hemingway
(8, 8), -- The Adventures of Tom Sawyer - Mark Twain
(9, 9), -- A Tale of Two Cities - Charles Dickens
(10, 10); -- War and Peace - Leo Tolstoy

-- Insert orders
INSERT INTO orders (user_id, total_amount, status) VALUES 
(2, 45.97, 'CONFIRMED'), -- John's order
(3, 29.99, 'PENDING'),   -- Jane's order
(4, 22.98, 'CONFIRMED'), -- Bob's order
(5, 19.99, 'CANCELLED'), -- Alice's order
(2, 34.98, 'CONFIRMED'); -- John's second order

-- Insert order_items
INSERT INTO order_items (order_id, book_id, quantity, price) VALUES 
(1, 1, 1, 19.99), -- John ordered Harry Potter
(1, 5, 2, 12.99), -- John ordered 2 copies of Murder on the Orient Express
(2, 4, 1, 29.99), -- Jane ordered The Lord of the Rings
(3, 6, 1, 9.99),  -- Bob ordered Pride and Prejudice
(3, 7, 1, 11.99), -- Bob ordered The Old Man and the Sea
(4, 1, 1, 19.99), -- Alice ordered Harry Potter (cancelled)
(5, 2, 1, 15.99), -- John's second order - The Shining
(5, 8, 1, 10.99), -- John's second order - The Adventures of Tom Sawyer
(5, 9, 1, 8.99);  -- John's second order - A Tale of Two Cities

-- Insert shipments
INSERT INTO shipments (order_id, tracking_number, shipped_at, delivery_estimate) VALUES 
(1, 'TRK123456789', '2023-01-15 10:30:00', '2023-01-20'),
(3, 'TRK234567890', '2023-01-16 11:45:00', '2023-01-21'),
(5, 'TRK345678901', '2023-01-17 09:15:00', '2023-01-22');

-- Insert reviews
INSERT INTO reviews (user_id, book_id, rating, comment) VALUES 
(2, 1, 5, 'Excellent book! Highly recommended for all ages.'),
(3, 4, 5, 'A classic fantasy masterpiece.'),
(4, 6, 4, 'A timeless romance novel with witty dialogue.'),
(4, 7, 3, 'Good but a bit slow-paced for my taste.'),
(5, 2, 5, 'One of the scariest books I''ve ever read!');