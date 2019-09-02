package sample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    private static int MAX_CONNECTIONS=5;
    private static FabricaConexao instance = new FabricaConexao();


    private static String ENDERECO_SERVIDOR = "localhost";
    private static String NOME_BANCO = "pizzapp";

    private static String USER="user";
    private static String PASSWORD="user12345";

    private static String STRING_CONEXAO_MYSQL="jdbc:mysql://"+ENDERECO_SERVIDOR+"/"+NOME_BANCO;
    private static String STRING_CONEXAO_SQLITE="jdbc:sqlite:pizzappemi.sqlite";

    private static String STRING_CONEXAO = STRING_CONEXAO_MYSQL;



    private static Connection[] connections;

    private FabricaConexao(){
        connections = new Connection[MAX_CONNECTIONS];
    }


    public static Connection getConnection() throws SQLException{

        for(int i=0;i<MAX_CONNECTIONS;i++){
            if(connections[i] ==null || connections[i].isClosed()){
                connections[i] = DriverManager.getConnection(STRING_CONEXAO,USER,PASSWORD);
                return connections[i];
            }
        }

        throw new SQLException("Muitas conexoes abertas!!!");

    }



}
