package it.unica.checkengine;

import java.util.Date;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Tributo {
    private String tipo;
    private String messaggio;
    private Date ultimaRicorrenza;
    private int intervalloPagameto;


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getUltimaRicorrenza() {
        return ultimaRicorrenza;
    }

    public void setUltimaRicorrenza(Date ultimaRicorrenza) {
        this.ultimaRicorrenza = ultimaRicorrenza;
    }

    public int getIntervalloPagameto() {
        return intervalloPagameto;
    }

    public void setIntervalloPagameto(int intervalloPagameto) {
        this.intervalloPagameto = intervalloPagameto;
    }
}
