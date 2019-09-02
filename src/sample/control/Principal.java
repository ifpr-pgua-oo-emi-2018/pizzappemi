package sample.control;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import sample.NavegadorCenas;
import sample.model.Cliente;
import sample.model.Pedido;
import sample.model.Pizza;
import sample.model.Pizzaria;

import javax.swing.*;
import java.sql.SQLException;

public class Principal {


    @FXML
    private ListView<Pizza> ltvSabores;

    @FXML
    private ListView<Pizza> ltvPedido;

    @FXML
    private ListView<Cliente> ltvClientes;

    @FXML
    private Button btIncluir;

    @FXML
    private Button btAbrirPedido;

    @FXML
    private Button btFecharPedido;

    @FXML
    private Text txtValor;

    ObservableList<Pizza> sabores;


    public void initialize(){
        try{
            ltvSabores.setItems(Pizzaria.getInstance().listaSabores());
            ltvClientes.setItems(Pizzaria.getInstance().listaClientes());
            btFecharPedido.setDisable(true);
            btIncluir.setDisable(true);
            ltvClientes.setDisable(true);
        }catch (SQLException e){
            mensagem(Alert.AlertType.ERROR,e.getMessage());
        }
    }

    public void abrirPedido(){
        try{
            Pizzaria.getInstance().abrirPedido();
            ltvPedido.setItems(Pizzaria.getInstance().getListaPedido());
            ltvClientes.setDisable(false);
            btFecharPedido.setDisable(false);
            btIncluir.setDisable(false);
        }catch (Exception e){
            mensagem(Alert.AlertType.ERROR,e.getMessage());
        }
    }

    public void incluirPizza(){
        Pizza p = ltvSabores.getSelectionModel().getSelectedItem();

        try{
            if(p!=null){
                Pizzaria.getInstance().incluirPizza(p);
                txtValor.setText("Valor Total R$:"+Pizzaria.getInstance().getValorPedido());
            }
        }catch (Exception e){
            mensagem(Alert.AlertType.ERROR,e.getMessage());
        }
    }

    public void fecharPedido(){
        try{
                double valor = Pizzaria.getInstance().fecharPedido();

                //INSERIR NO BANCO DE DADOS....


                mensagem(Alert.AlertType.CONFIRMATION,"O valor do pedido é R$ "+valor);

                ltvPedido.setItems(null);

                btFecharPedido.setDisable(true);
                btIncluir.setDisable(true);
                ltvClientes.setDisable(true);
                txtValor.setText("Valor Total R$:");


        }catch (Exception e){
            mensagem(Alert.AlertType.ERROR,e.getMessage());
        }
    }


    @FXML
    public void buscaPizzas(KeyEvent evt){
        try{


            if(evt.getCode() == KeyCode.Z && evt.isControlDown()){
                Pizzaria.getInstance().listaSabores();
                ((TextField)evt.getSource()).setText("");
            }else{
                String texto = ((TextField)evt.getSource()).getText() + evt.getText();

                if(texto.length() >= 3){
                    Pizzaria.getInstance().buscaPizza(texto);
                }
            }
        }catch (SQLException e){
            mensagem(Alert.AlertType.ERROR,"Erro ao listar Pizzas!"+e.getMessage());
        }
    }

    @FXML
    public void buscaClientes(KeyEvent evt){
        try{

            String texto = ((TextField)evt.getSource()).getText();
            if(evt.getCode() != KeyCode.BACK_SPACE){
                texto += evt.getText();
            }

            System.out.println(texto+" "+texto.length());
            if(evt.getCode() == KeyCode.Z && evt.isControlDown() || texto.length()==0){
                Pizzaria.getInstance().listaClientes();
                ((TextField)evt.getSource()).setText("");
            }else{


                if(texto.length() >= 3){
                    Pizzaria.getInstance().buscaCliente(texto);
                }
            }

        }catch (SQLException e){
            mensagem(Alert.AlertType.ERROR,"Erro ao listar Clientes!"+e.getMessage());
        }

    }


    @FXML
    public void incluiClientePedido(){
        try{

            Cliente c = ltvClientes.getSelectionModel().getSelectedItem();

            if(c!=null){

                Pizzaria.getInstance().incluiClientePedido(c);

            }

        }catch (Exception e){
            mensagem(Alert.AlertType.ERROR,e.getMessage());
        }

    }


    public void cadastrarPizza(){

        NavegadorCenas.loadJanela(NavegadorCenas.JANELA_CADASTRO);

    }

    public void cadastrarCliente(){

        NavegadorCenas.loadJanela(NavegadorCenas.JANELA_CADASTRO_CLIENTE);

    }

    public void listarPedidos(){
        NavegadorCenas.loadJanela(NavegadorCenas.JANELA_VISUALIZA_PEDIDOS);
    }


    private void mensagem(Alert.AlertType type, String msg){
        Alert alert = new Alert(type,msg);
        alert.showAndWait();
    }


}
