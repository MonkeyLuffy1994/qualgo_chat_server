
CREATE DATABASE IF NOT EXISTS qualgo_chat;

USE qualgo_chat;

CREATE TABLE UserInfo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);


CREATE TABLE Message (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    room_id INT,
    content TEXT,
    sent_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES UserInfo(id)
);
