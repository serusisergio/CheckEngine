package it.unica.checkengine;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Avaria {
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

    public int getUrgenza() {
        return urgenza;
    }

    public void setUrgenza(int urgenza) {
        this.urgenza = urgenza;
    }
}
