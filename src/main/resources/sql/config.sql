DROP SCHEMA IF EXISTS servers;

CREATE SCHEMA servers;

CREATE TABLE `servers`.`skids`
(
    `id`       INT(25)     NOT NULL AUTO_INCREMENT,
    `guild_id` VARCHAR(25) NOT NULL,
    `user_id`  VARCHAR(25),
    UNIQUE KEY `user_id_unique` (`user_id`) USING BTREE,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;