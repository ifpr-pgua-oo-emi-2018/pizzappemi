package sample;

import sample.model.Pizza;

import java.sql.*;
import java.util.ArrayList;

public class Main2 {


    public static void main(String[] args) {

        try {
            /*Pizza p = new Pizza("bananacaxi",100.0);



            PreparedStatement stm = con.prepareStatement("INSERT INTO Pizzas(sabor,valor) VALUES (?,?)");

            stm.setString(1,p.getSabor());
            stm.setDouble(2,p.getValor());

            stm.executeUpdate();

            stm.close();
            con.close();*/

            Connection con = DriverManager.getConnection("jdbc:sqlite:pizzappemi.sqlite");


            Pizza p = new Pizza("peperoni", 100.0);

            PreparedStatement stm = con.prepareStatement("INSERT INTO Pizzas(sabor,valor) VALUES (?,?)");

            stm.setString(1, p.getSabor());
            stm.setDouble(2, p.getValor());

            stm.executeUpdate();

            stm.close();

            stm = con.prepareStatement("select seq from sqlite_sequence where name='pizzas'");


            ResultSet rs = stm.executeQuery();

            rs.next();
            System.out.println(rs.getInt("seq"));

            rs.close();
            stm.close();



        }catch (SQLException e){
            e.printStackTrace();
        }


    }


}
