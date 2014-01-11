drop procedure geraRelatorioVendasMes;
delimiter |
create procedure geraRelatorioVendasMes() 
begin
	-- cria relatorio das vendas do mês
	select
		pedido.dataVenda as data_da_venda,
		pedido.mesa as mesa,
		sum(produto_pedido.quantidade*produto.preco) as preco_total
	from pedido
	join produto_pedido on produto_pedido.codVenda = pedido.codVenda
	join produto on produto.codProd = produto_pedido.codProd
	order by pedido.dataVenda;
end
|
delimiter ;


drop procedure geraRelatorioVendasMesDescrevendoProdutos;
delimiter |
create procedure geraRelatorioVendasMesDescrevendoProdutos() 
begin
	-- cria relatorio das vendas do mês
	select
		pedido.dataVenda as data_da_venda,
		pedido.mesa as mesa,
		produto.descricao as nome_produto,
		produto_pedido.quantidade as quantidade_produto,
		produto_pedido.quantidade*produto.preco as total_por_produto
	from pedido
	join produto_pedido on produto_pedido.codVenda = pedido.codVenda
	join produto on produto.codProd = produto_pedido.codProd
	order by pedido.dataVenda;
end
|
delimiter ;
