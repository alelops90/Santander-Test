INSERT INTO BANK_CLIENT (name, exclusive, balance, account_number, birth_date) VALUES ('Joao', TRUE, 5000, '93120031', '1990-09-03')
INSERT INTO BANK_CLIENT (name, exclusive, balance, account_number, birth_date) VALUES ('Maria', FALSE, 700, '93120032', '1975-11-09')

INSERT INTO BANK_TRANSACTION (amount, previous_balance, new_balance, number_account, transaction_time, operation_type) VALUES (500, 4500, 4000, '93120031', '2023-04-07', 'drawal')
INSERT INTO BANK_TRANSACTION (amount, previous_balance, new_balance, number_account, transaction_time, operation_type) VALUES (500, 4000, 4500, '93120031', '2023-04-10', 'deposite')
INSERT INTO BANK_TRANSACTION (amount, previous_balance, new_balance, number_account, transaction_time, operation_type) VALUES (500, 4500, 5000, '93120031', '2023-04-20', 'drawal')
INSERT INTO BANK_TRANSACTION (amount, previous_balance, new_balance, number_account, transaction_time, operation_type) VALUES (1000, 5000, 6000, '93120031', '2023-04-15', 'deposite')


INSERT INTO BANK_TRANSACTION (amount, previous_balance, new_balance, number_account, transaction_time, operation_type) VALUES (500, 2000, 1500, '93120032', '2023-04-07', 'drawal')
INSERT INTO BANK_TRANSACTION (amount, previous_balance, new_balance, number_account, transaction_time, operation_type) VALUES (800, 1500, 2300, '93120032', '2023-04-10', 'deposite')
INSERT INTO BANK_TRANSACTION (amount, previous_balance, new_balance, number_account, transaction_time, operation_type) VALUES (1000, 2300, 1300, '93120032', '2023-04-15', 'drawal')
INSERT INTO BANK_TRANSACTION (amount, previous_balance, new_balance, number_account, transaction_time, operation_type) VALUES (1000, 1300, 2300, '93120032', '2023-04-20', 'deposite')
