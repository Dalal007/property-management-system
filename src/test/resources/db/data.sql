use propertiesdb;

INSERT INTO propertiesdb.roles ('role_id', 'name') VALUES (1, 'ADMIN');
INSERT INTO propertiesdb.roles ('role_id', 'name') VALUES (2, 'LANDLORD');
INSERT INTO propertiesdb.roles ('role_id', name) VALUES (3, 'USER');

INSERT INTO propertiesdb.users ('user_id', 'user_name', 'password', 'enabled', 'contact_no') VALUES (1, 'admin', 'admin', true, 9408056444);
INSERT INTO propertiesdb.users ('user_id', 'user_name', 'password', 'enabled', 'contact_no') VALUES (2, 'landlord', 'landlord', true, 8460307260);
INSERT INTO propertiesdb.users ('user_id', 'user_name', 'password', 'enabled', 'contact_no') VALUES (3, 'user', 'user', true, 9408056444);

INSERT INTO propertiesdb.users_roles ('users_user_id', 'roles_role_id') VALUES (1,1);
INSERT INTO propertiesdb.users_roles ('users_user_id', 'roles_role_id') VALUES (2,2);
INSERT INTO propertiesdb.users_roles ('users_user_id', 'roles_role_id') VALUES (3,3);

INSERT INTO propertiesdb.properties ('property_id', 'name', 'type', 'location', 'price', 'verified', 'users_user_id') VALUES (1, '4bhk', 'bunglow', 'ahmedabad', 7000000, true, 2);
