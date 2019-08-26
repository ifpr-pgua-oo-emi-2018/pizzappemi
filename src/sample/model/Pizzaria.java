package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Pizzaria {

    private String ARQ = "pizzas.bin";

    PizzaDAO pizzaDAO = new PizzaDAOImpl();

    ClienteDAO clienteDAO = new ClienteDAOImpl();

    PedidoDAO pedidoDAO = new PedidoDAOImpl();

    private ObservableList<Pizza> sabores;
    private ObservableList<Cliente> clientes;

    private Pedido pedido;

    private static Pizzaria instance=new Pizzaria();

    private Pizzaria(){
        sabores = FXCollections.observableArrayList();
        clientes = FXCollections.observableArrayList();
    }

    public static Pizzaria getInstance(){
        return instance;
    }

    public void cadastraPizza(String sabor, Double valor) throws SQLException{

        pizzaDAO.insere(sabor,valor);

    }

    public void cadastraCliente(String nome, String telefone, int anoNascimento) throws SQLException{
        clienteDAO.insere(nome, telefone, anoNascimento);
    }

    public void abrirPedido() throws Exception{
        if(pedido == null){
            pedido = new Pedido();
        }else{
            throw new Exception("Pedido já aberto!!");
        }

    }

    public void incluirPizza(Pizza p) throws Exception{
        if(pedido != null){
            pedido.incluir(p);
        }else{
            throw new Exception("Pedido fechado!!");
        }

    }


    public void incluiClientePedido(Cliente c) throws Exception{

        if(pedido != null){
            pedido.setCliente(c);
        }else{
            throw new Exception("Pedido fechado!!!");
        }
    }

    public Double fecharPedido() throws Exception{




        Double valor=0.0;
        if(pedido != null){
            valor = pedido.getValorTotal();

            //insere o pedido no banco de dados
            pedidoDAO.insere(pedido);


            pedido = null;
            return valor;
        }else {
            throw  new Exception("Pedido fechado!!");
        }



    }

    public ObservableList listaSabores() throws SQLException{

        sabores.clear();

        sabores.addAll(pizzaDAO.lista());

        return sabores;
    }

    public Double getValorPedido(){
        if(pedido != null){
            return pedido.getValorTotal();
        }

        return 0.0;
    }

    public ObservableList getListaPedido(){
        if(pedido != null){
            return pedido.listaPizzas();
        }else{
            return FXCollections.emptyObservableList();
        }
    }

    public ObservableList buscaPizza(String texto){
        sabores.clear();

        try{

            Connection con = DriverManager.getConnection("jdbc:sqlite:pizzappemi.sqlite");

            PreparedStatement stm = con.prepareStatement("SELECT * FROM PIZZAS where SABOR like ?");

            stm.setString(1,"%"+texto+"%");

            ResultSet res = stm.executeQuery();

            while(res.next()){
                int id = res.getInt("ID");
                String sabor = res.getString("SABOR");
                Double valor = res.getDouble("VALOR");

                Pizza p = new Pizza(id,sabor,valor);

                sabores.add(p);
            }

            res.close();
            stm.close();
            con.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return sabores;
    }

    public ObservableList buscaCliente(String texto) throws SQLException{
        clientes.clear();

        clientes.addAll(clienteDAO.buscaNome(texto));

        return clientes;
    }



    public ObservableList listaClientes() throws SQLException{
        clientes.clear();

        clientes.addAll(clienteDAO.lista());

        return clientes;
    }



    public void salva() throws IOException {

        File f = new File(ARQ);
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(new ArrayList<>(sabores));

        oos.close();
        fos.close();

    }

    public void carrega() throws IOException,ClassNotFoundException{

        File f = new File(ARQ);
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);


        ArrayList pizzas = (ArrayList) ois.readObject();

        sabores.addAll(pizzas);


        ois.close();
        fis.close();

    }


}
