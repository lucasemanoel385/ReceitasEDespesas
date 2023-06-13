create table despesas(

    id bigint not null auto_increment,
    descricao varchar(255),
    valor float not null,
    data date not null,

    primary key(id)

);