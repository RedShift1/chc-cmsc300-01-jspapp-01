USE admonitus;

INSERT INTO users (email, password, picture_path, activation_key, active, creation_date) VALUES ('testmail@mwebmaster.com', '9f735e0df9a1ddc702bf0a1a7b83033f9f7153a00c29de82cedadc9957289b05', 'dummy/path/src/img/landscape.jpg', '442312', '1', now());
INSERT INTO users (email, password, picture_path, activation_key, active, creation_date) VALUES ('stephenhawking@cambridge.edu', 'e7edd6da7bc67389f20bdf84c95fd5c852b1585f648f209822a012f2cd9daf5a', 'dummy/path/src/img/landscape.jpg', '242134', '1', now());

INSERT INTO reminders(text, frequency, datestart, user_id, creation_date) VALUES ('Study', '2', '2015-9-15', '0', now());
INSERT INTO reminders(text, frequency, datestart, user_id, creation_date) VALUES ('Call Mom', '1', '2015-9-20', '0', now());
INSERT INTO reminders(text, frequency, datestart, user_id, creation_date) VALUES ('Finish assignment', '4', '2015-9-16', '1', now());
INSERT INTO reminders(text, frequency, datestart, user_id, creation_date) VALUES ('Pick up kids from school', '1', '2015-10-25', '1', now());
INSERT INTO reminders(text, frequency, datestart, user_id, creation_date) VALUES ('Throw party for friends', '3', '2015-11-25', '1', now());
INSERT INTO reminders(text, frequency, datestart, user_id, creation_date) VALUES ('Work on car', '2', '2015-12-1', '0', now());
INSERT INTO reminders(text, frequency, datestart, user_id, creation_date) VALUES ('Clean coffeemaker', '4', '2015-9-19', '1', now());