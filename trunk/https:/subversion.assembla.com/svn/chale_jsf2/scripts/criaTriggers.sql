drop trigger atualizaEstoqueNoUpdate;
delimiter |
create trigger atualizaEstoqueNoUpdate
after update on produto_pedido for each row
begin
	update produto set qtdEstoque = qtdEstoque - (NEW.quantidade - OLD.quantidade) where codProd = NEW.codProd;
end |
delimiter ;

drop trigger atualizaEstoqueNoInsert;
delimiter |
create trigger atualizaEstoqueNoInsert
after insert on produto_pedido for each row
begin
	update produto set qtdEstoque = qtdEstoque - NEW.quantidade where codProd = NEW.codProd;
end |
delimiter ;
