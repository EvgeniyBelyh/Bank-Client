INSERT INTO `account` (`id`,`client_id`,`account_type_id`,`currency_id`,`number`,`balance`,`overlimit`,`interest_rate`,`loan_rate`,`card_number`,`blocked`,`expiration_date`,`CVV`,`account_name`) VALUES (1,1,1,1,'40817810100001234567',50000,50000,0,25,'4036111122223333',0,'2020-07-15 17:00:00','232','Кредитная карта');
INSERT INTO `account` (`id`,`client_id`,`account_type_id`,`currency_id`,`number`,`balance`,`overlimit`,`interest_rate`,`loan_rate`,`card_number`,`blocked`,`expiration_date`,`CVV`,`account_name`) VALUES (2,1,2,1,'40817810200001234567',25000,10000,0,25,'4036111177771111',0,'2019-07-20 18:00:00','821','Дебетовая карта');
INSERT INTO `account` (`id`,`client_id`,`account_type_id`,`currency_id`,`number`,`balance`,`overlimit`,`interest_rate`,`loan_rate`,`card_number`,`blocked`,`expiration_date`,`CVV`,`account_name`) VALUES (3,1,3,1,'42307810700001234567',40000,0,8.25,0,NULL,0,NULL,NULL,'Копилка');
INSERT INTO `account` (`id`,`client_id`,`account_type_id`,`currency_id`,`number`,`balance`,`overlimit`,`interest_rate`,`loan_rate`,`card_number`,`blocked`,`expiration_date`,`CVV`,`account_name`) VALUES (4,2,1,1,'40817810200001122334',100000,100000,0,25,'4036123455884321',0,'2019-06-14 14:00:00','743','Кредитная карта');
INSERT INTO `account` (`id`,`client_id`,`account_type_id`,`currency_id`,`number`,`balance`,`overlimit`,`interest_rate`,`loan_rate`,`card_number`,`blocked`,`expiration_date`,`CVV`,`account_name`) VALUES (5,2,2,1,'40817810400001122334',70000,20000,0,25,'4036567833220987',0,'2018-09-04 20:00:00','157','Дебетовая карта');
INSERT INTO `account` (`id`,`client_id`,`account_type_id`,`currency_id`,`number`,`balance`,`overlimit`,`interest_rate`,`loan_rate`,`card_number`,`blocked`,`expiration_date`,`CVV`,`account_name`) VALUES (6,3,2,1,'40817810200007755331',45000,0,0,0,'4036857609126354',0,'2018-03-17 19:30:00','789','Дебетовая карта');

INSERT INTO `account_type` (`id`,`name`) VALUES (1,'Кредитная карта');
INSERT INTO `account_type` (`id`,`name`) VALUES (2,'Дебетовая карта');
INSERT INTO `account_type` (`id`,`name`) VALUES (3,'Вклад');

INSERT INTO `bank_message` (`id`,`client_id`,`text`,`message_date`) VALUES (1,1,'Сообщение тест1','2011-04-20 17:00:00');
INSERT INTO `bank_message` (`id`,`client_id`,`text`,`message_date`) VALUES (2,1,'Сообщение тест2','2012-03-20 17:00:00');
INSERT INTO `bank_message` (`id`,`client_id`,`text`,`message_date`) VALUES (3,1,'Сообщение тест3','2012-04-20 17:00:00');
INSERT INTO `bank_message` (`id`,`client_id`,`text`,`message_date`) VALUES (11,1,'Сообщение №1','2015-03-20 17:00:00');
INSERT INTO `bank_message` (`id`,`client_id`,`text`,`message_date`) VALUES (12,1,'Сообщение №1','2014-03-20 17:00:00');
INSERT INTO `bank_message` (`id`,`client_id`,`text`,`message_date`) VALUES (14,1,'ТЕСТ ОБНОВЛЕНИЯ','2017-03-20 17:00:00');
INSERT INTO `bank_message` (`id`,`client_id`,`text`,`message_date`) VALUES (15,1,'Сообщение №1','2010-02-20 17:00:00');
INSERT INTO `bank_message` (`id`,`client_id`,`text`,`message_date`) VALUES (16,1,'Сообщение №1','2012-03-20 17:00:00');
INSERT INTO `bank_message` (`id`,`client_id`,`text`,`message_date`) VALUES (17,1,'Сообщение №1','2012-04-20 17:00:00');

