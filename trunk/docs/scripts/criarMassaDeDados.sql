-- Inserção de alguns produtos e serviços.
insert into produto(descricao, qtdEstoque, preco, tipoServico, qtdMinEstoque)
	values('Ruffles, a batata da onda!', 1000, 1, false, 10);
insert into produto(descricao, qtdEstoque, preco, tipoServico, qtdMinEstoque)
	values('minhoca pra pescar', 1000, 0.1, false, 100);
insert into produto(descricao, qtdEstoque, preco, tipoServico, qtdMinEstoque)
	values('Tonyu - suco de maçã da Yakult', 1000, 2, false, 10);
insert into produto(descricao, qtdEstoque, preco, tipoServico, qtdMinEstoque)
	values('Noite no chalé', 7, 100, true, 0);
insert into produto(descricao, qtdEstoque, preco, tipoServico, qtdMinEstoque)
	values('Descida de tirolesa', 999999, 5, true, 0);

-- Inserção de perfis de usuário
insert into perfis_acesso(cod_perfil_acesso, nome, descricao)
	values(1, 'Desenvolvedor da aplicação', 'Perfil dos desenvolvedores da aplicação, devem ter poderes para tudo');
insert into perfis_acesso(cod_perfil_acesso, nome, descricao)
	values(2, 'Administrador de aplicação', 'Perfil dos usuários responsáveis por administrar a aplicação, devem ter poderes para tudo no banco "chale"');
insert into perfis_acesso(cod_perfil_acesso, nome, descricao)
	values(3, 'Usuário de aplicação', 'Perfil dos usuários responsáveis por utilizar a aplicação');
insert into perfis_acesso(cod_perfil_acesso, nome, descricao)
	values(4, 'Visitante', 'Perfil dos usuários que só podem visualizar dados, não podem alterar nem inserir');

-- Inserção de pessoas que serão usuários
insert into pessoa(codPessoa, nome)
	values(1, 'Felipe');
insert into pessoa(codPessoa, nome)
	values(2, 'Lucas');
insert into pessoa(codPessoa, nome)
	values(3, 'Adjalma');
insert into pessoa(codPessoa, nome)
	values(4, 'Botucatu');
insert into pessoa(codPessoa, nome)
	values(5, 'Cosmiro');

-- Inserção de usuários
insert into usuario(codUsuario, login, senha, cod_perfil_acesso, codPessoa)
	values(1, 'prog_felipe', 'abc123', 1, 1);
insert into usuario(codUsuario, login, senha, cod_perfil_acesso, codPessoa)
	values(2, 'prog_lucas', 'xyz789', 1, 2);
insert into usuario(codUsuario, login, senha, cod_perfil_acesso, codPessoa)
	values(3, 'adm_adjalma', '12081976', 2, 3);
insert into usuario(codUsuario, login, senha, cod_perfil_acesso, codPessoa)
	values(4, 'func_botucatu', 'tantofaz', 3, 4);
insert into usuario(codUsuario, login, senha, cod_perfil_acesso, codPessoa)
	values(5, 'estag_cosmiro', 'wowelfwizzardlv99', 4, 5);

-- Inserção de um pedido falso
insert into mesa(codMesa, usada)
	values(1, true);
insert into mesa(codMesa, usada)
	values(2, false);
insert into mesa(codMesa, usada)
	values(3, true);
insert into mesa(codMesa, usada)
	values(4, true);
insert into mesa(codMesa, usada)
	values(5, false);
insert into mesa(codMesa, usada)
	values(6, true);
insert into mesa(codMesa, usada)
	values(7, false);
insert into mesa(codMesa, usada)
	values(8, false);
insert into mesa(codMesa, usada)
	values(9, false);
insert into mesa(codMesa, usada)
	values(10, false);

insert into pedido(dataVenda, finalizada, vendaPrazo, mesa)
	values('2012-01-12-11-11', false, false, 1);
insert into pessoa(nome, telefone)
	values('João sem Braço', '12345678');
insert into produto_pedido
	values(1, 1, 2);
insert into produto_pedido
	values(1, 3, 20);
