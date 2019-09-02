package sample.control;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.NavegadorCenas;
import sample.model.Cliente;
import sample.model.Pizza;
import sample.model.Pizzaria;

import java.sql.SQLException;

public class JanelaVisualizaClientes {



    @FXML
    private TableView<Cliente> tbClientes;


    @FXML
    private TableColumn<Cliente,Integer > tcClienteId;


    @FXML
    private TableColumn<Cliente,String > tcClienteNome;

    @FXML
    private TableColumn<Cliente,String> tcClienteTelefone;

    @FXML
    private TableColumn<Cliente,Integer> tcClienteAnoNascimento;



    public void initialize(){



        //fazendo o mapeamento para a tabela das pizzas
        tcClienteId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcClienteTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tcClienteAnoNascimento.setCellValueFactory(new PropertyValueFactory<>("anoNascimento"));



        //carregando os dados dos pedidos
        try{
            tbClientes.setItems(Pizzaria.getInstance().listaClientes());

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void voltar(){

        NavegadorCenas.loadJanela(NavegadorCenas.PRINCIPAL);

    }


}
