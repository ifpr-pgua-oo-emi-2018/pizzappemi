package sample.model;

import javax.xml.ws.FaultAction;
import java.sql.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {


    private static String INSERT = "INSERT INTO pedidos(idCliente,valorTotal,data) VALUES(?,?,?)";
    private static String ULTIMO_ID = "select seq from sqlite_sequence where name='pedidos'";
    private static String INSERE_PEDIDO_PIZZA = "INSERT INTO pedidopizza(idPedido,idPizza,valor) VALUES(?,?,?)";

    private static String LISTA = "SELECT * FROM pedidos";
    private static String LISTA_PEDIDOPIZZA = "SELECT * FROM pedidopizza WHERE idPedido=?";

    @Override
    public Pedido insere(Pedido p) throws SQLException {

        Connection con = FabricaConexao.getConnection();


        //necessário utiliza o parametro Statement.RETURN_GENERATED_KEYS para poder acessar o id
        //do pedido que foi inserido
        PreparedStatement stm = con.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
        stm.setInt(1,p.getCliente().getId());
        stm.setDouble(2,p.getValorTotal());
        stm.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
        int rows = stm.executeUpdate();


        //buscando o id do pedido que acabou de ser inserido
        if(rows == 0){
            throw  new SQLException("Problema ao inserir Pedido!!");
        }

        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        p.setId(id);

        rs.close();
        stm.close();


        //inserir todas as pizzas do pedido na tabela pedidopizza

        stm = con.prepareStatement(INSERE_PEDIDO_PIZZA);

        for(Pizza pizza:p.listaPizzas()){

            stm.setInt(1,p.getId());
            stm.setInt(2,pizza.getId());
            stm.setDouble(3,pizza.getValor());

            stm.executeUpdate();

        }

        stm.close();
        con.close();
        return p;
    }

    @Override
    public Pedido buscaId(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Pedido> lista() throws SQLException {

        ArrayList<Pedido> pedidos = new ArrayList<>();

        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con.prepareStatement(LISTA);

        ResultSet rs = stm.executeQuery();

        ClienteDAO clienteDAO = new ClienteDAOImpl();
        PizzaDAO pizzaDAO = new PizzaDAOImpl();
        PreparedStatement stm2 = con.prepareStatement(LISTA_PEDIDOPIZZA);

        while(rs.next()){
            int id = rs.getInt("id");
            int idCliente = rs.getInt("idCliente");
            LocalDateTime data = rs.getTimestamp("data").toLocalDateTime();
            double valorTotal = rs.getDouble("valorTotal");

            Cliente c = clienteDAO.buscaId(idCliente);

            Pedido pedido = new Pedido();

            pedido.setId(id);
            pedido.setCliente(c);
            pedido.setValorTotal(valorTotal);
            //pedido.setData(LocalDateTime.parse(data.toString()));
            pedido.setData(data);





            //buscando as pizzas do pedido
            stm2.setInt(1,pedido.getId());
            ResultSet rs2 = stm2.executeQuery();
            while (rs2.next()){
                int idPizza = rs2.getInt("idPizza");
                double valor = rs2.getDouble("valor");

                Pizza p = pizzaDAO.buscaId(idPizza);
                p.setValor(valor);

                pedido.incluir(p);
            }

            rs2.close();

            pedidos.add(pedido);
        }
        rs.close();
        stm2.close();
        stm.close();
        con.close();

        return pedidos;
    }

    @Override
    public Pedido atualiza(Pedido p) throws SQLException {
        return null;
    }

    @Override
    public boolean remove(Pedido p) throws SQLException {
        return false;
    }
}
