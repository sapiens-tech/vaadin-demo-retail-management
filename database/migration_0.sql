--1. create user --
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`                        VARCHAR(32)  NOT NULL,

    `user_name`                 VARCHAR(100) NOT NULL,
    `password`                  VARCHAR(256) NOT NULL,
    `first_name`                VARCHAR(100) NOT NULL,
    `last_name`                 VARCHAR(100) NOT NULL,
    `email_address`             VARCHAR(100) NOT NULL,
    `hash_salt`                 VARCHAR(256) NOT NULL,
    `phone`                     VARCHAR(256),
    `role`                      TINYINT(4) NOT NULL,

    `last_logged_in_read`       DATETIME NULL,
    `last_logged_in_write`      DATETIME NULL,
    `last_logged_in_ip_address` VARCHAR(50) NULL,
    `last_logged_in_user_agent` VARCHAR(500) NULL,

    `version`                   INT(11) NOT NULL,
    `created_by`                VARCHAR(32) NULL,
    `updated_by`                VARCHAR(32) NULL,
    `created_on`                DATETIME     NOT NULL,
    `updated_on`                DATETIME     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`email_address`),
    UNIQUE (`user_name`)
) Engine = InnoDB;
