package it.unica.checkengine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Isadora on 25/01/2015.
 */
public class VistaListaFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public VistaListaFragment() {
    }

    //richiama il file .xml in cui è definito il suo stile e il suo id
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vistalista_tab, container, false);
        TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label2);
        return rootView;
    }
}
