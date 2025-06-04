package main.model;

public abstract class ModeloBase {

    private int id;

    public ModeloBase(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract void exibirDetalhes(); 
}