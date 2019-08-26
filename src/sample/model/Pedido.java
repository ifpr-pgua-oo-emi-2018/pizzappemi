package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Pedido {

    private int id;
    private Cliente cliente;
    private ObservableList<Pizza> pizzas;
    private Double valorTotal;
    private LocalDateTime data;

    public Pedido(){
        pizzas = FXCollections.observableArrayList();
    }

    public void incluir(Pizza p){
        pizzas.add(p);
    }

    public Double getValorTotal(){

        valorTotal=0.0;
        for(Pizza p:pizzas){
            valorTotal += p.getValor();
        }

        return valorTotal;
    }

    public ObservableList<Pizza> listaPizzas(){
        return pizzas;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
