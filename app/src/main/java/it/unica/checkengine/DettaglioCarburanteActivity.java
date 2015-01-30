package it.unica.checkengine;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class DettaglioCarburanteActivity extends ActionBarActivity {

    public static final String ARG_GARAGE = "garage";
    //provvisoio fino a che non impostiamo la scrittura sul DB
    public static final int SOGLIA_AVVISO_MAX = 200;
    public static final int DIMENSIONE_SERBATOIO = 40;
    public int valoreSogliaUtente;
    private Garage garage;
    private SeekBar seekbarSoglia;
    private TextView textSogliaPreavviso;

    // Aggiornamento Soglia
    private ProgressDialog pDialog;
    private String output, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_carburante);

        // Showing progress dialog
        pDialog = new ProgressDialog(DettaglioCarburanteActivity.this);
        pDialog.setMessage("Attendi...");
        pDialog.setCancelable(false);
        pDialog.show();

        TextView testoCarburanteResiduo = (TextView) findViewById(R.id.text_carburante_residuo);
        TextView testoRifornimentoPrevisto = (TextView) findViewById(R.id.text_rifornimento_previsto);
        TextView testoKmRimanenti = (TextView) findViewById(R.id.text_km_rimamenti);
        TextView testoSogliaPreavviso = (TextView) findViewById(R.id.text_preavviso);
        textSogliaPreavviso = (EditText) findViewById(R.id.edittext_preavviso);
        seekbarSoglia = (SeekBar) findViewById(R.id.seekBar_preavviso);
        garage = (Garage) getIntent().getSerializableExtra(ARG_GARAGE);
        Auto auto = garage.getAuto();
        Log.d("DettaglioCarburante", auto.getNome());

        // Abilito il pulsante indietro
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //imposto il titolo della actionbar come da prototipo
        setTitle(auto.getModello() + " - " + auto.getNome());

        int giorniRimanenti = (int) (auto.getCarburante() / auto.getConsumoMedio() * 100) / auto.getKmGiornalieri();
        int kmRimanenti = (int) (auto.getCarburante() / auto.getConsumoMedio() * 100);

        //a seconda della quantità di carburante imposto il colore del semaforo e della progress bar
        ImageView semaforo = (ImageView)findViewById(R.id.semaforo);
        ProgressBar progBarCarburante = (ProgressBar)findViewById(R.id.progressBar_carburante);
        progBarCarburante.setMax(DIMENSIONE_SERBATOIO);
        progBarCarburante.setProgress((int)auto.getCarburante());

        if(auto.isCarburanteRed()){
            semaforo.setImageDrawable(this.getResources().getDrawable(R.drawable.semaforo_rosso));
            progBarCarburante.getProgressDrawable().setColorFilter(Color.RED,android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        else if(auto.isCarburanteOrange()){
            semaforo.setImageDrawable(this.getResources().getDrawable(R.drawable.semaforo_arancio));
            progBarCarburante.getProgressDrawable().setColorFilter(Color.YELLOW,android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        else{
            semaforo.setImageDrawable(this.getResources().getDrawable(R.drawable.semaforo_verde));
            progBarCarburante.getProgressDrawable().setColorFilter(Color.GREEN,android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        testoCarburanteResiduo.setText("Residuo: " + auto.getCarburante() + " litri");

        if (giorniRimanenti == 1) {
            testoRifornimentoPrevisto.setText("Rifornimento previsto tra: 1 giorno");
        } else {
            testoRifornimentoPrevisto.setText("Rifornimento previsto tra: " + giorniRimanenti + " giorni");
        }
        testoKmRimanenti.setText("Rifornimento previsto tra: " + kmRimanenti + " Km");
        testoSogliaPreavviso.setText("Soglia preavviso: ");

        //impostazioni seekbar e editText
        seekbarSoglia.setMax(SOGLIA_AVVISO_MAX);
        seekbarSoglia.incrementProgressBy(1);
        textSogliaPreavviso.setText(String.valueOf(valoreSogliaUtente));

        //La seekbar rispecchia il valore della soglia del settext "sogliaPreavviso"
        valoreSogliaUtente = Integer.parseInt(textSogliaPreavviso.getText().toString());
        seekbarSoglia.setProgress(valoreSogliaUtente);

        // Recuperiamo dal server il valore reale
        new recuperaSogliaThread(this).execute();

        seekbarSoglia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSogliaPreavviso.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        textSogliaPreavviso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valoreSogliaUtente = Integer.parseInt(textSogliaPreavviso.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                seekbarSoglia.setProgress(valoreSogliaUtente);
            }
        });

    }

    public void sogliaAggiornata() {

        if (new String("ok").equals(output)) {
            Toast.makeText(this, "Soglia Aggiornata!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MyCarActivity.class);
            intent.putExtra(GarageActivity.EXTRA_MESSAGE, garage.getAuto().getTarga());
            startActivity(intent);

        } else {
            Toast.makeText(this, "Errore", Toast.LENGTH_LONG).show();
        }
    }

    public void aggiornaSoglia(View view) {
        Log.d("dettaglioCarburante", "premuto aggiorna soglia");

        EditText eSoglia = (EditText) findViewById(R.id.edittext_preavviso);
        String soglia = eSoglia.getText().toString();
        String targa = garage.getAuto().getTarga();

        url = "http://checkengine.matta.ga/soglia.php?action=aggiorna&targa=" + targa + "&soglia=" + soglia;
        url = url.replace(" ", "%20");

        new aggiornaSogliaThread(this).execute();

    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(getApplicationContext(), MyCarActivity.class);
                intent.putExtra(GarageActivity.EXTRA_MESSAGE, garage.getAuto().getTarga());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class aggiornaSogliaThread extends AsyncTask<Void, Void, Void>{

        DettaglioCarburanteActivity parent;

        public aggiornaSogliaThread(DettaglioCarburanteActivity parent){
            this.parent = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            pDialog = new ProgressDialog(DettaglioCarburanteActivity.this);
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
                Log.d("dettaglioCarburante url", ">"+url+"<");
                output =sh.makeServiceCall(url, ServiceHandler.GET).toString();

                Log.d("dettaglioCarburante output", ">"+output+"<");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (pDialog.isShowing())
                pDialog.dismiss();

            parent.sogliaAggiornata();
        }
    }

    private class recuperaSogliaThread extends AsyncTask<Void, Void, Void>{

        DettaglioCarburanteActivity parent;

        public recuperaSogliaThread(DettaglioCarburanteActivity parent){
            this.parent = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                // Creating service handler class instance
                ServiceHandler sh = new ServiceHandler();

                // Making a request to url and getting response
                url = "http://checkengine.matta.ga/soglia.php?action=recupera&targa="+garage.getAuto().getTarga();

                String valll = sh.makeServiceCall(url, ServiceHandler.GET).toString();
                Log.d("dettaglioCarburante val recuperato", valll);
                valoreSogliaUtente = Integer.parseInt(valll);
                seekbarSoglia.setProgress(valoreSogliaUtente);
                Log.d("dettaglioCarburante val s ut", url);
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
    }



}
