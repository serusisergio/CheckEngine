package it.unica.checkengine;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

            JSONObject jGarage = new JSONObject(json);
            garage = new Garage(jGarage.getString("nGommista"), jGarage.getString("nMeccanico"));

            JSONObject jAuto = jGarage.getJSONObject("auto");
            auto = new Auto(jAuto.getDouble("carburante"),
                            jAuto.getDouble("olio"),
                            jAuto.getInt("km"),
                            jAuto.getString("nome"),
                            jAuto.getInt("kmGiornalieri"),
                            jAuto.getDouble("consumoMedio"));

            JSONArray jManutenzioni = jAuto.getJSONArray("manutenzioni");
            JSONArray jAvarie = jAuto.getJSONArray("avarie");
            JSONArray jTributi = jAuto.getJSONArray("tributi");

            for(int i=0; i<jManutenzioni.length(); i++){
                JSONObject m = jManutenzioni.getJSONObject(i);
                manutenzione = new Manutenzione(m.getString("tipo"),
                        m.getInt("kmUltimaRicorrenza"),
                        m.getInt("kmIntervallo"));

                if(manutenzione.getKmUltimaRicorrenza()+manutenzione.getIntervalloRicorrenza()>= auto.getKm() + 200){
                    manutenzione.setMessaggio(manutenzione.getTipo() + "è da eseguire tra meno di 200 km. Prendi appuntamento col tuo professionista di fiducia");
                } else if(manutenzione.getKmUltimaRicorrenza()+manutenzione.getIntervalloRicorrenza()>= auto.getKm()){
                    manutenzione.setMessaggio(manutenzione.getTipo() + "è da eseguire al più presto possibile. Prendi appuntamento col tuo professionista di fiducia");
                } else {
                    manutenzione.setMessaggio(manutenzione.getTipo() + "è da eseguire tra " + (manutenzione.getIntervalloRicorrenza() - (auto.getKm()-manutenzione.getKmUltimaRicorrenza()))
                                            + " km");
                }

                auto.addManutenzione(manutenzione);
            }

            for(int i=0; i<jAvarie.length(); i++){
                JSONObject a = jAvarie.getJSONObject(i);
                avaria = new Avaria(a.getString("tipo"), a.getInt("urgenza"));

                String messaggio = "L'auto ha segnalato un problema a " + avaria.getTipo() + ". Prendi appuntamento col tuo professionista di fiducia";
                avaria.setMessaggio(messaggio);

                auto.addAvaria(avaria);
            }

            for(int i=0; i<jTributi.length(); i++){
                JSONObject t = jTributi.getJSONObject(i);
                tributo = new Tributo(t.getString("nome"), t.getDouble("costo"), parseDate(t.getString("ultimaRicorrenza")), t.getInt("cadenza"));

                Date dataOggi = new Date();

                //2592000000l = 30 giorni
                Date dataAvviso = new Date(tributo.getUltimaRicorrenza().getTime() + tributo.getIntervalloPagameto()*86400000l - 2592000000l);
                Date dataScadenza = new Date(tributo.getUltimaRicorrenza().getTime() + tributo.getIntervalloPagameto()*86400000l);

                Long l = new Long((dataOggi.getTime() - tributo.getIntervalloPagameto())/86400000l);
                tributo.setGiorniAllScadenza(tributo.getIntervalloPagameto() - l.intValue());

                if(dataOggi.after(dataAvviso) && !dataOggi.after(dataScadenza)){
                    tributo.setMessaggio(tributo.getTipo() + " scadrà tra meno di 30 giorni. Ricordati che l'importo da pagare è "+tributo.getImporto());
                } else if(dataOggi.after(dataScadenza)){
                    tributo.setMessaggio(tributo.getTipo() + " è scaduto. Ricordati che l'importo da pagare è "+tributo.getImporto());
                } else {
                    tributo.setMessaggio(tributo.getTipo() + " è da pagare tra " + tributo.getGiorniAllScadenza() + ". Ricordati che l'importo da pagare è "+tributo.getImporto());
                }

                auto.addTributo(tributo);
            }


            return garage;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Date parseDate(String sData){
        String[] el = sData.split("-");
        Integer year = new Integer(el[0]);
        Integer month = new Integer(el[1]);
        Integer day = new Integer(el[2]);
        Date data = new Date(year, month, day);
        return data;
    }
}
