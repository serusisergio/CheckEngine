package it.unica.checkengine;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Manutenzione {
    private String tipo;
    private String messaggio;
    private int kmUltimaRicorrenza;
    private int intervalloRicorrenza;

    public Manutenzione(String tipo, String messaggio, int kmUltimaRicorrenza, int intervalloRicorrenza){
        setTipo(tipo);
        setMessaggio(messaggio);
        setKmUltimaRicorrenza(kmUltimaRicorrenza);
        setIntervalloRicorrenza(intervalloRicorrenza);
    }

    public Manutenzione(String tipo, int kmUltimaRicorrenza, int intervalloRicorrenza){
        setTipo(tipo);
        setKmUltimaRicorrenza(kmUltimaRicorrenza);
        setIntervalloRicorrenza(intervalloRicorrenza);
    }

    public Manutenzione(){

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

    public int getKmUltimaRicorrenza() {
        return kmUltimaRicorrenza;
    }

    public void setKmUltimaRicorrenza(int kmUltimaRicorrenza) {
        this.kmUltimaRicorrenza = kmUltimaRicorrenza;
    }

    public int getIntervalloRicorrenza() {
        return intervalloRicorrenza;
    }

    public void setIntervalloRicorrenza(int intervalloRicorrenza) {
        this.intervalloRicorrenza = intervalloRicorrenza;
    }
}
