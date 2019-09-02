package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaDAOImpl implements PizzaDAO{


    @Override
    public Pizza insere(String sabor, double valor) throws SQLException {
        Pizza p = new Pizza(sabor,valor);
        Connection con = FabricaConexao.getConnection();


        PreparedStatement stm = con
                    .prepareStatement("INSERT INTO pizzas(sabor,valor) VALUES (?,?)");

        stm.setString(1,p.getSabor());
        stm.setDouble(2,p.getValor());

        stm.executeUpdate();

        stm.close();
        con.close();

        return p;
    }

    @Override
    public Pizza atualiza(Pizza p) throws SQLException {
        Connection con = FabricaConexao.getConnection();


        PreparedStatement stm = con
                .prepareStatement("UPDATE pizzas SET sabor=? ,valor=? WHERE id=?");

        stm.setString(1,p.getSabor());
        stm.setDouble(2,p.getValor());
        stm.setInt(3,p.getId());

        stm.executeUpdate();

        stm.close();
        con.close();

        return p;
    }

    @Override
    public boolean remove(Pizza p) throws SQLException {
        Connection con = FabricaConexao.getConnection();


        PreparedStatement stm = con
                .prepareStatement("DELETE FROM pizzas WHERE id=?");

        stm.setInt(1,p.getId());

        stm.executeUpdate();

        stm.close();
        con.close();

        return true;


    }

    @Override
    public Pizza buscaId(int id) throws SQLException {

        Pizza p=null;

        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM pizzas where id=?");

        stm.setInt(1,id);

        ResultSet res = stm.executeQuery();

        while(res.next()){
            String sabor = res.getString("sabor");
            Double valor = res.getDouble("valor");

            p = new Pizza(id,sabor,valor);
        }

        res.close();
        stm.close();
        con.close();

        return p;


    }

    @Override
    public List<Pizza> lista() throws SQLException {
        ArrayList<Pizza> sabores = new ArrayList<>();

        Connection con = FabricaConexao.getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM pizzas");

        ResultSet rs = stm.executeQuery();


        while(rs.next()){
            int id = rs.getInt("id");
            String sabor = rs.getString("sabor");
            double valor = rs.getDouble("valor");

            Pizza p = new Pizza(id,sabor,valor);

            sabores.add(p);
        }


        rs.close();
        stm.close();
        con.close();

        return sabores;
    }
}
