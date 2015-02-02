package it.unica.checkengine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class VistaListaFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_GARAGE = "garage";
    public static final String ARG_SEZIONE = "sezione";
    ExpandableListView expandableList;
    private Garage garage;

    public VistaListaFragment() {
    }

    //richiama il file .xml in cui è definito il suo stile e il suo id
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vistalista_tab, container, false);

        expandableList = (ExpandableListView) rootView.findViewById(R.id.list);
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        Bundle bundle = getArguments();
        Garage garage = (Garage) bundle.getSerializable(ARG_GARAGE);

        MyExpandableAdapter adapter = new MyExpandableAdapter(getActivity(), garage);

        expandableList.setAdapter(adapter);

        // Listview Group click listener
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview on child click listener
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "ciao", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        settaSezioni(-1);

        return rootView;
    }

    public void settaSezioni(int sezioneDaAprire) {

        Log.d("VistaListaFragment", "Tento di aprire la sezione " + sezioneDaAprire);

        //apro la sezione richiesta
        switch (sezioneDaAprire) {
            case 0:
                expandableList.expandGroup(0);
                expandableList.collapseGroup(1);
                expandableList.collapseGroup(2);
                break;
            case 1:
                expandableList.expandGroup(1);
                expandableList.collapseGroup(0);
                expandableList.collapseGroup(2);
                break;
            case 2:
                expandableList.expandGroup(2);
                expandableList.collapseGroup(1);
                expandableList.collapseGroup(0);
                break;
            default:
                expandableList.expandGroup(0);
                expandableList.expandGroup(1);
                expandableList.expandGroup(2);
        }

    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        if (visible == true)
            Toast.makeText(getActivity().getApplicationContext(), "Clicca sugli elementi della lista per aprire il dettaglio", Toast.LENGTH_SHORT).show();
    }

}
