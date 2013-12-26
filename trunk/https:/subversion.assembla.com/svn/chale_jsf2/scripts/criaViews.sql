create view pessoa_usuario_perfil_acesso as
	select p.nome as nome, u.login as login, pa.nome as perfil from perfis_acesso pa
	join usuario u on u.cod_perfil_acesso = pa.cod_perfil_acesso
	join pessoa p on p.codPessoa = u.codPessoa;
