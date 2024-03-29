insert into user_details(id, birth_date, name) values (1000, current_date(), 'Steve');

insert into user_details(id, birth_date, name) values (1001, current_date(), 'Author');

insert into user_details(id, birth_date, name) values (1002, current_date(), 'Nigel');

insert into post (id, description, user_id) values (2001, 'New Post!', 1002);
insert into post (id, description, user_id) values (2002, 'Newer Post!', 1002);
insert into post (id, description, user_id) values (2003, 'New Post Again!', 1002);
insert into post (id, description, user_id) values (2004, 'Hi, another New Post!', 1002);


-- current_date() is an h2 method