-- Some initial data

-- Companies
INSERT INTO `Companies` (`id`, `active`, `city`, `country`, `department`, `name`, `street`, `zip_code`)
VALUES ('100', true, 'Esslingen 1', 'Germany 1', 'yeew 1', 'name 1', 'zuhause 1', 'hhhhh 1'),
       ('101', true, 'Esslingen 2', 'Germany 2', 'yeew 2', 'name 2', 'zuhause 2', 'hhhhh 2'),
       ('102', true, 'Esslingen 3', 'Germany 3', 'yeew 3', 'name 3', 'zuhause 3', 'hhhhh 3'),
       ('103', true, 'Esslingen 4', 'Germany 4', 'yeew 4', 'name 4', 'zuhause 4', 'hhhhh 4')
;

-- Users
INSERT INTO Users (id, username, password, is_admin, first_name, last_name, email, phone, mobile, active, company_id)
VALUES ('100', 'admin', 'admin', true, 'Admin', 'Administrator', 'ich@admin.de', '', '', true, 100),
       ('101', 'max', 'user', false, 'Max', 'Mustermann', 'max@muster.de', '0711-12345', '', true, 102),
       ('102', 'klara', 'user', false, 'Klara', 'Musterfrau', 'klara@musterfrau.de', '0711-12345', '', true, 100),
       ('103', 'carla', 'user', true, 'Carla', 'Carlatti', 'carlac@muster.de', '0711-12345', '', true, 100),
       ('104', 'SiggiTheBest', 'user', false, 'Siggi', 'Best', 'siggi@best.de', '0711-12345', '', true, 102),
       ('105', 'paulinchen', 'user', false, 'Paula', 'Inchen', 'paula@wow.de', '0711-12345', '', true, 103)
;

-- Contracts
INSERT INTO Contracts (id, active, date_start, date_stop, version, license_key, feature_1, feature_2, feature_3, ip_1,
                       ip_2, ip_3, company_id, user1_id, user2_id)
VALUES ('100', true, DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), '01', '12345', '01',
        '02', '03', '192.172.1.0', '', '', 100, 100, null),
       ('101', true, DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), '02', '98765', '01',
        '02', '03', '192.172.1.0', '', '', 101, 101, null),
       ('102', true, DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), '03', '98765', '01',
        '02', '03', '192.172.1.0', '', '', 101, 100, null),
       ('103', true, DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), '04', '98765', '01',
        '02', '03', '192.172.1.0', '', '', 101, 101, null),
       ('104', true, DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), '05', '98765', '01',
        '02', '03', '192.172.1.0', '', '', 101, null, null),
       ('105', true, DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), DATE_FORMAT(CURRENT_DATE, '%d.%m.%Y'), '06', '98765', '01',
        '02', '03', '192.172.1.0', '', '', 101, 100, 101)
;