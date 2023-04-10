create table tarefa(
	id bigint not null auto_increment,
    cliente_id bigint not null,
    
    status varchar(20) not null,
    data_pedido datetime not null,
    data_finalizacao datetime,
    
    conteudo_titulo varchar(255) not null,
    conteudo_subtitulo varchar(255) not null,
    conteudo_texto varchar(2000) not null,
    
    primary key (id)
);

alter table tarefa add constraint fk_tarefa_cliente
foreign key (cliente_id) references cliente (id);