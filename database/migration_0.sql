--1. create user --
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`                        VARCHAR(32)  NOT NULL,

    `user_name`                 VARCHAR(100),
    `password`                  VARCHAR(256),
    `first_name`                VARCHAR(100),
    `last_name`                 VARCHAR(100),
    `email_address`             VARCHAR(100),
    `hash_salt`                 VARCHAR(256),
    `phone`                     VARCHAR(256),
    `role`                      TINYINT(4),

    `last_logged_in_read`       DATETIME,
    `last_logged_in_write`      DATETIME,
    `last_logged_in_ip_address` VARCHAR(50),
    `last_logged_in_user_agent` VARCHAR(500),

    `version`                   INT(11) NOT NULL,
    `created_by`                VARCHAR(32),
    `updated_by`                VARCHAR(32),
    `created_on`                DATETIME,
    `updated_on`                DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE (`email_address`),
    UNIQUE (`user_name`)
) Engine = InnoDB;
