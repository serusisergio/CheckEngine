package it.unica.checkengine;

/**
 * Created by Stefano on 23/01/2015.
 */
public class Auto {

    private int carburante ;/*possimamo farlo statico, direi che varia tra 0 e 47 litri*/
    private int livelloOlio ; /*la macchina ha sui 4 litri di olio */
    private int km ;
    private String nome;

    public Auto(){
        this.setCarburante(25);
        this.setLivelloOlio(4);
        this.setKm(0); /*supponiamo che l'abbia ppena comprata? */
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
}
