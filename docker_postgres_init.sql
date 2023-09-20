CREATE TABLE bank
(
    id            SERIAL PRIMARY KEY NOT NULL,
    name          VARCHAR(50),
    date_creation TIMESTAMP,
    last_modified TIMESTAMP,
    version       BIGINT
);

CREATE TABLE client
(
    id            SERIAL PRIMARY KEY NOT NULL,
    name          VARCHAR(50),
    last_name     VARCHAR(50),
    date_creation TIMESTAMP,
    last_modified TIMESTAMP,
    version       BIGINT
);

CREATE TABLE account
(
    id            SERIAL PRIMARY KEY NOT NULL,
    number        VARCHAR(50),
    amount        BIGINT,
    currency      VARCHAR(10),
    date_creation TIMESTAMP,
    last_modified TIMESTAMP,
    version       BIGINT
);

CREATE TABLE transaction
(
    id                  SERIAL PRIMARY KEY NOT NULL,
    account_number_from VARCHAR(50),
    account_number_to   VARCHAR(50),
    amount              BIGINT,
    currency            VARCHAR(10),
    date_creation       TIMESTAMP,
    last_modified       TIMESTAMP,
    version             BIGINT
);

CREATE TABLE bank_client_account_relation
(
    bank_id    BIGINT NOT NULL,
    client_id  BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    FOREIGN KEY (bank_id) REFERENCES bank (id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE,
    UNIQUE (bank_id, client_id, account_id)
);

CREATE TABLE transaction_account_relation
(
    transaction_id  BIGINT NOT NULL,
    account_from_id BIGINT NOT NULL,
    account_to_id   BIGINT NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transaction (id) ON DELETE CASCADE,
    FOREIGN KEY (account_from_id) REFERENCES account (id),
    FOREIGN KEY (account_to_id) REFERENCES account (id),
    UNIQUE (transaction_id, account_from_id, account_to_id)
);

INSERT INTO bank (id, name, date_creation, last_modified, version)
VALUES (1, 'Bank 1', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (2, 'Bank 2', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (3, 'Bank 3', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (4, 'Bank 4', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (5, 'Bank 5', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1);

INSERT INTO client (id, name, last_name, date_creation, last_modified, version)
VALUES (1, 'Client 1', 'Client 1', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (2, 'Client 2', 'Client 2', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (3, 'Client 3', 'Client 3', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (4, 'Client 4', 'Client 4', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (5, 'Client 5', 'Client 5', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (6, 'Client 6', 'Client 6', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (7, 'Client 7', 'Client 7', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (8, 'Client 8', 'Client 8', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (9, 'Client 9', 'Client 9', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (10, 'Client 10', 'Client 10', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (11, 'Client 11', 'Client 11', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (12, 'Client 12', 'Client 12', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (13, 'Client 13', 'Client 13', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (14, 'Client 14', 'Client 14', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (15, 'Client 15', 'Client 15', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (16, 'Client 16', 'Client 16', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (17, 'Client 17', 'Client 17', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (18, 'Client 18', 'Client 18', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (19, 'Client 19', 'Client 19', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (20, 'Client 20', 'Client 20', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1);

INSERT INTO account (id, number, amount, currency, date_creation, last_modified, version)
VALUES (1, '0000000000000001', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (2, '0000000000000011', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (3, '0000000000000111', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (4, '0000000000001111', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (5, '0000000000011111', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (6, '0000000000111111', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (7, '0000000001111111', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (8, '0000000011111111', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (9, '0000000111111111', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (10, '0000001111111111', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (11, '0000011111111111', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (12, '0000111111111111', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (13, '0001111111111111', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (14, '0011111111111111', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (15, '0111111111111111', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (16, '1111111111111111', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (17, '1111111111111112', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (18, '1111111111111122', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (19, '1111111111111222', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (20, '1111111111112222', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (21, '1111111111122222', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (22, '1111111111222222', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (23, '1111111112222222', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (24, '1111111122222222', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (25, '1111111222222222', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (26, '1111112222222222', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (27, '1111122222222222', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (28, '1111222222222222', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (29, '1112222222222222', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (30, '1122222222222222', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (31, '1222222222222222', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (32, '2222222222222222', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (33, '2222222222222223', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (34, '2222222222222233', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (35, '2222222222222333', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (36, '2222222222223333', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (37, '2222222222233333', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (38, '2222222222333333', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (39, '2222222223333333', 1000.0, 'BYN', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1),
       (40, '2222222233333333', 1000.0, 'USD', '2020-03-08 10:00:00', '2020-03-08 10:00:00', 1);

INSERT INTO transaction (id, account_number_from, account_number_to, amount, currency, date_creation, last_modified, version)
VALUES (1, '0000000000000001', '0000000000000111', 200.0, 'BYN-USD', '2020-04-08 10:00:00', '2020-04-08 10:00:00', 1),
       (2, '0000000000000111', '0000000000000001', 50.0, 'USD-BYN', '2020-04-11 10:00:00', '2020-04-11 10:00:00', 1),
       (3, '0000000000000001', '0000000000000111', 200.0, 'BYN-USD', '2020-05-08 10:00:00', '2020-05-08 10:00:00', 1),
       (4, '0000000000000111', '0000000000000001', 50.0, 'USD-BYN', '2020-05-11 10:00:00', '2020-05-11 10:00:00', 1);

INSERT INTO bank_client_account_relation (bank_id, client_id, account_id)
VALUES (1, 1, 1),
       (1, 2, 2),
       (1, 3, 3),
       (1, 2, 4),
       (1, 4, 5),
       (1, 5, 6),
       (1, 6, 7),
       (1, 7, 8),
       (2, 1, 9),
       (2, 6, 10),
       (2, 8, 11),
       (2, 9, 12),
       (2, 10, 13),
       (2, 11, 14),
       (2, 12, 15),
       (2, 13, 16),
       (3, 3, 17),
       (3, 7, 18),
       (3, 9, 19),
       (3, 14, 20),
       (3, 15, 21),
       (3, 15, 22),
       (3, 16, 23),
       (3, 17, 24),
       (4, 5, 25),
       (4, 11, 26),
       (4, 12, 27),
       (4, 14, 28),
       (4, 18, 29),
       (4, 19, 30),
       (4, 19, 31),
       (4, 20, 32),
       (5, 4, 33),
       (5, 8, 34),
       (5, 10, 35),
       (5, 13, 36),
       (5, 16, 37),
       (5, 17, 38),
       (5, 18, 39),
       (5, 20, 40);

INSERT INTO transaction_account_relation (transaction_id, account_from_id, account_to_id)
VALUES (1, 1, 3),
       (2, 3, 1),
       (3, 1, 3),
       (4, 3, 1);