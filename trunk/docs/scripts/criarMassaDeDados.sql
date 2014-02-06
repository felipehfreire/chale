-- Inser��o de alguns produtos e servi�os.
insert into produto(descricao, qtdEstoque, preco, tipoServico, qtdMinEstoque)
	values('Ruffles, a batata da onda!', 1000, 1, false, 10);
insert into produto(descricao, qtdEstoque, preco, tipoServico, qtdMinEstoque)
	values('minhoca pra pescar', 1000, 0.1, false, 100);
insert into produto(descricao, qtdEstoque, preco, tipoServico, qtdMinEstoque)
	values('Tonyu - suco de ma�� da Yakult', 1000, 2, false, 10);
insert into produto(descricao, qtdEstoque, preco, tipoServico, qtdMinEstoque)
	values('Noite no chal�', 7, 100, true, 0);
insert into produto(descricao, qtdEstoque, preco, tipoServico, qtdMinEstoque)
	values('Descida de tirolesa', 999999, 5, true, 0);

-- Inser��o de perfis de usu�rio
insert into perfis_acesso(cod_perfil_acesso, nome, descricao)
	values(1, 'Desenvolvedor da aplica��o', 'Perfil dos desenvolvedores da aplica��o, devem ter poderes para tudo');
insert into perfis_acesso(cod_perfil_acesso, nome, descricao)
	values(2, 'Administrador de aplica��o', 'Perfil dos usu�rios respons�veis por administrar a aplica��o, devem ter poderes para tudo no banco "chale"');
insert into perfis_acesso(cod_perfil_acesso, nome, descricao)
	values(3, 'Usu�rio de aplica��o', 'Perfil dos usu�rios respons�veis por utilizar a aplica��o');
insert into perfis_acesso(cod_perfil_acesso, nome, descricao)
	values(4, 'Visitante', 'Perfil dos usu�rios que s� podem visualizar dados, n�o podem alterar nem inserir');

-- Inser��o de Clientes que ser�o usu�rios
insert into cliente (txt_nome, txt_telefone) values ('Felipe', '(35)9999-9999');
insert into cliente (txt_nome, txt_telefone) values ('Jhonatan', '(35)9999-9999');
insert into cliente (txt_nome, txt_telefone) values ('Thiago', '(35)9999-9999');
insert into cliente (txt_nome, txt_telefone) values ('Ludmila', '(35)9999-9999');
insert into cliente (txt_nome, txt_telefone) values ('Ana', '(35)9999-9999');


-- Inser��o de usu�rios
insert into usuario (pw_senha, txt_nome_usuario) values (md5('123'), 'teste');

-- Inser��o mesas
insert into mesa (ind_usada) values (false);
insert into mesa (ind_usada) values (false);
insert into mesa (ind_usada) values (false);
insert into mesa (ind_usada) values (false);
insert into mesa (ind_usada) values (false);
insert into mesa (ind_usada) values (false);
insert into mesa (ind_usada) values (false)
	

insert into pedido(dataVenda, finalizada, vendaPrazo, mesa)
	values('2012-01-12-11-11', false, false, 1);
insert into pessoa(nome, telefone)
	values('Jo�o sem Bra�o', '12345678');
insert into produto_pedido
	values(1, 1, 2);
insert into produto_pedido
	values(1, 3, 20);
