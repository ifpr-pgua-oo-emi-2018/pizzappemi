package sample.control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sample.NavegadorCenas;
import sample.model.Pizzaria;

import java.sql.SQLException;

public class JanelaCadastroCliente {
    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField tfAnoNascimento;

        public void cadastrar(){

        String nome = tfNome.getText();
        String telefone = tfTelefone.getText();
        int anoNascimento = Integer.valueOf(tfAnoNascimento.getText());

        try{
            Pizzaria.getInstance().cadastraCliente(nome,telefone,anoNascimento);
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Erro ao inserir cliente! "+e.getMessage());
            alert.showAndWait();
        }


        voltar();
    }

    public void voltar(){
        NavegadorCenas.loadJanela(NavegadorCenas.PRINCIPAL);
    }

}
