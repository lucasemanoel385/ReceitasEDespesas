create table usuarios_perfis(

    usuario_id bigint not null,
    perfis_id bigint not null,
    foreign key(usuario_id) references usuarios(id),
    foreign key(perfis_id) references perfil(id));
