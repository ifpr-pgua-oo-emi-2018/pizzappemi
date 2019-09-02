--
-- File generated with SQLiteStudio v3.2.1 on qui ago 15 14:54:47 2019
--
-- Text encoding used: UTF-8
--

DROP DATABASE IF EXISTS pizzapp;
CREATE DATABASE pizzapp;

USE pizzapp;

CREATE USER 'user'@'%' IDENTIFIED BY 'user12345';

GRANT ALL ON pizzapp.* TO 'user'@'%';

-- Table: clientes
DROP TABLE IF EXISTS clientes;
CREATE TABLE clientes (id INTEGER AUTO_INCREMENT, nome VARCHAR (100) NOT NULL, telefone VARCHAR (15) NOT NULL, anoNascimento INTEGER NOT NULL, PRIMARY KEY(id));

-- Table: pedidos
DROP TABLE IF EXISTS pedidos;
CREATE TABLE pedidos (
    id        INTEGER  PRIMARY KEY AUTO_INCREMENT,
    idCliente INTEGER  REFERENCES clientes (id),
    data  DATETIME,
    valorTotal     DOUBLE
);

-- Table: pedidospizza

-- Table: pizzas
DROP TABLE IF EXISTS pizzas;
CREATE TABLE pizzas (id INTEGER AUTO_INCREMENT, sabor VARCHAR (100) NOT NULL, valor DOUBLE NOT NULL, PRIMARY KEY(id));

DROP TABLE IF EXISTS pedidospizza;
CREATE TABLE pedidopizza (id INTEGER AUTO_INCREMENT, idPedido INTEGER REFERENCES pedidos (id) ON DELETE CASCADE, idPizza INTEGER REFERENCES pizzas (id), valor DOUBLE, PRIMARY KEY(id));


