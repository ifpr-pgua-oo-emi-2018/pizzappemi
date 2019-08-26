package sample.model;

import java.sql.SQLException;
import java.util.List;

public interface PedidoDAO {

    Pedido insere(Pedido p) throws SQLException;

    Pedido buscaId(int id) throws SQLException;
    List<Pedido> lista() throws SQLException;

    Pedido atualiza(Pedido p) throws SQLException;

    boolean remove(Pedido p) throws SQLException;


}
