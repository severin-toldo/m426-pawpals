INSERT INTO security_role (id, name) VALUES (1, 'ADMIN');

INSERT INTO address (id, street, state, city, zip) VALUES (1, 'street', 'state', 'city', '1234');

INSERT INTO user (id, email, firstname, lastname, password, phone_number, address_id_fk) VALUES (1, 'admin@admin.com', 'admin', 'user', '$2a$10$8GF/pBJPLbAXrQXRMNeurOfZxQRtH/UXuiYZxSrbpTnStXB.ZooVa', '1234567890', 1);

INSERT INTO user_security_roles (user_id, security_roles_id) VALUES (1, 1);





	
	
