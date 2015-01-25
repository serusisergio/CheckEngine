package it.unica.checkengine;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Auto {

    private int carburante ;/*possimamo farlo statico, direi che varia tra 0 e 47 litri*/
    private int livelloOlio ; /*la macchina ha sui 4 litri di olio */
    private int km ;
    private String nome;
    private List<Manutenzione> manutenzioni;
    private List<Tributo> tributi;
    private List<Avaria> avarie;
    private int kmGiornalieri;

    public Auto(){
        this.setCarburante(25);
        this.setLivelloOlio(4);
        this.setKm(0); /*supponiamo che l'abbia ppena comprata? */

        this.manutenzioni = new LinkedList<>();
        this .tributi = new LinkedList<>();
        this.avarie = new LinkedList<>();
    }

    public void addManutenzione(Manutenzione m){
        this.getManutenzioni().add(m);
    }

    public void addTributo(Tributo t){
        this.getTributi().add(t);
    }

    public void addAvaria(Avaria a){
        this.getAvarie().add(a);
    }

    public int getCarburante() {
        return carburante;
    }

    public void setCarburante(int carburante) {
        if(carburante>0&carburante<47 ) {
            this.carburante = carburante;
        }
    }

    public int getLivelloOlio() {
        return livelloOlio;
    }

    public void setLivelloOlio(int livelloOlio) {
        if(livelloOlio>0&livelloOlio<4) {
            this.livelloOlio = livelloOlio;
        }
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getKmGiornalieri() {
        return kmGiornalieri;
    }

    public void setKmGiornalieri(int kmGiornalieri) {
        this.kmGiornalieri = kmGiornalieri;
    }

    public List<Manutenzione> getManutenzioni() {
        return manutenzioni;
    }

    public List<Tributo> getTributi() {
        return tributi;
    }

    public List<Avaria> getAvarie() {
        return avarie;
    }
}
