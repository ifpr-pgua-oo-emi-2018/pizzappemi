package sample.control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sample.NavegadorCenas;
import sample.model.Pizzaria;

import java.sql.SQLException;

public class JanelaCadastro {

    @FXML
    private TextField tfSabor;

    @FXML
    private TextField tfValor;

    public void cadastrar(){

        String sabor = tfSabor.getText();
        Double valor = Double.valueOf(tfValor.getText());


        try{
            Pizzaria.getInstance().cadastraPizza(sabor,valor);
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Erro ao inserir pizza! "+e.getMessage());
            alert.showAndWait();
        }

        voltar();
    }

    public void voltar(){
        NavegadorCenas.loadJanela(NavegadorCenas.PRINCIPAL);
    }

}
