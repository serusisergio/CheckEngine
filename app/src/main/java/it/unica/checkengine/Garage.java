package it.unica.checkengine;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Garage {
    private String numMeccanico;
    private String numGommista;
    private List<Auto> auto;

    public Garage(){
        this.auto = new LinkedList<>();
    }

    public Garage(String numGommista, String numMeccanico){
        this.auto = new LinkedList<>();
        this.setNumGommista(numGommista);
        this.setNumMeccanico(numMeccanico);
    }

    public void addAuto(Auto a){
        this.auto.add(a);
    }

    public String getNumMeccanico() {
        return numMeccanico;
    }

    public void setNumMeccanico(String numMeccanico) {
        this.numMeccanico = numMeccanico;
    }

    public String getNumGommista() {
        return numGommista;
    }

    public void setNumGommista(String numGommista) {
        this.numGommista = numGommista;
    }

    public List<Auto> getAuto() {
        return auto;
    }
}
