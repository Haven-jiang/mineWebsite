CREATE TABLE `user_youth_data` (
                                   `nid` varchar(25) NOT NULL,
                                   `userid` varchar(25) NOT NULL,
                                   `uuid` varchar(30) NOT NULL,
                                   PRIMARY KEY (`nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `youth_data_course` (
                                     `course` varchar(6) NOT NULL DEFAULT 'course',
                                     `id` varchar(10) DEFAULT NULL,
                                     PRIMARY KEY (`course`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



