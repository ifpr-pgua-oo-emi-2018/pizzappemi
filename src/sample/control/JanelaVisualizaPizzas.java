package sample.control;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.NavegadorCenas;
import sample.model.Pedido;
import sample.model.Pizza;
import sample.model.Pizzaria;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class JanelaVisualizaPizzas {



    @FXML
    private TableView<Pizza> tbPizzas;


    @FXML
    private TableColumn<Pizza,Integer > tcPizzaId;


    @FXML
    private TableColumn<Pizza,String > tcPizzaSabor;

    @FXML
    private TableColumn<Pizza,Double> tcPizzaValor;


    public void initialize(){



        //fazendo o mapeamento para a tabela das pizzas
        tcPizzaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcPizzaSabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
        tcPizzaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));


        //carregando os dados dos pedidos
        try{
            tbPizzas.setItems(Pizzaria.getInstance().listaSabores());

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void voltar(){

        NavegadorCenas.loadJanela(NavegadorCenas.PRINCIPAL);

    }


}
