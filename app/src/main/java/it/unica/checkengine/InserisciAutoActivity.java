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
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class InserisciAutoActivity extends ActionBarActivity {
    private ProgressDialog pDialog;
    String output;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserisci_auto);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inserisci_auto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void inserisciAuto(View view) {
        String targa, modello, nome;
        int kmGiornalieri, consumoMedio;

        EditText eTarga, eModello, eNome, eKmGiornalieri, eConsumomedio;

        eTarga = (EditText) findViewById(R.id.targaAuto);
        targa = eTarga.getText().toString();

        eModello = (EditText) findViewById(R.id.modelloAuto);
        modello = eModello.getText().toString();

        eNome = (EditText) findViewById(R.id.nomeAuto);
        nome = eNome.getText().toString();

        eKmGiornalieri = (EditText) findViewById(R.id.kmGiornalieriAuto);
        if (eKmGiornalieri.getText().toString().equals("")) {
            kmGiornalieri = 0;
        } else {
            kmGiornalieri = Integer.parseInt(eKmGiornalieri.getText().toString());
        }


        eConsumomedio = (EditText) findViewById(R.id.consumoAuto);
        if (eConsumomedio.getText().toString().equals("")) {
            consumoMedio = 0;
        } else {
            consumoMedio = Integer.parseInt(eConsumomedio.getText().toString());
        }

        url = "http://checkengine.matta.ga/inserisciauto.php?masterkey=98HT984RH34R0834H984TH&targa=" + targa + "&modello=" + modello + "&nome=" + nome + "&kmGiornalieri=" + kmGiornalieri + "&consumoMedio=" + consumoMedio;

        new InsertAuto().execute();


        Log.d("InserisciAutoActivity: ", "Ho invocato il thread");


        while(output == null) {

        }

        if (new String("error").equals("ok")) {
            Toast.makeText(this, output, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, GarageActivity.class);
            this.startActivity(intent);

        } else {
            Toast.makeText(this, "Errore, riprova!", Toast.LENGTH_LONG).show();
        }
    }


    private class InsertAuto extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            ProgressDialog pDialog = new ProgressDialog(InserisciAutoActivity.this);
            pDialog.setMessage("Attendi...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            output = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("InserisciAutoActivity output", ">"+output+"<");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }}
