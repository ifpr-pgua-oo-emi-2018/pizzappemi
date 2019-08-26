package sample.model;

import java.sql.SQLException;
import java.util.List;

public interface PizzaDAO {


    //Create
    Pizza insere(String sabor, double valor) throws SQLException;

    //Read
    Pizza buscaId(int id) throws SQLException;
    List<Pizza> lista() throws SQLException;


    //Update
    Pizza atualiza(Pizza p) throws SQLException;
    //Delete
    boolean remove(Pizza p) throws SQLException;


}
