drop function consumoTotalDeCliente;
delimiter |
create function consumoTotalDeCliente(nome_cliente char(50)) 
returns boolean
begin
	declare x boolean;
	select
		sum(produto_pedido.quantidade*produto.preco) into x
	from pedido
	join produto_pedido on produto_pedido.codVenda = pedido.codVenda
	join produto on produto.codProd = produto_pedido.codProd
	join pessoa on pessoa.codPessoa = pedido.pessoa
	where pessoa.nome like nome_cliente;
	return x;
end
|
delimiter ;

drop function consumoTotalDeClienteAposCertaData;
delimiter |
create function consumoTotalDeClienteAposCertaData(nome_cliente char(50), dataInicial date) 
returns boolean
begin
	declare x boolean;
	select
		sum(produto_pedido.quantidade*produto.preco) into x
	from pedido
	join produto_pedido on produto_pedido.codVenda = pedido.codVenda
	join produto on produto.codProd = produto_pedido.codProd
	join pessoa on pessoa.codPessoa = pedido.pessoa
	where pessoa.nome like nome_cliente
	and pedido.dataVenda >= dataInicial;
	return x;
end
|
delimiter ;

drop function consumoTotalDeClienteAntesDeCertaData;
delimiter |
create function consumoTotalDeClienteAntesDeCertaData(nome_cliente char(50), dataFinal date) 
returns boolean
begin
	declare x boolean;
	select
		sum(produto_pedido.quantidade*produto.preco) into x
	from pedido
	join produto_pedido on produto_pedido.codVenda = pedido.codVenda
	join produto on produto.codProd = produto_pedido.codProd
	join pessoa on pessoa.codPessoa = pedido.pessoa
	where pessoa.nome like nome_cliente
	and pedido.dataVenda <= dataFinal;
	return x;
end
|
delimiter ;

drop function consumoTotalDeClienteEmCertoIntervaloDeDatas;
delimiter |
create function consumoTotalDeClienteEmCertoIntervaloDeDatas(nome_cliente char(50), dataInicial date, dataFinal date) 
returns boolean
begin
	declare x boolean;
	select
		sum(produto_pedido.quantidade*produto.preco) into x
	from pedido
	join produto_pedido on produto_pedido.codVenda = pedido.codVenda
	join produto on produto.codProd = produto_pedido.codProd
	join pessoa on pessoa.codPessoa = pedido.pessoa
	where pessoa.nome like nome_cliente
	and pedido.dataVenda >= dataInicial
	and pedido.dataVenda <= dataFinal;
	return x;
end
|
delimiter ;