INSERT INTO `client` (`id`,`name`,`login`,`password`,`blocked`,`admin`) VALUES (1,'Белых Евгений Владимирович','belyh','12345678',0,1);
INSERT INTO `client` (`id`,`name`,`login`,`password`,`blocked`,`admin`) VALUES (2,'Петров Сергей Борисович','petrov','87654321',0,0);
INSERT INTO `client` (`id`,`name`,`login`,`password`,`blocked`,`admin`) VALUES (3,'Егоров Василий Петрович','egorov','11223344',1,0);

INSERT INTO `currency` (`id`,`name`,`code`) VALUES (1,'RUR','810');
INSERT INTO `currency` (`id`,`name`,`code`) VALUES (2,'USD','840');
INSERT INTO `currency` (`id`,`name`,`code`) VALUES (3,'EUR','982');

INSERT INTO `deposit` (`id`,`name`,`interest_rate`,`duration`,`discription`) VALUES (1,'Копилка',5.75,35,'Простые проценты. Начисление в конце срока вклада');
INSERT INTO `deposit` (`id`,`name`,`interest_rate`,`duration`,`discription`) VALUES (2,'Сохраняй',6.8,365,'Простые проценты. Начисление в конце квартала');
INSERT INTO `deposit` (`id`,`name`,`interest_rate`,`duration`,`discription`) VALUES (3,'Приумножай',5.3,90,'Сложные проценты. Начисление в конце месяца');

INSERT INTO `operation` (`id`,`create_date`,`account_id`,`operation_type_id`,`description`,`destination_account`,`partner_bank_id`,`number`,`execution_date`,`amount`,`status_id`,`comment`) VALUES (1,'2017-03-23 12:38:11',2,1,'Перевод между собственными счетами клиента Белых Евгений Владимирович','42307810700001234567',1,NULL,NULL,10000,1,NULL);
INSERT INTO `operation` (`id`,`create_date`,`account_id`,`operation_type_id`,`description`,`destination_account`,`partner_bank_id`,`number`,`execution_date`,`amount`,`status_id`,`comment`) VALUES (2,'2017-04-10 23:10:54',2,1,'Перевод между собственными счетами клиента Белых Евгений Владимирович','42307810700001234567',1,NULL,NULL,10000,1,NULL);
INSERT INTO `operation` (`id`,`create_date`,`account_id`,`operation_type_id`,`description`,`destination_account`,`partner_bank_id`,`number`,`execution_date`,`amount`,`status_id`,`comment`) VALUES (3,'2017-04-10 23:57:55',2,1,'Перевод внутри банка','12345678912345678900',1,NULL,NULL,5000,1,NULL);
INSERT INTO `operation` (`id`,`create_date`,`account_id`,`operation_type_id`,`description`,`destination_account`,`partner_bank_id`,`number`,`execution_date`,`amount`,`status_id`,`comment`) VALUES (4,'2017-04-13 00:56:20',2,4,'Блокировка карты','0',1,20423,'2017-04-13 22:11:20',0,2,'Карта успешно заблокирована');

INSERT INTO `operation_type` (`id`,`name`) VALUES (1,'Внутренний перевод');
INSERT INTO `operation_type` (`id`,`name`) VALUES (2,'Внешний перевод');
INSERT INTO `operation_type` (`id`,`name`) VALUES (3,'Оплата услуг');
INSERT INTO `operation_type` (`id`,`name`) VALUES (4,'Блокировка карты');
INSERT INTO `operation_type` (`id`,`name`) VALUES (5,'Выпуск виртуальной карты');

