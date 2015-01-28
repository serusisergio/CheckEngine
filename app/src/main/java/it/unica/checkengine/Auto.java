package it.unica.checkengine;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Auto implements Serializable{

    private static final int OLIO_MIN = 3;
    private static final int KM_MIN = 100;
    private static final int CARBURANTE_RISERVA = 5;
    private double carburante ;/*possimamo farlo statico, direi che varia tra 0 e 47 litri*/
    private double livelloOlio ; /*la macchina ha sui 4 litri di olio */
    private int km ;
    private String nome;
    private List<Manutenzione> manutenzioni;
    private List<Tributo> tributi;
    private List<Avaria> avarie;
    private int kmGiornalieri;
    private double consumoMedio;

    public Auto(double carburante, double livelloOlio, int km, String nome, int kmGiornalieri, double consumoMedio){
        this.setCarburante(carburante);
        this.setLivelloOlio(livelloOlio);
        this.setKm(km);
        this.setNome(nome);
        this.setKmGiornalieri(kmGiornalieri);
        this.setConsumoMedio(consumoMedio);

        this.manutenzioni = new LinkedList<>();
        this.tributi = new LinkedList<>();
        this.avarie = new LinkedList<>();
    }

    public Auto(){
        this.manutenzioni = new LinkedList<>();
        this .tributi = new LinkedList<>();
        this.avarie = new LinkedList<>();
    }

    public boolean isCarburanteOrange(){
        return (carburante*consumoMedio < KM_MIN);
    }

    public boolean isCarburanteRed(){
        return (carburante<CARBURANTE_RISERVA);
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

    public double getCarburante() {
        return carburante;
    }

    public void setCarburante(double carburante) {

            this.carburante = carburante;

    }

    public double getLivelloOlio() {
        return livelloOlio;
    }

    public void setLivelloOlio(double livelloOlio) {

            this.livelloOlio = livelloOlio;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getConsumoMedio() {
        return consumoMedio;
    }

    public void setConsumoMedio(double consumoMedio) {
        this.consumoMedio = consumoMedio;
    }
}
