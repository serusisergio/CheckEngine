package it.unica.checkengine;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    public VistaMacchinaFragment() { }

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

        //Verifico lo stato delle avarie - Inizio dei controlli
        if(auto.isCarburanteOrange() || auto.isCarburanteRed()){
            iconFuel.setVisibility(View.VISIBLE);
            Log.d("Vista macchina", "il carburante è rosso o arancione e il valore è :"+auto.getCarburante());
            flagAvaria = true;
            iconFuel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getApplicationContext(), "Metti benzina tanalla miserabile", Toast.LENGTH_SHORT).show();
                }
            });

        }
        if(auto.isOlioRed()){
            iconMotore.setVisibility(View.VISIBLE);
            flagAvaria = true;
            iconMotore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getApplicationContext(), "Sta finendo l'olio, OLIOOOO!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        for(Avaria t : auto.getAvarie()){
            iconMotore.setVisibility(View.VISIBLE);
            flagAvaria = true;
            iconMotore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getApplicationContext(), "Avaria! Avaria!", Toast.LENGTH_SHORT).show();
                }
            });
            //se trova un'avaria illumina l'icona e esce subito per evitare che richiami piu di una volta l'onClick
            break;
        }

        for(Tributo t : auto.getTributi()){
            if (t.isScaduto()) iconTributi.setVisibility(View.VISIBLE);
            iconTributi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getApplicationContext(), "Paga le tasse!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        for(Manutenzione t : auto.getManutenzioni()){
            if (t.isScaduto(auto.getKm())){
                if(t.getTipo().equals("Gomme")){
                    for( ImageView ruota : iconRuote ) {
                        ruota.setVisibility(View.VISIBLE);
                        ruota.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity().getApplicationContext(), "Ruota a terra!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    iconManutenzioni.setVisibility(View.VISIBLE);
                    iconManutenzioni.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity().getApplicationContext(), "Manutienimi!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                flagAvaria = true;
            }
        }
        if(flagAvaria) iconOk.setVisibility(View.INVISIBLE);

        return rootView;

    }

}
