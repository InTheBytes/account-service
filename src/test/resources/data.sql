INSERT INTO role (name)
	VALUES ('Admin');

INSERT INTO user (user_role, username, password, email, phone, first_name, last_name, active)
	VALUES (1, 'mosaab', '$2a$10$YMGP6L6Pv553Fr/0eSgPK.fMsD2iH87x5L17vCcx4HQXkiDAQDIM6', 'test@example.com', '0000000000', 'Mosaab', 'A', 0);

INSERT INTO user (user_role, username, password, email, phone, first_name, last_name, active)
	VALUES (1, 'verified', '$2a$10$YMGP6L6Pv553Fr/0eSgPK.fMsD2iH87x5L17vCcx4HQXkiDAQDIM6', 'verified@example.com', '0000000000', 'Verified', 'U', 1);

INSERT INTO user (user_role, username, password, email, phone, first_name, last_name, active)
VALUES (1, 'waiting', '$2a$10$YMGP6L6Pv553Fr/0eSgPK.fMsD2iH87x5L17vCcx4HQXkiDAQDIM6', 'waiting@example.com', '0000000000', 'Waiting', 'V', 0);

INSERT INTO confirmation (confirmation_token, user_id, created_date, is_confirmed)
	VALUES ('123', 2, '2021-04-06 11:00:00', 1);

INSERT INTO confirmation (confirmation_token, user_id, created_date, is_confirmed)
	VALUES ('validtoken', 3, NOW(), 0);
