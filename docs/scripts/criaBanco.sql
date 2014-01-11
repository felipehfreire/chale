-- CRIA O BANCO DE DADOS
drop database chale;
create database chale;

-- ENTRA NO BANCO DE DADOS
use chale;

-- CRIA AS TABELAS UTILIZADAS PELO SISTEMA
drop table produto;
create table produto(
   codProd integer not null auto_increment,
   descricao varchar(200) not null,
   qtdEstoque integer not null,
   preco double not null,
   tipoServico boolean not null,
   qtdMinEstoque integer,
   primary key(codProd)
);

drop table mesa;
create table mesa(
   codMesa integer not null auto_increment,
   usada boolean not null,
   primary key(codMesa)
);

drop table pessoa;
create table pessoa(
   codPessoa integer not null auto_increment,
   nome varchar(100) not null,
   telefone varchar(20),
   primary key(codPessoa)
);

drop table perfis_acesso;
create table perfis_acesso(
	cod_perfil_acesso integer not null auto_increment,
	nome varchar(50) not null,
	descricao varchar(200),
	primary key(cod_perfil_acesso)
);

drop table usuario;
create table usuario(
   codUsuario integer not null auto_increment,
   login varchar(100) not null,
   senha varchar(128) not null,
   cod_perfil_acesso integer not null,
   codPessoa integer not null,
   primary key(codUsuario),
   foreign key(codPessoa) references pessoa(codPessoa),
   foreign key(cod_perfil_acesso) references perfis_acesso(cod_perfil_acesso)
);

drop table pedido;
create table pedido(
   codVenda integer not null auto_increment,
   dataVenda date not null,
   finalizada boolean not null,
   vendaPrazo boolean not null,
   pessoa integer,
   mesa integer,
   primary key(codVenda),
   foreign key(pessoa) references pessoa(codPessoa),
   foreign key(mesa) references mesa(codMesa)
);

drop table produto_pedido;
create table produto_pedido(
	codVenda integer not null,
	codProd integer not null,
	quantidade integer not null,
	primary key(codVenda, codProd),
	foreign key(codVenda) references pedido(codVenda),
	foreign key(codProd) references produto(codProd)
);
