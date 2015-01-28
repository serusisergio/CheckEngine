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
    private List<String> messaggi;
    ArrayList<String> childList;

    public MyExpandableAdapter(Activity context){//}, List<String> messaggi, Map<String, List<String>> messageCollections) {
        this.context = context;

        ArrayList<String> groupList = new ArrayList<>();
        groupList.add("Segnalazioni");
        groupList.add("Manutenzioni");
        groupList.add("Tributi");


        // preparing messaggi collection(child)
        String[] segnalazioni = { "Livello Carburante" };
        String[] manutenzioni = { "Cambio Olio", "Tagliando", "Cambio Gomme" };
        String[] tributi = { "Assicurazione", "Bollo"};


        messageCollections = new LinkedHashMap<>();

        for (String laptop : groupList) {
            if (laptop.equals("Segnalazioni")) {
                loadChild(segnalazioni);
            } else if (laptop.equals("Manutenzioni"))
                loadChild(manutenzioni);
            else if (laptop.equals("Tributi"))
                loadChild(tributi);

            messageCollections.put(laptop, childList);
            this.messaggi = groupList;
        }

    }

    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<>();
        for (String model : laptopModels)
            childList.add(model);
    }

    public Object getChild(int groupPosition, int childPosition) {
        return messageCollections.get(messaggi.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.nomeRiga);

        ImageView delete = (ImageView) convertView.findViewById(R.id.semaforo);
        delete.setImageDrawable(convertView.getResources().getDrawable(R.drawable.semaforo_rosso));
        delete.setOnClickListener(new OnClickListener() {

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

        item.setText(laptop);
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