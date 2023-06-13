create table perfil(

    id bigint not null auto_increment,
    nome varchar(25) not null unique,

    primary key(id)

);

INSERT INTO perfil(nome) VALUES('ROLE_USER');
INSERT INTO perfil(nome) VALUES('ROLE_ADMIN');