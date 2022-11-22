CREATE TABLE `users` (
	`id` VARCHAR NOT NULL,
	`first_name` VARCHAR NOT NULL,
	`last_name` VARCHAR NOT NULL,
	`display_name` VARCHAR NOT NULL,
	`avatar_url` VARCHAR,
	`location` VARCHAR,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `teams` (
	`id` VARCHAR NOT NULL,
	`name` VARCHAR NOT NULL,
	`team_lead_id` VARCHAR,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `team_member` (
	`user_id` VARCHAR NOT NULL,
	`team_id` VARCHAR NOT NULL,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	PRIMARY KEY (`user_id`,`team_id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`id`),
    FOREIGN KEY (`team_id`) REFERENCES teams(`id`)
);

CREATE TABLE `roles` (
	`id` VARCHAR NOT NULL,
	`code` VARCHAR NOT NULL,
	`name` VARCHAR NOT NULL,
	`is_default` BOOLEAN NOT NULL,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `memberships` (
	`id` VARCHAR NOT NULL,
	`role_code` VARCHAR NOT NULL,
	`user_id` VARCHAR NOT NULL,
	`team_id` VARCHAR NOT NULL,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`id`),
    FOREIGN KEY (`team_id`) REFERENCES teams(`id`),
    FOREIGN KEY (`role_code`) REFERENCES roles(`code`)
);
