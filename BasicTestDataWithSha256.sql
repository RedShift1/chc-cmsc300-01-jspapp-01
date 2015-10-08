USE admonitus;

INSERT INTO users (email, password, picture_path, activation_key, active) VALUES ('glenn@mwebmaster.com', '9f735e0df9a1ddc702bf0a1a7b83033f9f7153a00c29de82cedadc9957289b05', 'dummy/path/src/img/landscape.jpg', '23234', '1');
INSERT INTO users (email, password, picture_path, activation_key, active) VALUES ('turing@alan.com', '9f735e0df9a1ddc702bf0a1a7b83033f9f7153a00c29de82cedadc9957289b05', 'dummy/path/src/img/landscape.jpg', '64232', '1');

INSERT INTO reminders(text, frequency, datestart, user_id) VALUES ('Remember the laundry', '2', '2015-9-15', '1');
INSERT INTO reminders(text, frequency, datestart, user_id) VALUES ('Do homework', '1', '2015-9-20', '1');
INSERT INTO reminders(text, frequency, datestart, user_id) VALUES ('Cook dinner', '4', '2015-9-16', '1');
INSERT INTO reminders(text, frequency, datestart, user_id) VALUES ('Pick up kids', '1', '2015-10-25', '2');
INSERT INTO reminders(text, frequency, datestart, user_id) VALUES ('Fly airplane', '3', '2015-11-25', '3');
INSERT INTO reminders(text, frequency, datestart, user_id) VALUES ('Set alarm clock', '2', '2015-12-1', '3');
INSERT INTO reminders(text, frequency, datestart, user_id) VALUES ('Write book', '4', '2015-9-19', '3');