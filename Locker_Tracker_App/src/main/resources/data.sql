insert into User (email, password, enabled) VALUES ('gyula@something.com', '$2a$10$kOIWxlkxbU1NtqSF/Q2r9enhORF4KDogYjqCMCilex9mlF7z0KRne', true);
insert into User (email, password, enabled) VALUES ('csilla@something.com', '$2a$10$X0.amibuDw/3giiohc2bhODu9O2V8JmPNJvJ0xCas.qdDKxguiuK2', true);
insert into User (email, password, enabled) VALUES ('alma@something.com', '$2a$10$X0.amibuDw/3giiohc2bhODu9O2V8JmPNJvJ0xCas.qdDKxguiuK2', true);
insert into User (email, password, enabled) VALUES ('endre@something.com', '$2a$10$X0.amibuDw/3giiohc2bhODu9O2V8JmPNJvJ0xCas.qdDKxguiuK2', true);
insert into User (email, password, enabled) VALUES ('laci@something.com', '$2a$10$X0.amibuDw/3giiohc2bhODu9O2V8JmPNJvJ0xCas.qdDKxguiuK2', true);

insert into Locker (id) values (1);
insert into Locker (id) values (2);
insert into Locker (id) values (3);
insert into Locker (id) values (4);
insert into Locker (id) values (5);
insert into Locker (id) values (6);
insert into Locker (id) values (7);
insert into Locker (id) values (8);
insert into Locker (id) values (9);
insert into Locker (id) values (10);

insert into Locker_user (user_id, locker_id) values ((select id FROM USER where email like '%alma%'), 3);
insert into Locker_user (user_id, locker_id) values ((select id FROM USER where email like '%gyula%'), 2);
insert into Locker_user (user_id, locker_id) values ((select id FROM USER where email like '%gyula%'), 6);
insert into Locker_user (user_id, locker_id) values ((select id FROM USER where email like '%csilla%'), 5);
insert into Locker_user (user_id, locker_id) values ((select id FROM USER where email like '%laci%'), 1);
insert into Locker_user (user_id, locker_id) values ((select id FROM USER where email like '%endre%'), 4);



