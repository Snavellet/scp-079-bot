CREATE SCHEMA IF NOT EXISTS servers;

CREATE TABLE IF NOT EXISTS `servers`.`skids`
(
    `id`       INT(25)     NOT NULL AUTO_INCREMENT,
    `guild_id` VARCHAR(25) NOT NULL,
    `user_id`  VARCHAR(25),
    UNIQUE KEY `user_id_unique` (`user_id`) USING BTREE,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `servers`.`warnings`
(
    `id`            INT(25)     NOT NULL AUTO_INCREMENT,
    `guild_id`      VARCHAR(25) NOT NULL,
    `user_id`       VARCHAR(25) NOT NULL,
    `moderator_id`  VARCHAR(25) NOT NULL,
    `moderator_tag` VARCHAR(25) NOT NULL,
    `date_ms`       BIGINT(25),
    `reason`        VARCHAR(100),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
