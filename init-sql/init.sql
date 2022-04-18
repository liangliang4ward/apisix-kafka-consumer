CREATE TABLE `day_count` (
  `id` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `count` bigint(20) DEFAULT NULL,
  `success_count` bigint(20) DEFAULT NULL,
  `about_5xx_count` bigint(20) DEFAULT NULL,
  `about_4xx_count` bigint(20) DEFAULT NULL,
  `service_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `username` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `count_date` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `fiveminute_count` (
  `id` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `count` bigint(20) DEFAULT NULL,
  `success_count` bigint(20) DEFAULT NULL,
  `about_5xx_count` bigint(20) DEFAULT NULL,
  `about_4xx_count` bigint(20) DEFAULT NULL,
  `service_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `username` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `count_date` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE `month_count` (
  `id` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `count` bigint(20) DEFAULT NULL,
  `success_count` bigint(20) DEFAULT NULL,
  `about_5xx_count` bigint(20) DEFAULT NULL,
  `about_4xx_count` bigint(20) DEFAULT NULL,
  `service_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `username` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `count_date` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `service_id` (`service_id`),
  KEY `service_id_2` (`service_id`,`username`),
  KEY `service_id_3` (`service_id`,`username`,`count_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE `year_count` (
  `id` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `count` bigint(20) DEFAULT NULL,
  `success_count` bigint(20) DEFAULT NULL,
  `about_5xx_count` bigint(20) DEFAULT NULL,
  `about_4xx_count` bigint(20) DEFAULT NULL,
  `service_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `username` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `count_date` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `service_id` (`service_id`),
  KEY `username` (`username`),
  KEY `service_id_2` (`service_id`,`username`,`count_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;