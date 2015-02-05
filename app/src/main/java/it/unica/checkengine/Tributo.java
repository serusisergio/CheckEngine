package it.unica.checkengine;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Tributo implements Serializable{
    private String tipo;
    private String messaggio;
    private double importo;
    private Date ultimaRicorrenza;
    private int intervalloPagamento;
    private int giorniAllaScadenza;

    public Tributo(){

    }

    public Tributo(String tipo, double importo, Date ultimaRicorrenza, int intervalloPagamento) {
        setTipo(tipo);
        setImporto(importo);
        setUltimaRicorrenza(ultimaRicorrenza);
        setIntervalloPagamento(intervalloPagamento);
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

    public int getIntervalloPagamento() {
        return intervalloPagamento;
    }

    public void setIntervalloPagamento(int intervalloPagamento) {
        this.intervalloPagamento = intervalloPagamento;
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

    public int getGiorniAllaScadenza() {
        return giorniAllaScadenza;
    }

    public void setGiorniAllaScadenza(int giorniAllaScadenza) {
        this.giorniAllaScadenza = giorniAllaScadenza;
    }

    public boolean isRed(){
        Date dataOggi = new Date();
        Date dataScadenza = new Date(getUltimaRicorrenza().getTime() + getIntervalloPagamento() * 86400000l);
        //return dataOggi.after(dataScadenza);
        Log.d("dataoggi", ""+dataOggi.getTime());
        Log.d("datascadenza", ""+dataScadenza.getTime());
        Log.d("intervallopagamento", "" + getIntervalloPagamento());
        return dataOggi.getTime()>dataScadenza.getTime();
    }

    public boolean isOrange(){
        Date dataOggi = new Date();
        //2592000000l = 30 giorni in unix time
        Date dataAvviso = new Date(getUltimaRicorrenza().getTime() + getIntervalloPagamento() * 86400000l - 2592000000l);
        return dataOggi.getTime() > dataAvviso.getTime() && !isRed();
    }
}
