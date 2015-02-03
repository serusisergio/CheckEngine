package it.unica.checkengine;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Stefano on 26/01/2015.
 */
public class JSONParser {
    public Garage parse(String json){

        Garage garage;
        Auto auto;
        Avaria avaria;
        Tributo tributo;
        Manutenzione manutenzione;

        try {
            if(json==null){
                Log.d("JSONparser", "è null");
            }

            JSONObject JSON = new JSONObject(json);
            JSONObject jGarage = JSON.getJSONObject("garage");
            garage = new Garage(jGarage.getString("nGommista"), jGarage.getString("nMeccanico"));

            JSONObject jAuto = JSON.getJSONObject("auto");
            auto = new Auto(jAuto.getString("targa"),
                            jAuto.getString("modello"),
                            jAuto.getDouble("carburante"),
                            jAuto.getDouble("olio"),
                            jAuto.getInt("km"),
                            jAuto.getString("nome"),
                            jAuto.getInt("kmGiornalieri"),
                    jAuto.getDouble("consumoMedio"),
                    jAuto.getInt("sogliaAvvisoCarburante"));

            JSONArray jManutenzioni = jAuto.getJSONArray("manutenzioni");
            JSONArray jAvarie = jAuto.getJSONArray("avarie");
            JSONArray jTributi = jAuto.getJSONArray("tributi");

            for(int i=0; i<jManutenzioni.length(); i++){
                JSONObject m = jManutenzioni.getJSONObject(i);
                manutenzione = new Manutenzione(m.getString("tipo"),
                        m.getInt("kmUltimaRicorrenza"),
                        m.getInt("kmIntervallo"));

                if(manutenzione.isSogliaSuperata(auto.getKm())) {
                    if (manutenzione.getTipo().equals("Tagliando")){
                        manutenzione.setMessaggio("Il "+manutenzione.getTipo() + " è da eseguire tra meno di 200 km. Prendi appuntamento col tuo professionista di fiducia.");
                    }else if(manutenzione.getTipo().equals("Cambio gomme")){
                        manutenzione.setMessaggio("Il "+manutenzione.getTipo() + " è da eseguire tra meno di 200 km. Prendi appuntamento col tuo gommista di fiducia.");
                    }else if(manutenzione.getTipo().equals("Cambio olio")){
                        manutenzione.setMessaggio("Il "+manutenzione.getTipo() + " è da eseguire tra meno di 200 km. Prendi appuntamento col tuo meccanico di fiducia.");
                    }else{
                        manutenzione.setMessaggio(manutenzione.getTipo() + " è da eseguire tra meno di 200 km. Prendi appuntamento col tuo professionista di fiducia.");
                    }
                } else if(manutenzione.isScaduta(auto.getKm())){
                    if(manutenzione.getTipo().equals("Tagliando")) {
                        manutenzione.setMessaggio("Il tagliando è da eseguire al più presto possibile. Prendi appuntamento col tuo meccanico di fiducia.");
                    }else if(manutenzione.getTipo().equals("Cambio gomme")){
                        manutenzione.setMessaggio("Il cambio gomme è da eseguire tra meno di 200 km. Prendi appuntamento col tuo gommista di fiducia.");
                    }else if(manutenzione.getTipo().equals("Cambio olio")){
                        manutenzione.setMessaggio("Il cambio olio è da eseguire tra meno di 200 km. Prendi appuntamento col tuo meccanico di fiducia.");
                    }else{
                        manutenzione.setMessaggio(manutenzione.getTipo() + " è da eseguire tra meno di 200 km. Prendi appuntamento col tuo professionista di fiducia.");
                    }
                } else {
                    if(manutenzione.getTipo().equals("Tagliando")){
                        manutenzione.setMessaggio("Il tagliando è da eseguire tra "+manutenzione.getKmAllaScadenza(auto.getKm())+" Km.");
                    }else if(manutenzione.getTipo().equals("Cambio gomme")){
                        manutenzione.setMessaggio("Il cambio gomme è da eseguire tra "+manutenzione.getKmAllaScadenza(auto.getKm())+" Km.");
                    }else if(manutenzione.getTipo().equals("Cambio olio")){
                        manutenzione.setMessaggio("Il cambio olio è da eseguire tra "+manutenzione.getKmAllaScadenza(auto.getKm())+" Km.");
                    }else{
                        manutenzione.setMessaggio(manutenzione.getTipo() +" è da eseguire tra "+manutenzione.getKmAllaScadenza(auto.getKm())+" Km.");
                    }
                }
                auto.addManutenzione(manutenzione);
            }

            for(int i=0; i<jAvarie.length(); i++){
                JSONObject a = jAvarie.getJSONObject(i);
                avaria = new Avaria(a.getString("tipo"), a.getInt("urgenza"));
                String messaggio;
                if(avaria.getTipo().equals("Avaria luci")) {
                    messaggio = "L'auto ha segnalato un problema all'impianto luci. Prendi appuntamento col tuo meccanico o elettrauto di fiducia.";
                }else if(avaria.getTipo().equals("Avaria freni")){
                    messaggio = "L'auto ha segnalato un problema all'impianto freni. Prendi appuntamento col tuo meccanico di fiducia.";
                }else if(avaria.getTipo().equals("Foratura Gomme")){
                    messaggio= "L'auto ha segnalato un problema alle gomme, pressione insufficiente, possibile foratura. Prendi appuntamento col tuo gommista di fiducia.";
                }else if(avaria.getTipo().equals("Avaria batteria")){
                    messaggio= "L'auto ha segnalato un problema di bassa tensione della batteria. Prendi appuntamento col tuo meccanico di fiducia.";
                }else{
                    messaggio = "L'auto ha segnalato un problema a " + avaria.getTipo() + ". Prendi appuntamento col tuo professionista di fiducia.";
                }
                avaria.setMessaggio(messaggio);

                auto.addAvaria(avaria);
            }

            for(int i=0; i<jTributi.length(); i++){
                JSONObject t = jTributi.getJSONObject(i);
                tributo = new Tributo(t.getString("nome"), t.getDouble("costo"), parseDate(t.getString("ultimaRicorrenza")), t.getInt("cadenza"));
                Log.d("parseTributo", t.getInt("cadenza")+"");

                Date dataOggi = new Date();

                //2592000000l = 30 giorni
                Date dataAvviso = new Date(tributo.getUltimaRicorrenza().getTime() + tributo.getIntervalloPagameto()*86400000l - 2592000000l);
                Date dataScadenza = new Date(tributo.getUltimaRicorrenza().getTime() + tributo.getIntervalloPagameto()*86400000l);

                Long l = new Long((dataOggi.getTime() - tributo.getIntervalloPagameto())/86400000l);
                tributo.setGiorniAllScadenza(tributo.getIntervalloPagameto() - l.intValue());
                if(dataOggi.after(dataAvviso) && !dataOggi.after(dataScadenza)){
                    tributo.setMessaggio(tributo.getTipo() + " scadrà tra meno di 30 giorni. Ricordati che l'importo da pagare è "+tributo.getImporto()+"€");
                } else if(dataOggi.after(dataScadenza)){
                    if(tributo.getTipo().equals("Revisione")) {
                        tributo.setMessaggio("La " + tributo.getTipo() + " è scaduta. L'importo da pagare è circa " + tributo.getImporto() + "€");
                    }else if(tributo.getTipo().equals("Bollo" )){
                        tributo.setMessaggio("Il " + tributo.getTipo() + " è scaduto. L'importo da pagare è circa " + tributo.getImporto() + "€");
                    }else if(tributo.getTipo().equals("Assicurazione")){
                        tributo.setMessaggio("L'" + tributo.getTipo() + " è scaduta. L'importo da pagare è circa " + tributo.getImporto() + "€");
                    }else{
                        tributo.setMessaggio(tributo.getTipo() + " è da pagare tra " + tributo.getGiorniAllScadenza() + ". L'importo è circa" + tributo.getImporto() + "€");

                    }
                } else {
                    if(tributo.getTipo().equals("Revisione")) {
                        tributo.setMessaggio("La "+ tributo.getTipo() + " è da pagare tra " + tributo.getGiorniAllScadenza() + ". L'importo è circa" + tributo.getImporto() + "€");
                    }else if(tributo.getTipo().equals("Bollo")){
                        tributo.setMessaggio("Il "+ tributo.getTipo() + " è da pagare tra " + tributo.getGiorniAllScadenza() + ". L'importo è circa" + tributo.getImporto() + "€");
                    }else if(tributo.getTipo().equals("Assicurazione")){
                        tributo.setMessaggio("L'"+ tributo.getTipo() + " è da pagare tra " + tributo.getGiorniAllScadenza() + ". L'importo è circa" + tributo.getImporto() + "€");
                    }else{
                        tributo.setMessaggio(tributo.getTipo() + " è da pagare tra " + tributo.getGiorniAllScadenza() + ". L'importo è circa" + tributo.getImporto() + "€");
                    }
                }

                auto.addTributo(tributo);
            }

            garage.setAuto(auto);

            return garage;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Date parseDate(String sData){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = sf.parse(sData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }
}
