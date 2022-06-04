SET NAMES 'utf8';
DROP DATABASE IF EXISTS ventas;
CREATE DATABASE IF NOT EXISTS ventas DEFAULT CHARACTER SET utf8;
USE ventas;
CREATE TABLE clientes(
id_clientes					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_clientes				VARCHAR(25) NOT NULL, 
apellido_clientes			VARCHAR(25) NOT NULL
)DEFAULT CHARACTER SET utf8;

INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente1','Apellido1');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente2','Apellido2');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente3','Apellido3');
 
CREATE TABLE facturas(
id_facturas					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
id_clientes					INTEGER NOT NULL,
referencia_facturas		    VARCHAR(40) NOT NULL,
fecha_facturas				DATE NOT NULL, 
FOREIGN KEY(id_clientes) REFERENCES clientes(id_clientes)
)DEFAULT CHARACTER SET utf8;

INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(1,'FAC1231',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(2,'FAC1131',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(3,'FAC1331',NOW());

CREATE TABLE productos(
id_productos					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_productos				VARCHAR(80) NOT NULL, 
precio_productos				DOUBLE NOT NULL
)DEFAULT CHARACTER SET utf8;

INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto1',10.23);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto2',1.12);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto3',23.30);

CREATE TABLE facturas_productos(
id_facturas					INTEGER NOT NULL,
id_productos					INTEGER NOT NULL,
cantidad_facturas_productos	DOUBLE NOT NULL,
PRIMARY KEY(id_facturas,id_productos),
FOREIGN KEY(id_facturas) REFERENCES facturas(id_facturas),
FOREIGN KEY(id_productos) REFERENCES productos(id_productos)
)DEFAULT CHARACTER SET utf8;

INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,1,120);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,2,20);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,2,10);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,1,70);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,3,7);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(3,1,17);

create view todosclientes as select * from clientes;
create view todosProductos as select * from productos;


DELIMITER $$
create trigger clientes_mayus before insert on clientes for each row
begin
  set new.nombre_clientes=upper(new.nombre_clientes);
  set new.apellido_clientes=upper(new.apellido_clientes);
end $$
DELIMITER ;

DELIMITER $$
create trigger clientes_mayus_up before update on clientes for each row
begin
  set new.nombre_clientes=upper(new.nombre_clientes);
  set new.apellido_clientes=upper(new.apellido_clientes);
end $$
DELIMITER ;

DELIMITER $$
create trigger productos_mayus before insert on productos for each row
begin
  set new.nombre_productos=upper(new.nombre_productos);  
end $$
DELIMITER ;

DELIMITER $$
create trigger productos_mayus_up before update on productos for each row
begin
  set new.nombre_productos=upper(new.nombre_productos);  
end $$
DELIMITER ;

DELIMITER $$
create trigger facturas_mayus before insert on facturas for each row
begin
  set new.referencia_facturas=upper(new.referencia_facturas);  
end $$
DELIMITER ;

DELIMITER $$
create trigger facturas_mayus_up before update on facturas for each row
begin
  set new.referencia_facturas=upper(new.referencia_facturas);  
end $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE todosClientes()
BEGIN
	select * from todosClientes;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE todosProductos()
BEGIN
	select * from todosProductos;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE todosFacturaProd()
BEGIN
	select a.id_facturas,b.referencia_facturas,b.fecha_facturas,a.cantidad_facturas_productos,c.nombre_productos,c.precio_productos,c.id_productos
	from facturas_productos a, facturas b, productos c where a.id_facturas=b.id_facturas and a.id_productos=c.id_productos;
END $$
DELIMITER ;


DELIMITER $$

CREATE  PROCEDURE insertarClientes(in nombre varchar(25),in apellido varchar(25))
BEGIN
	insert into clientes(nombre_clientes,apellido_clientes) values(nombre,apellido);
END $$

CREATE  PROCEDURE insertarProductos(in nombre varchar(80),in precio double)
BEGIN
	insert into productos(nombre_productos ,precio_productos) values(nombre,precio);
END $$