INSERT INTO `partner_bank` (`id`,`name`,`INN`,`KPP`,`BIK`,`corr_account`) VALUES (1,'ОАО \"Крайинвестбанк\"','2309074812','230901001','040349516','30101810500000000516');
INSERT INTO `partner_bank` (`id`,`name`,`INN`,`KPP`,`BIK`,`corr_account`) VALUES (2,'КБ \"Кубань Кредит\" ООО','2312016641','231001001','040349722','30101810200000000722');
INSERT INTO `partner_bank` (`id`,`name`,`INN`,`KPP`,`BIK`,`corr_account`) VALUES (3,'Банк «Возрождение» (ПАО)','5000001042','997950001','044525181','30101810900000000181');
INSERT INTO `partner_bank` (`id`,`name`,`INN`,`KPP`,`BIK`,`corr_account`) VALUES (4,'АО «АЛЬФА-БАНК»','7728168971','770801001','044525593','30101810200000000593');

INSERT INTO `provider_category` (`id`,`name`) VALUES (1,'Сотовая связь');
INSERT INTO `provider_category` (`id`,`name`) VALUES (2,'Интернет');
INSERT INTO `provider_category` (`id`,`name`) VALUES (3,'ЖКХ');

INSERT INTO `provider_category_has_service_provider` (`provider_category_id`,`service_provider_id`) VALUES (1,1);
INSERT INTO `provider_category_has_service_provider` (`provider_category_id`,`service_provider_id`) VALUES (2,1);
INSERT INTO `provider_category_has_service_provider` (`provider_category_id`,`service_provider_id`) VALUES (1,2);
INSERT INTO `provider_category_has_service_provider` (`provider_category_id`,`service_provider_id`) VALUES (1,3);
INSERT INTO `provider_category_has_service_provider` (`provider_category_id`,`service_provider_id`) VALUES (2,3);
INSERT INTO `provider_category_has_service_provider` (`provider_category_id`,`service_provider_id`) VALUES (1,4);
INSERT INTO `provider_category_has_service_provider` (`provider_category_id`,`service_provider_id`) VALUES (2,5);
INSERT INTO `provider_category_has_service_provider` (`provider_category_id`,`service_provider_id`) VALUES (3,6);
INSERT INTO `provider_category_has_service_provider` (`provider_category_id`,`service_provider_id`) VALUES (3,7);
INSERT INTO `provider_category_has_service_provider` (`provider_category_id`,`service_provider_id`) VALUES (3,8);

INSERT INTO `service_provider` (`id`,`name`,`INN`,`account_number`,`partner_bank_id`) VALUES (1,'МТС','7740000046','40702810500000001234',4);
INSERT INTO `service_provider` (`id`,`name`,`INN`,`account_number`,`partner_bank_id`) VALUES (2,'Мегафон','7812014560','40702810400000009876',3);
INSERT INTO `service_provider` (`id`,`name`,`INN`,`account_number`,`partner_bank_id`) VALUES (3,'Билайн','7713076301','40702810200000007531',4);
INSERT INTO `service_provider` (`id`,`name`,`INN`,`account_number`,`partner_bank_id`) VALUES (4,'Теле2','7743895330','40702810700000008426',3);
INSERT INTO `service_provider` (`id`,`name`,`INN`,`account_number`,`partner_bank_id`) VALUES (5,'Ростелеком','7707049388','40702810800000004378',4);
INSERT INTO `service_provider` (`id`,`name`,`INN`,`account_number`,`partner_bank_id`) VALUES (6,'ООО \"УК ЮРСК Сервис\"','2312231818','40702810700000003579',2);
INSERT INTO `service_provider` (`id`,`name`,`INN`,`account_number`,`partner_bank_id`) VALUES (7,'ООО \"ГУК-Краснодар\"','2311104687','40702810500000001577',2);
INSERT INTO `service_provider` (`id`,`name`,`INN`,`account_number`,`partner_bank_id`) VALUES (8,'ООО \"Кубань-Сервис\"','2311121347','40702810400000005931',3);

INSERT INTO `status` (`id`,`name`) VALUES (1,'Новый');
INSERT INTO `status` (`id`,`name`) VALUES (2,'Исполнено');
INSERT INTO `status` (`id`,`name`) VALUES (3,'Не исполнено');



