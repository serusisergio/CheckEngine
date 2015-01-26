package it.unica.checkengine;

import java.util.Date;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Tributo {
    private String tipo;
    private String messaggio;
    private double importo;
    private Date ultimaRicorrenza;
    private int intervalloPagameto;
    private int giorniAllScadenza;

    public Tributo(){

    }

    public Tributo(String tipo, double importo, Date ultimaRicorrenza, int intervalloPagameto){
        setTipo(tipo);
        setImporto(importo);
        setUltimaRicorrenza(ultimaRicorrenza);
        setImporto(intervalloPagameto);
    }

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

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public int getGiorniAllScadenza() {
        return giorniAllScadenza;
    }

    public void setGiorniAllScadenza(int giorniAllScadenza) {
        this.giorniAllScadenza = giorniAllScadenza;
    }
}
