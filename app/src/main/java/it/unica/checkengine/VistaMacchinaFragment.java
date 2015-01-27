package it.unica.checkengine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isadora on 25/01/2015.
 */
public class VistaMacchinaFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_GARAGE = "garage";

    public VistaMacchinaFragment() {
    }

    //richiama il file .xml in cui è definito il suo stile e il suo id
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vistamacchina_tab, container, false);
        Bundle bundle = getArguments();
        Garage garage = (Garage)bundle.getSerializable(ARG_GARAGE);
        Auto auto = garage.getAuto();
        Boolean flagAvaria = false;

        //creo i nomi per le varie immagini
        ImageView iconMotore = (ImageView) rootView.findViewById(R.id.iconMotore);
        ImageView iconFuel = (ImageView) rootView.findViewById(R.id.iconFuel);
        ImageView iconTributi = (ImageView) rootView.findViewById(R.id.iconTributi);
        ImageView iconOk = (ImageView) rootView.findViewById(R.id.iconOk);
        ImageView iconManutenzioni = (ImageView) rootView.findViewById(R.id.iconManutenzioni);
        //Creo una List che contiene tutte e 4 le ruote
        List<ImageView> iconRuote = new ArrayList<ImageView>();
        iconRuote.add((ImageView) rootView.findViewById(R.id.iconRuota1));
        iconRuote.add((ImageView) rootView.findViewById(R.id.iconRuota2));
        iconRuote.add((ImageView) rootView.findViewById(R.id.iconRuota3));
        iconRuote.add((ImageView) rootView.findViewById(R.id.iconRuota4));

        //Nascondo tutte le icon delle avarie

        iconMotore.setVisibility(View.INVISIBLE);
        iconFuel.setVisibility(View.INVISIBLE);
        iconTributi.setVisibility(View.INVISIBLE);
        iconManutenzioni.setVisibility(View.INVISIBLE);
        for( ImageView ruota : iconRuote ) {
            ruota.setVisibility(View.INVISIBLE);
        }

        Log.d("vistaMacchinaFragment","numMeccanico:" + garage.getNumMeccanico());

        //Verifico lo stato delle avarie - Inizio dei controlli
        //i valori sono da valutare
        if(auto.getLivelloOlio() < 2){
            iconMotore.setVisibility(View.VISIBLE);
            flagAvaria = true;
        }
        if(auto.getCarburante() < 5){
            iconFuel.setVisibility(View.VISIBLE);
            flagAvaria = true;
        }

        for(Avaria t : auto.getAvarie()){
            iconMotore.setVisibility(View.VISIBLE);
            flagAvaria = true;
        }

        for(Tributo t : auto.getTributi()){
            if (t.isScaduto()) iconTributi.setVisibility(View.VISIBLE);
        }
        for(Manutenzione t : auto.getManutenzioni()){
            if (t.isScaduto(auto.getKm())){
                if(t.getTipo().equals("Gomme")){
                    for( ImageView ruota : iconRuote ) {
                        ruota.setVisibility(View.VISIBLE);
                        flagAvaria = true;
                    }
                } else {
                    iconManutenzioni.setVisibility(View.VISIBLE);
                    flagAvaria = true;
                }
            }
        }
        Log.d("vistaMacchinaFragment","Livello Olio " +auto.getLivelloOlio());
        if(flagAvaria) iconOk.setVisibility(View.INVISIBLE);


        return rootView;
    }

}
