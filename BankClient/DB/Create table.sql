CREATE DATABASE `bankclientdb` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор счета клиента',
  `client_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор клиента',
  `account_type_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор типа счета клиента',
  `currency_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор валюты счета',
  `number` varchar(45) NOT NULL COMMENT 'Номер счета клиента',
  `balance` double NOT NULL DEFAULT '0' COMMENT 'Текущий остаток денежных средств на счете клиента',
  `overlimit` double NOT NULL DEFAULT '0' COMMENT 'Размер овердрафта по счету',
  `interest_rate` float NOT NULL DEFAULT '0' COMMENT 'Ставка процента по счету (для вкладов и дебетовых карт)',
  `loan_rate` float NOT NULL DEFAULT '0' COMMENT 'Ставка по кредиту и овердрафту',
  `card_number` varchar(45) DEFAULT NULL COMMENT 'Номер карты (если карточный счет)',
  `blocked` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Флаг блокировки счета',
  `expiration_date` datetime DEFAULT NULL COMMENT 'Дата окончания срока действия карты',
  `CVV` varchar(3) DEFAULT NULL COMMENT 'CVV2 для карт Visa или CVC2 для карт MasterCard',
  `account_name` char(150) NOT NULL 'Наименование счета',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `client_idx` (`client_id`),
  KEY `account_type_idx` (`account_type_id`),
  KEY `currency_idx` (`currency_id`),
  CONSTRAINT `account_type` FOREIGN KEY (`account_type_id`) REFERENCES `account_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `currency` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `account_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор типа счета клиента',
  `name` varchar(45) NOT NULL COMMENT 'Наименование типа счета клиента',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `bank_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникильный идентификатор сообщения',
  `client_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор клиента',
  `text` varchar(200) NOT NULL COMMENT 'Текст сообщения',
  `message_date` datetime DEFAULT NULL 'Дата сообщения',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `client_idx` (`client_id`),
  CONSTRAINT `client_message` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор клиента',
  `name` varchar(100) NOT NULL COMMENT 'Наименование клиента',
  `login` varchar(50) NOT NULL COMMENT 'Логин клиента для доступа в систему',
  `password` varchar(45) NOT NULL COMMENT 'Пароль для входа в систему',
  `blocked` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Флаг для блокировки доступа клиента к системе',
  `admin` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Флаг для администратора системы',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `currency` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор валюты',
  `name` varchar(45) NOT NULL COMMENT 'Наименование валюты',
  `code` varchar(3) NOT NULL COMMENT 'Код валюты',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `deposit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `interest_rate` float NOT NULL,
  `duration` int(11) NOT NULL,
  `discription` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор операции',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Дата создания операции',
  `account_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор счета клиента',
  `operation_type_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор типа операции',
  `description` varchar(200) NOT NULL COMMENT 'Назначение платежа',
  `destination_account` varchar(20) NOT NULL COMMENT 'Номер счета контрагента',
  `partner_bank_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор банка-контрагента',
  `number` int(11) DEFAULT NULL COMMENT 'Номер операции',
  `execution_date` datetime DEFAULT NULL COMMENT 'Дата исполнения операции',
  `amount` double NOT NULL COMMENT 'Сумма операции',
  `status_id` int(11) NOT NULL 'Уникальный идентификатор статуса',
  `comment` varchar(200) DEFAULT NULL COMMENT 'Комментарий',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `status_idx` (`status_id`),
  KEY `account_idx` (`account_id`),
  KEY `operation_type_idx` (`operation_type_id`),
  KEY `partner_bank_idx` (`partner_bank_id`),
  CONSTRAINT `account` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `operation_type_operation` FOREIGN KEY (`operation_type_id`) REFERENCES `operation_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `partner_bank_operation` FOREIGN KEY (`partner_bank_id`) REFERENCES `partner_bank` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `status` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `operation_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор типа операции',
  `name` varchar(45) NOT NULL COMMENT 'Наименование типа операции',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `partner_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор банка-контрагента',
  `name` varchar(100) NOT NULL COMMENT 'Наименование банка-контрагента',
  `INN` varchar(10) NOT NULL COMMENT 'ИНН банка-контрагента',
  `KPP` varchar(10) NOT NULL COMMENT 'КППмбанка-контрагента',
  `BIK` varchar(10) NOT NULL COMMENT 'БИК банка-контрагента',
  `corr_account` varchar(20) NOT NULL COMMENT 'Корреспондентский счет банка-контрагента',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `provider_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор категории поставщиков услуг',
  `name` varchar(100) NOT NULL COMMENT 'Наименование категории поставщика услуг',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `provider_category_has_service_provider` (
  `provider_category_id` int(11) NOT NULL,
  `service_provider_id` int(11) NOT NULL,
  PRIMARY KEY (`provider_category_id`,`service_provider_id`),
  KEY `fk_provider_category_has_service_provider_service_provider1_idx` (`service_provider_id`),
  KEY `fk_provider_category_has_service_provider_provider_category_idx` (`provider_category_id`),
  CONSTRAINT `fk_provider_category_has_service_provider_provider_category1` FOREIGN KEY (`provider_category_id`) REFERENCES `provider_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_provider_category_has_service_provider_service_provider1` FOREIGN KEY (`service_provider_id`) REFERENCES `service_provider` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `service_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор поставщика услуг',
  `name` varchar(100) NOT NULL COMMENT 'Наименование поставщика услуг',
  `INN` varchar(10) NOT NULL COMMENT 'ИНН поставщика услуг',
  `account_number` varchar(20) NOT NULL COMMENT 'Номер банковского счета поставщика услуг',
  `partner_bank_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор банка поставщика услуг',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `partner_bank_idx` (`partner_bank_id`),
  CONSTRAINT `partner_bank_service_provider` FOREIGN KEY (`partner_bank_id`) REFERENCES `partner_bank` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE `status` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор статуса',
  `name` varchar(45) NOT NULL COMMENT 'Наименование статуса',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор шаблона',
  `account_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор счета клиента',
  `operation_type_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор типа операции',
  `description` varchar(200) NOT NULL COMMENT 'Назначение платежа',
  `destination_account` varchar(20) NOT NULL COMMENT 'Номер счета контрагента',
  `partner_bank_id` int(11) NOT NULL COMMENT 'Уникальный идентификатор банка-контрагента',
  `name` char(150) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `account_idx` (`account_id`),
  KEY `operation_type_idx` (`operation_type_id`),
  KEY `partner_bank_idx` (`partner_bank_id`),
  CONSTRAINT `account0` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `operation_type_template` FOREIGN KEY (`operation_type_id`) REFERENCES `operation_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `partner_bank_template` FOREIGN KEY (`partner_bank_id`) REFERENCES `partner_bank` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
