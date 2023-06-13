create table usuarios(

    id bigint not null auto_increment,
    login varchar(100) not null unique,
    senha varchar(100) not null,
    primary key(id)

);

INSERT INTO usuarios (login, senha) VALUES ('lucaa@gmail.com', '$2a$12$RgailGMuh3.PZQDjGJQ6auzzv5AouS8ob6/CQRpKw9mZvHM0Z2R/m');
INSERT INTO usuarios (login, senha) VALUES ('lucaapreto@gmail.com', '$2a$12$RgailGMuh3.PZQDjGJQ6auzzv5AouS8ob6/CQRpKw9mZvHM0Z2R/m');