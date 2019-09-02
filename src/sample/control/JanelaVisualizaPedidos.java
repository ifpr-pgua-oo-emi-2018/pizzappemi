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

public class JanelaVisualizaPedidos {


    //declaração da tabela
    @FXML
    private TableView<Pedido> tbPedidos;


    //declaracao de cada coluna,
    //mapeando um pedido para o atributo que será
    //mostrado na coluna
    
    @FXML
    private TableColumn<Pedido,Integer> tcPedidoId;

    @FXML
    private TableColumn<Pedido,LocalDateTime> tcPedidoData;

    @FXML
    private TableColumn<Pedido,Double> tcPedidoValor;

    @FXML
    private TableColumn<Pedido,String> tcPedidoCliente;




    @FXML
    private TableView<Pizza> tbPedidoPizzas;

    @FXML
    private TableColumn<Pizza,String > tcPizzaSabor;

    @FXML
    private TableColumn<Pizza,Double> tcPizzaValor;


    public void initialize(){


        //fazendo o mapeamento para a tabela dos pedidos
        tcPedidoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcPedidoData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tcPedidoValor.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        tcPedidoCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        //fazendo o mapeamento para a tabela das pizzas
        tcPizzaSabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
        tcPizzaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));


        //carregando os dados dos pedidos
        try{
            tbPedidos.setItems(Pizzaria.getInstance().listaPedidos());

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void voltar(){

        NavegadorCenas.loadJanela(NavegadorCenas.PRINCIPAL);

    }


    @FXML
    public void carregarPizzas(){

        Pedido pedido = tbPedidos.getSelectionModel().getSelectedItem();

        if(pedido != null){
            tbPedidoPizzas.setItems(pedido.listaPizzas());
        }


    }






}
