package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO{


    @Override
    public Cliente insere(String nome, String telefone, int anoNascimento) throws SQLException {

        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con
                .prepareStatement("INSERT INTO clientes(nome,telefone,anoNascimento) VALUES (?,?,?)");

        stm.setString(1,nome);
        stm.setString(2,telefone);
        stm.setInt(3,anoNascimento);

        stm.executeUpdate();

        stm.close();
        con.close();


        Cliente c = new Cliente(nome,telefone,anoNascimento);

        return c;


    }

    @Override
    public Cliente buscaId(int id) throws SQLException {

        Cliente c = null;

        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM clientes where id=?");

        stm.setInt(1,id);

        ResultSet res = stm.executeQuery();

        while(res.next()){
            String nome = res.getString("nome");
            String telefone = res.getString("telefone");
            int anoNascimento = res.getInt("anoNascimento");

            c = new Cliente(id,nome,telefone,anoNascimento);

        }

        res.close();
        stm.close();
        con.close();

        return c;

    }

    @Override
    public List<Cliente> lista() throws SQLException {

        ArrayList<Cliente> clientes = new ArrayList<>();

        Connection con = FabricaConexao.getConnection();

        Statement stm = con.createStatement();

        ResultSet res = stm.executeQuery("SELECT * FROM clientes");

        while(res.next()){
            int id = res.getInt("id");
            String nome = res.getString("nome");
            String telefone = res.getString("telefone");
            int anoNascimento =res.getInt("anoNascimento");

            Cliente c = new Cliente(id,nome,telefone,anoNascimento);

            clientes.add(c);
        }

        res.close();
        stm.close();
        con.close();

        return clientes;
    }

    @Override
    public List<Cliente> buscaNome(String texto) throws SQLException {

        ArrayList<Cliente> clientes = new ArrayList<>();

        Connection con = FabricaConexao.getConnection();


        PreparedStatement stm = con.prepareStatement("SELECT * FROM clientes where nome like ?");

        stm.setString(1,"%"+texto+"%");

        ResultSet res = stm.executeQuery();

        while(res.next()){
            int id = res.getInt("id");
            String nome = res.getString("nome");
            String telefone = res.getString("telefone");
            int anoNascimento = res.getInt("anoNascimento");

            Cliente c = new Cliente(id,nome,telefone,anoNascimento);

            clientes.add(c);
        }

        res.close();
        stm.close();
        con.close();

        return clientes;
    }

    @Override
    public Cliente atualiza(Cliente c) throws SQLException {
        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con
                .prepareStatement("UPDATE clientes SET nome=?, telefone=?, anoNascimento=? WHERE id=?");

        stm.setString(1,c.getNome());
        stm.setString(2,c.getTelefone());
        stm.setInt(3,c.getAnoNascimento());

        stm.setInt(4,c.getId());

        stm.executeUpdate();

        stm.close();
        con.close();

        return c;

    }

    @Override
    public boolean remove(Cliente c) throws SQLException {
        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con
                .prepareStatement("DELETE FROM clientes WHERE id=?");


        stm.setInt(1,c.getId());

        stm.executeUpdate();

        stm.close();
        con.close();

        return true;
    }
}
