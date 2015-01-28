package it.unica.checkengine;

import java.io.Serializable;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Avaria implements Serializable{
    private String tipo;
    private String messaggio;
    private int urgenza;

    public Avaria(){

    }

    public Avaria(String tipo, int urgenza){
        setTipo(tipo);
        setUrgenza(urgenza);
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public boolean isUrgenzaOrange(){
        return urgenza==1;
    }

    public boolean isUrgenzaRed(){
        return urgenza==0;
    }

    public void setUrgenza(int urgenza) {
        this.urgenza = urgenza;
    }
}
