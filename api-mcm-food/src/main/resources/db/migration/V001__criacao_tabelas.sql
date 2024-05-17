create table tb_cozinha(
	id bigint not null auto_increment,
	nome varchar(60) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_cidade(
	id bigint not null auto_increment,
	nome varchar(80) not null,
	estado_id bigint not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_estado(
	id bigint not null auto_increment,
	nome varchar(80) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_forma_pagamento (
	id bigint not null auto_increment,
	descricao varchar(60) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_grupo (
	id bigint not null auto_increment,
	nome varchar(60) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_grupo_permissao (
	grupo_id bigint not null,
	permissao_id bigint not null,
	primary key (grupo_id, permissao_id)
) engine=InnoDB default charset=utf8;

create table tb_permissao (
	id bigint not null auto_increment,
	descricao varchar(60) not null,
	nome varchar(100) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_produto (
	id bigint not null auto_increment,
	restaurante_id bigint not null,
	nome varchar(80) not null,
	descricao text not null,
	preco decimal(10,2) not null,
	ativo tinyint(1) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_restaurante (
	id bigint not null auto_increment,
	cozinha_id bigint not null,
	nome varchar(80) not null,
	taxa_frete decimal(10,2) not null,
	data_atualizacao datetime not null,
	data_cadastro datetime not null,
	endereco_cidade_id bigint,
	endereco_cep varchar(9),
	endereco_logradouro varchar(100),
	endereco_numero varchar(20),
	endereco_complemento varchar(60),
	endereco_bairro varchar(60),
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_restaurante_forma_pagamento (
	restaurante_id bigint not null,
	forma_pagamento_id bigint not null,
	primary key (restaurante_id, forma_pagamento_id)
) engine=InnoDB default charset=utf8;

create table tb_usuario (
	id bigint not null auto_increment,
	nome varchar(80) not null,
	email varchar(255) not null,
	senha varchar(255) not null,
	data_cadastro datetime not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_usuario_grupo (
	usuario_id bigint not null,
	grupo_id bigint not null,
	primary key (usuario_id, grupo_id)
) engine=InnoDB default charset=utf8;