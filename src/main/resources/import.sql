INSERT INTO comunidades(nombre) VALUES ('comunidad de madrid');
INSERT INTO comunidades(nombre) VALUES ('Castilla y Leon');
INSERT INTO comunidades(nombre) VALUES ('Catalunya');
INSERT INTO comunidades(nombre) VALUES ('Valencia');
INSERT INTO comunidades(nombre) VALUES ('Bilbao');
INSERT INTO comunidades(nombre) VALUES ('Oceania');
INSERT INTO comunidades(nombre) VALUES ('Africa');
INSERT INTO comunidades(nombre) VALUES ('Antartida');

INSERT INTO alumnos(comunidad_id,nombre,apellido,dni,email,telefono,direccion,cp) VALUES(1,"Jose", "Perez","54286211R", "jp@email.com",600938754,"calle Real",28991)
INSERT INTO alumnos(comunidad_id,nombre,apellido,dni,email,telefono,direccion,cp) VALUES(2,"Javier", "Palomo", "7319211D", "jp@email.com",600938754,"calle Real",28991)
INSERT INTO alumnos(comunidad_id,nombre,apellido,dni,email,telefono,direccion,cp) VALUES(3,"Juan", "Montiel","54286211R", "jp@email.com",600938754,"calle Real",28991)
INSERT INTO alumnos(comunidad_id,nombre,apellido,dni,email,telefono,direccion,cp) VALUES(4,"Manuel", "Dominguez","54286211R", "jp@email.com",600938754,"calle Real",28991)
INSERT INTO alumnos(comunidad_id,nombre,apellido,dni,email,telefono,direccion,cp) VALUES(5,"Alex", "Moro", "54286211R", "jp@email.com",600938754,"calle Real",28991)
INSERT INTO alumnos(comunidad_id,nombre,apellido,dni,email,telefono,direccion,cp) VALUES(6,"Alvaro", "Cota","54286211R", "jp@email.com",600938754,"calle Real",28991)
INSERT INTO alumnos(comunidad_id,nombre,apellido,dni,email,telefono,direccion,cp) VALUES(7,"Ruben", "Zazo", "54286211R", "jp@email.com",600938754,"calle Real",28991)
INSERT INTO alumnos(comunidad_id,nombre,apellido,dni,email,telefono,direccion,cp) VALUES(8,"Nacho", "Nuñez", "54286211R", "jp@email.com",600938754,"calle Real",28991)
INSERT INTO alumnos(comunidad_id,nombre,apellido,dni,email,telefono,direccion,cp) VALUES(9,"Robert", "Molina","54286211R", "jp@email.com",600938754,"calle Real",28991)

INSERT INTO usuarios(username,password,enabled) VALUES ('Juan', '$2a$10$y7ZSjG/C7hkSrzw44XcXFeTdkYoSEATyxEQ2v7FyeMU9PoI2wb42a',1);
INSERT INTO usuarios(username,password,enabled) VALUES ('admin', '$2a$10$KhVj3.j0IKM0bZtFMlA0ZOHrJDPdJrJSyOpnHuf/vQX65RSCRKVKy',1);
INSERT INTO roles(nombre) VALUES('ROLE_USER');
INSERT INTO roles(nombre) VALUES('ROLE_ADMIN');


INSERT INTO usuarios_roles (usuario_id,role_id) VALUES(1,1);
INSERT INTO usuarios_roles (usuario_id,role_id) VALUES(2,2);
INSERT INTO usuarios_roles (usuario_id,role_id) VALUES(2,1);