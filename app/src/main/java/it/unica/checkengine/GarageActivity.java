package it.unica.checkengine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//http://www.androidhive.info/2012/01/android-json-parsing-tutorial/

public class GarageActivity extends ActionBarActivity {

    private ProgressDialog pDialog;

    private JSONObject garage;
    private JSONArray auto;
    // Hashmap for ListView
    private ArrayList<HashMap<String, String>> listaAuto;

    // URL to get JSON
    private static String url = "http://is0eir.altervista.org/ium/json.php";

    private ListView lista;

    public final static String EXTRA_MESSAGE = "it.unica.checkengine.TARGA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        /*String json = "{Auto:[{nome: Sergia, targa: DA111AD},{nome: Kakkina, targa: AA001AA}]}";

        String[] arrayAuto = {"Sergia", "Kakkina"};
        ListView lista = (ListView) findViewById(R.id.listAuto);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(lista.getContext(), android.R.layout.simple_list_item_1, arrayAuto);
        lista.setAdapter(adapter);*/

        lista = (ListView) findViewById(R.id.listAuto);
        new GetJson().execute();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("garage", "auto: " + position);
                try {
                    JSONObject autoScelta = auto.getJSONObject(position);
                    Log.d("garage", "targa: " + autoScelta.getString("targa"));
                    Intent intent = new Intent(parent.getContext(), MyCarActivity.class);
                    String message = autoScelta.getString("targa");
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private class GetJson extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            listaAuto =  new ArrayList<HashMap<String, String>>();

            // Showing progress dialog
            pDialog = new ProgressDialog(GarageActivity.this);
            pDialog.setMessage("Attendi...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    garage = jsonObj.getJSONObject("garage");

                    auto = garage.getJSONArray("auto");
                    Log.d("Response: ", "> " + auto);

                    // looping through All Contacts
                    for (int i = 0; i < auto.length(); i++) {
                        JSONObject c = auto.getJSONObject(i);

                        String nomeAuto = c.getString("nome");
                        String targaAuto = c.getString("targa");

                        // tmp hashmap for single contact
                        HashMap<String, String> mappaAuto = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        mappaAuto.put("nome", nomeAuto);
                        mappaAuto.put("targa", targaAuto);


                        // adding contact to contact list
                        listaAuto.add(mappaAuto);
                        Log.d("bcktask", "iterazione " + i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    GarageActivity.this, listaAuto,
                    R.layout.list_item, new String[] { "nome", "targa" },
                    new int[] { R.id.nome, R.id.targa});
            Log.d("postexecute", listaAuto.toString());
            lista.setAdapter(adapter);
        }

    }

}
