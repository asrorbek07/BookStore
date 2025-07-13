-- Database schema for bookstore application

-- Categories table
CREATE TABLE IF NOT EXISTS categories
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Publishers table
CREATE TABLE IF NOT EXISTS publishers
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) unique not null ,
    contact_email VARCHAR(100) unique ,
    phone         VARCHAR(20) unique ,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Authors table
CREATE TABLE IF NOT EXISTS authors
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    full_name  VARCHAR(100) not null ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Users table
CREATE TABLE IF NOT EXISTS users
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    full_name  VARCHAR(100) not null ,
    email      VARCHAR(100) UNIQUE not null ,
    password   VARCHAR(100) unique not null ,
    phone      VARCHAR(20) unique ,
    role       ENUM ('CUSTOMER', 'ADMIN') DEFAULT 'CUSTOMER',
    created_at TIMESTAMP                  DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Books table
CREATE TABLE IF NOT EXISTS books
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(255) not null ,
    isbn           VARCHAR(20) UNIQUE not null ,
    price          DECIMAL(10, 2) not null ,
    stock          INT not null ,
    published_year INT not null ,
    category_id    INT,
    publisher_id   INT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories (id) on delete set null ,
    FOREIGN KEY (publisher_id) REFERENCES publishers (id) on delete set null
);

-- Book Authors junction table
CREATE TABLE IF NOT EXISTS book_authors
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    book_id    INT,
    author_id  INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books (id) on delete set null ,
    FOREIGN KEY (author_id) REFERENCES authors (id) on delete set null
);

-- Orders table
CREATE TABLE IF NOT EXISTS orders
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    user_id      INT,
    total_amount DECIMAL(10, 2) not null ,
    status       ENUM ('PENDING', 'CONFIRMED', 'CANCELLED') not null ,
    order_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Order Items table
CREATE TABLE IF NOT EXISTS order_items
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    order_id   INT,
    book_id    INT,
    quantity   INT not null ,
    price      DECIMAL(10, 2) not null ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (book_id) REFERENCES books (id) on delete set null
);

-- Shipments table
CREATE TABLE IF NOT EXISTS shipments
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    order_id          INT,
    tracking_number   VARCHAR(100) not null ,
    shipped_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    delivery_estimate DATE DEFAULT CURRENT_DATE,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);

-- Reviews table
CREATE TABLE IF NOT EXISTS reviews
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT,
    book_id     INT,
    rating      INT CHECK (rating BETWEEN 1 AND 5),
    comment     TEXT,
    reviewed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (book_id) REFERENCES books (id)
);
