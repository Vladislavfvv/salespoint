INSERT INTO salespointschema.user_access(user_login, user_password, full_name, user_role)
VALUES ('administrator', '111', 'Administrators full name', 'administrator'),
       ('manager', '222', 'Managers full name', 'user');

insert into salespointschema.payment_system (payment_system_name)
values ('VISA International Service Association'),
       ('Mastercard'),
       ('JCB'),
       ('American Express'),
       ('Diners Club International'),
       ('China UnionPay ');

INSERT INTO salespointschema.acquiring_bank (bic,
                                             abbreviated_name)
VALUES ('041234567', 'ПАО Банк-эквайер №1'),
       ('041234568', 'ПАО Банк-эквайер №2'),
       ('041234569', 'ПАО Банк-эквайер №3');

INSERT INTO salespointschema.sales_point (pos_name,
                                          pos_address,
                                          pos_inn,
                                          acquiring_bank_id)
VALUES ('Shop №1', 'City 1-st 1', '123456788888', 1),
       ('Shop №2 ', 'City, 2-st 2 ', '159148777777', 2),
       ('Shop №3', 'City, 3-st 3 ', '123596222222', 1);

INSERT INTO salespointschema.merchant_category_code (mcc,
                                                     mcc_name)
VALUES ('5309', 'Беспошлинные магазины Duty Free'),
       ('5651', 'Одежда для всей семьи'),
       ('5691', 'Магазины мужской и женской одежды'),
       ('5812', 'Места общественного питания, рестораны'),
       ('5814', 'Фастфуд');

INSERT INTO salespointschema.terminal (terminal_id,
                                       mcc_id,
                                       sales_point_id)
VALUES ('00000001', 1, 1),
       ('00000002', 2, 2),
       ('00000003', 3, 3);


INSERT INTO salespointschema.transaction_type (transaction_type_name, operator)
VALUES ('Списание со счета ', '-'),
       ('Пополнение счета', '+');

INSERT INTO salespointschema.card (card_number,
                                   expiration_date,
                                   holder_name,
                                   payment_system_id)
VALUES ('4123450000000019', '2025-12-31', 'IVAN I. IVANOV', 2 ),
       ('5123450000000024', '2025-12-31', 'SEMION E. PETROV', 3);


INSERT INTO salespointschema.response_code (error_code,
                                            error_description,
                                            error_level)
VALUES ('00', 'одобрено и завершено', 'Все в порядке'),
       ('01', 'авторизация отклонена, обратиться в банк-эмитент', 'не критическая'),
       ('03', 'незарегистрированная торговая точка или агрегатор платежей', 'не критическая'),
       ('05', 'авторизация отклонена, оплату не проводить', 'критическая'),
       ('41', 'карта утеряна, изъять', 'критическая'),
       ('51', 'недостаточно средств на счёте', 'сервисная или аппаратная ошибка'),
       ('55', 'неправильный PIN', 'не критическая');




INSERT INTO salespointschema.transaction (transaction_date,
                                          sum,
                                          transaction_type_id,
                                          card_id,
                                          terminal_id,
                                          response_code_id,
                                          authorization_code)
VALUES ('2022-10-22', 10.11, 1, 1, 2, 1, 2),
       ('2022-04-06', 50.92, 1, 2, 2, 2, 3);