CREATE  PROCEDURE insertarFacturas(in idC int, in referencia varchar(40),in fecha date)
BEGIN
	insert into facturas(id_clientes ,referencia_facturas, fecha_facturas) values(idC,referencia,fecha);
END $$

CREATE  PROCEDURE insertarFacturaProd(in idF int, in idP int, in cantidad double)
BEGIN
	insert into facturas_productos(id_facturas ,id_productos, cantidad_facturas_productos) values(idF,idP,cantidad);
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarClientes(in id int)
BEGIN
	delete from clientes where id_clientes=id;
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarProductos(in id int)
BEGIN
	delete from productos where id_productos=id;
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarFactura(in idf int)
BEGIN
	delete from facturas where id_facturas=idf;
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarFacturaProd(in idF int, in idP int)
BEGIN
	delete from facturas_productos where id_facturas=idF and id_productos=idP;
END $$

DELIMITER $$
CREATE  PROCEDURE modificarClientes(in id int,in nombre varchar(25),in apellido varchar(25))
BEGIN
	update clientes set nombre_clientes=nombre,apellido_clientes=apellido where id_clientes=id;
END $$

DELIMITER $$
CREATE PROCEDURE modificarProductos(in id int,in nombre varchar(80),in precio double)
BEGIN
	update productos set nombre_productos=nombre,precio_productos=precio where id_productos=id;
END $$

DELIMITER $$
CREATE PROCEDURE modificarFacturas(in id int,in idC int,in referencia varchar(40),in fecha date)
BEGIN
	update facturas set referencia_facturas=referencia, fecha_facturas=fecha, id_clientes=idC where id_facturas=id;
END $$

DELIMITER $$
CREATE PROCEDURE modificarFacturaProd(in idF int,in idP int,in cantidad double, in idNF int,in idNP int)
BEGIN
	update facturas_productos set id_facturas=idNF, id_productos=idNP, cantidad_facturas_productos=cantidad where id_facturas=idF and id_productos=idP;
END $$


DELIMITER $$
CREATE  PROCEDURE buscarClientes(in patron varchar(15))
BEGIN
	select * from clientes where nombre_clientes like concat('%',patron,'%');
END $$

DELIMITER $$
CREATE  PROCEDURE buscarProductos(in patron varchar(80))
BEGIN
	select * from productos where nombre_productos like concat('%',patron,'%');
END $$

DELIMITER $$
CREATE  PROCEDURE buscarFact(in patron varchar(40))
BEGIN
		select a.id_facturas,a.referencia_facturas,a.fecha_facturas,b.id_clientes,b.nombre_clientes,b.apellido_clientes 
		from facturas a,clientes b where a.id_clientes=b.id_clientes and a.referencia_facturas like concat('%',patron,'%');
END $$

DELIMITER $$
CREATE  PROCEDURE buscarFacturas()
BEGIN
	select a.id_facturas,a.referencia_facturas,a.fecha_facturas,b.id_clientes,b.nombre_clientes,b.apellido_clientes 
		from facturas a,clientes b where a.id_clientes=b.id_clientes;
END $$
DELIMITER ;
-- cascadas

ALTER TABLE `ventas`.`facturas_productos` 
DROP FOREIGN KEY `facturas_productos_ibfk_1`,
DROP FOREIGN KEY `facturas_productos_ibfk_2`;
ALTER TABLE `ventas`.`facturas_productos` 
ADD CONSTRAINT `facturas_productos_ibfk_1`
  FOREIGN KEY (`id_facturas`)
  REFERENCES `ventas`.`facturas` (`id_facturas`)
  ON DELETE CASCADE,
ADD CONSTRAINT `facturas_productos_ibfk_2`
  FOREIGN KEY (`id_productos`)
  REFERENCES `ventas`.`productos` (`id_productos`)
  ON DELETE CASCADE;
  
  ALTER TABLE `ventas`.`facturas` 
DROP FOREIGN KEY `facturas_ibfk_1`;
ALTER TABLE `ventas`.`facturas` 
ADD CONSTRAINT `facturas_ibfk_1`
  FOREIGN KEY (`id_clientes`)
  REFERENCES `ventas`.`clientes` (`id_clientes`)
  ON DELETE CASCADE;
  
use ventas;