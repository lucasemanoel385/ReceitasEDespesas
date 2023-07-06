create table usuarios_perfis(

    usuario_id bigint not null,
    perfis_id bigint not null,
    foreign key(usuario_id) references usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key(perfis_id) references perfil(id) ON DELETE CASCADE ON UPDATE CASCADE);
