CREATE TABLE order_table (
                             id BIGINT ,
                             customer_name VARCHAR(255) NOT NULL,
                             product_id BIGINT NOT NULL,
                             quantity INT NOT NULL,
                             order_date TIMESTAMP NOT NULL,
                             status VARCHAR(100) NOT NULL
);

CREATE TABLE product_table (
                               id BIGINT ,
                               name VARCHAR(255) NOT NULL,
                               description VARCHAR(255),
                               price DECIMAL(10, 2) NOT NULL,
                               stock INT NOT NULL
);

CREATE TABLE shipment_table (
                                id BIGINT ,
                                order_id BIGINT NOT NULL,
                                shipment_date TIMESTAMP NOT NULL,
                                tracking_number VARCHAR(255) NOT NULL,
                                carrier VARCHAR(100) NOT NULL,
                                status VARCHAR(100) NOT NULL
);

CREATE TABLE invoice_table (
                               id BIGINT ,
                               order_id BIGINT NOT NULL,
                               invoice_date TIMESTAMP NOT NULL,
                               total DECIMAL(10, 2) NOT NULL,
                               payment_status VARCHAR(100) NOT NULL
);