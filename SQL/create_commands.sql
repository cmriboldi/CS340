CREATE TABLE command
(
command_id INT PRIMARY KEY NOT NULL,
game_id INT NOT NULL,
order_of_execution INT NOT NULL,
command BLOB NOT NULL,
);