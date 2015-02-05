package it.unica.checkengine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DettaglioManutenzioneActivity extends ActionBarActivity {
    public static final String ARG_GARAGE = "garage";
    private Garage garage;
    private ProgressDialog pDialog;
    private String output, url;
    private Manutenzione manutenzione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_manutenzione);

        garage = (Garage) getIntent().getSerializableExtra(ARG_GARAGE);
        Auto auto = garage.getAuto();
        manutenzione = (Manutenzione) getIntent().getSerializableExtra("dettagliManutenzione");
        String tipoManutenzione = manutenzione.getTipo();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //imposto il titolo della actionbar come da prototipo
        setTitle(auto.getModello() + " - " + auto.getNome());


        TextView sottotitolo = (TextView) findViewById(R.id.text_sottotitolo);
        sottotitolo.setText(tipoManutenzione);

        ImageView semaforo = (ImageView) findViewById(R.id.semaforo);
        String coloreSemaforo = getIntent().getStringExtra("coloreSemaforo");
        switch (coloreSemaforo) {
            case "red":
                semaforo.setImageDrawable(this.getResources().getDrawable(R.drawable.semaforo_rosso));
                break;
            case "orange":
                semaforo.setImageDrawable(this.getResources().getDrawable(R.drawable.semaforo_arancio));
                break;
            case "green":
                semaforo.setImageDrawable(this.getResources().getDrawable(R.drawable.semaforo_verde));
                break;
        }

        TextView messaggio = (TextView) findViewById(R.id.text_messaggio);
        messaggio.setText(manutenzione.getMessaggio());
        Button bottoneChiama = (Button) findViewById(R.id.button_meccanico);
        bottoneChiama.setVisibility(View.INVISIBLE);

        if (tipoManutenzione.equals("Cambio gomme")) {
            bottoneChiama.setText("CHIAMA IL GOMMISTA");
            bottoneChiama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Sto chiamando il gommista", Toast.LENGTH_SHORT).show();
                    String numG = "tel:" + garage.getNumGommista();
                    //commento le prossime 2 righe per il momento che mi partono chiamate quando testo il bottone
                    //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(numG));
                    //startActivity(intent);
                }
            });
        } else {
            bottoneChiama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Sto chiamando il meccanico", Toast.LENGTH_SHORT).show();
                    String numM = "tel:" + garage.getNumMeccanico();
                    //commento le prossime 2 righe per il momento che mi partono chiamate quando testo il bottone
                    //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(numM));
                    //startActivity(intent);
                }
            });
        }

        if (coloreSemaforo.equals("red") || coloreSemaforo.equals("orange")) {
            bottoneChiama.setVisibility(View.VISIBLE);
        }

        //mostro sempre bottoneEseguita perché l'utente potrebbe eseguire la manutenzione anche prima della scadenza

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(getApplicationContext(), MyCarActivity.class);
                intent.putExtra(GarageActivity.EXTRA_MESSAGE, garage.getAuto().getTarga());
                intent.putExtra("sezione", 1);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void kmAggiornati() {

        if (new String("ok").equals(output)) {
            Toast.makeText(this, "Manutenzione segnata come eseguita!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MyCarActivity.class);
            intent.putExtra(GarageActivity.EXTRA_MESSAGE, garage.getAuto().getTarga());
            startActivity(intent);

        } else {
            Toast.makeText(this, "Errore", Toast.LENGTH_LONG).show();
        }
    }

    public void aggiornaKm(View view) {
        Log.d("dettaglioCarburante", "premuto aggiorna soglia");

        String targa = garage.getAuto().getTarga();
        String km = "" + (garage.getAuto().getKm());
        String tipo = manutenzione.getTipo();

        url = "http://checkengine.matta.ga/kmmanutenzione.php?targa=" + targa + "&km=" + km + "&tipo=" + tipo;
        url = url.replace(" ", "%20");

        new impostaKmTread(this).execute();

    }

    private class impostaKmTread extends AsyncTask<Void, Void, Void> {

        DettaglioManutenzioneActivity parent;

        public impostaKmTread(DettaglioManutenzioneActivity parent) {
            this.parent = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            pDialog = new ProgressDialog(DettaglioManutenzioneActivity.this);
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
                Log.d("dettaglioCarburante url", ">" + url + "<");
                output = sh.makeServiceCall(url, ServiceHandler.GET).toString();

                Log.d("dettaglioCarburante output", ">" + output + "<");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (pDialog.isShowing())
                pDialog.dismiss();

            parent.kmAggiornati();
        }
    }

}
