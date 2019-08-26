package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO{


    @Override
    public Cliente insere(String nome, String telefone, int anoNascimento) throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:sqlite:pizzappemi.sqlite");

        PreparedStatement stm = con
                .prepareStatement("INSERT INTO CLIENTES(NOME,TELEFONE,ANONASCIMENTO) VALUES (?,?,?)");

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

        Connection con = DriverManager.getConnection("jdbc:sqlite:pizzappemi.sqlite");

        PreparedStatement stm = con.prepareStatement("SELECT * FROM CLIENTES where ID=?");

        stm.setInt(1,id);

        ResultSet res = stm.executeQuery();

        while(res.next()){
            String nome = res.getString("NOME");
            String telefone = res.getString("NOME");
            int anoNascimento = res.getInt("ANONASCIMENTO");

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

        Connection con = DriverManager.getConnection("jdbc:sqlite:pizzappemi.sqlite");

        Statement stm = con.createStatement();

        ResultSet res = stm.executeQuery("SELECT * FROM CLIENTES");

        while(res.next()){
            int id = res.getInt("ID");
            String nome = res.getString("NOME");
            String telefone = res.getString("TELEFONE");
            int anoNascimento =res.getInt("ANONASCIMENTO");

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

        Connection con = DriverManager.getConnection("jdbc:sqlite:pizzappemi.sqlite");


        PreparedStatement stm = con.prepareStatement("SELECT * FROM CLIENTES where NOME like ?");

        stm.setString(1,"%"+texto+"%");

        ResultSet res = stm.executeQuery();

        while(res.next()){
            int id = res.getInt("ID");
            String nome = res.getString("NOME");
            String telefone = res.getString("NOME");
            int anoNascimento = res.getInt("ANONASCIMENTO");

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
        Connection con = DriverManager.getConnection("jdbc:sqlite:pizzappemi.sqlite");

        PreparedStatement stm = con
                .prepareStatement("UPDATE CLIENTES SET NOME=?, TELEFONE=?, ANONASCIMENTO=? WHERE ID=?");

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
        Connection con = DriverManager.getConnection("jdbc:sqlite:pizzappemi.sqlite");

        PreparedStatement stm = con
                .prepareStatement("DELETE FROM CLIENTES WHERE ID=?");


        stm.setInt(1,c.getId());

        stm.executeUpdate();

        stm.close();
        con.close();

        return true;
    }
}
