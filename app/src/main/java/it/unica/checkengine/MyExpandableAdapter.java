package it.unica.checkengine;

/**
 * Created by sergio on 26/01/2015.
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyExpandableAdapter extends BaseExpandableListAdapter {


    private Activity context;
    private Map<String, List<String>> messageCollections;
    private Map<String, List<String>> semaforiCollections;
    private List<String> messaggi;
    ArrayList<String> childList;

    public MyExpandableAdapter(Activity context, Garage garage) {
        this.context = context;

        ArrayList<String> groupList = new ArrayList<>();
        groupList.add("Segnalazioni");
        groupList.add("Manutenzioni");
        groupList.add("Tributi");


       /* // preparing messaggi collection(child)
        String[] segnalazioni = { "Livello Carburante" };
        String[] manutenzioni = { "Cambio Olio", "Tagliando", "Cambio Gomme" };
        String[] tributi = { "Assicurazione", "Bollo"};*/

        ArrayList<String> segnalazioni = new ArrayList<>();
        ArrayList<String> semaforiSegnalazioni = new ArrayList<>();

        ArrayList<String> manutenzioni = new ArrayList<>();
        ArrayList<String> semaforiManutenzioni = new ArrayList<>();

        ArrayList<String> tributi = new ArrayList<>();
        ArrayList<String> semaforiTributi = new ArrayList<>();

        Auto auto = garage.getAuto();

        //Popolo la lista di segnalazioni

        segnalazioni.add("Livello Carburante");
        if(auto.isCarburanteOrange()){
            semaforiSegnalazioni.add("orange");
        } else if(auto.isCarburanteRed()){
            semaforiSegnalazioni.add("red");
        } else {
            semaforiSegnalazioni.add("green");
        }

        segnalazioni.add("Livello Olio");
        if(auto.isOlioRed()){
            semaforiSegnalazioni.add("red");
        } else {
            semaforiSegnalazioni.add("green");
        }

        for(Avaria a : auto.getAvarie()){
            segnalazioni.add(a.getTipo());
            semaforiSegnalazioni.add("red");
        }

        //Popolo la lista di manutenzioni
        for(Manutenzione m : auto.getManutenzioni()){
            manutenzioni.add(m.getTipo());
            if(m.isOrange(auto.getKm())){
                semaforiManutenzioni.add("orange");
            } else if(m.isRed(auto.getKm())){
                semaforiManutenzioni.add("red");
            } else {
                semaforiManutenzioni.add("green");
            }
        }

        //popolo la lista dei tributi
        for(Tributo t : auto.getTributi()){
            tributi.add(t.getTipo());
            if(t.isOrange()){
                semaforiTributi.add("orange");
            } else if(t.isRed()){
                semaforiTributi.add("red");
            } else {
                semaforiTributi.add("green");
            }
        }

        messageCollections = new LinkedHashMap<>();
        semaforiCollections = new LinkedHashMap<>();

        ArrayList<String> items = new ArrayList<>();
        ArrayList<String> semafori = new ArrayList<>();

        for (String sezione : groupList) {
            if (sezione.equals("Segnalazioni")) {
                items = segnalazioni;
                semafori = semaforiSegnalazioni;
            } else if (sezione.equals("Manutenzioni")){
                items = manutenzioni;
                semafori = semaforiManutenzioni;
            } else if (sezione.equals("Tributi")){
                items = tributi;
                semafori = semaforiTributi;
            }

            messageCollections.put(sezione, items);
            semaforiCollections.put(sezione, semafori);
            items = new ArrayList<>();
            this.messaggi = groupList;
        }

    }



    public Object getChild(int groupPosition, int childPosition) {
        return messageCollections.get(messaggi.get(groupPosition)).get(childPosition);
    }

    public String getSemaforo(int groupPosition, int childPosition) {
        return semaforiCollections.get(messaggi.get(groupPosition)).get(childPosition);
    }
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String riga = (String) getChild(groupPosition, childPosition);
        final String semaforo = getSemaforo(groupPosition, childPosition);

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.nomeRiga);

        ImageView ImageSemaforo = (ImageView) convertView.findViewById(R.id.semaforo);

        switch(semaforo){
            case "red":
                ImageSemaforo.setImageDrawable(convertView.getResources().getDrawable(R.drawable.semaforo_rosso));
                break;
            case "orange":
            ImageSemaforo.setImageDrawable(convertView.getResources().getDrawable(R.drawable.semaforo_arancio));
                break;
            case "green":
                ImageSemaforo.setImageDrawable(convertView.getResources().getDrawable(R.drawable.semaforo_verde));
                break;
        }

        ImageSemaforo.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<String> child =
                                        messageCollections.get(messaggi.get(groupPosition));
                                child.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        item.setText(riga);
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return messageCollections.get(messaggi.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return messaggi.get(groupPosition);
    }

    public int getGroupCount() {
        return messaggi.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.nomeGruppo);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(laptopName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}