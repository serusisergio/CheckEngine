package it.unica.checkengine;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Garage implements Serializable{
    private String numMeccanico;
    private String numGommista;
    private Auto auto;

    public Garage(){
        }

    public Garage(String numGommista, String numMeccanico){
        this.setNumGommista(numGommista);
        this.setNumMeccanico(numMeccanico);
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

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }
}
