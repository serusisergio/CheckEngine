package it.unica.checkengine;


import java.util.ArrayList;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class VistaListaFragment extends Fragment {

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private Context context;
    private Garage garage;

   /* @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // this is not really  necessary as ExpandableListActivity contains an ExpandableList
        //setContentView(R.layout.vistalista_tab);

        ExpandableListView expandableList = (ExpandableListView) rootView.findViewById(R.id.list);
        ExpandableListView expandableList = (ExpandableListView) findV

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        setGroupParents();
        setChildData();

        MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);
    }*/

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_GARAGE = "garage";

    public VistaListaFragment() {
   }

    //richiama il file .xml in cui è definito il suo stile e il suo id
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vistalista_tab, container, false);

        ExpandableListView expandableList = (ExpandableListView) rootView.findViewById(R.id.list);
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        Bundle bundle = getArguments();
        Garage garage = (Garage) bundle.getSerializable(ARG_GARAGE);

        setGroupParents();
        setChildData();

        MyExpandableAdapter adapter = new MyExpandableAdapter(getActivity(), parentItems, childItems);

        expandableList.setAdapter(adapter);

        // Listview Group click listener
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext()," Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expandableList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext()," Collapsed",Toast.LENGTH_SHORT).show();
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

        return rootView;
    }

    public void setGroupParents() {
        parentItems.add("Segnalazioni");
        parentItems.add("Manutenzioni");
        parentItems.add("Tributi");
    }

    public void setChildData() {

        // Android
        ArrayList<String> child = new ArrayList<String>();
        child.add("Core");
        child.add("Games");
        childItems.add(child);

        // Core Java
        child = new ArrayList<String>();
        child.add("Apache");
        child.add("Applet");
        child.add("AspectJ");
        child.add("Beans");
        child.add("Crypto");
        childItems.add(child);

        // Desktop Java
        child = new ArrayList<String>();
        child.add("Accessibility");
        child.add("AWT");
        child.add("ImageIO");
        child.add("Print");
        childItems.add(child);

    }

}
