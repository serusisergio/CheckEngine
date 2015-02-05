package it.unica.checkengine;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class DettaglioTributiActivity extends ActionBarActivity {
    public static final String ARG_GARAGE = "garage";
    protected int mYear;
    protected int mMonth;
    protected int mDay;
    String tipoTributo;
    protected DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear + 1;
                    mDay = dayOfMonth;
                    url = "http://checkengine.matta.ga/datatributo.php?nome=" + tipoTributo + "&targa=" + auto.getTarga() + "&data=" + mYear + "-" + mMonth + "-" + mDay;
                    url = url.replace(" ", "%20");
                    Log.d("DettaglioTributiAct", url);
                    new aggiornaDataThread(me).execute();
                }
            };
    Auto auto;
    private ProgressDialog pDialog;
    private String output, url;
    private DettaglioTributiActivity me;
    private Garage garage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_tributi);

        me = this;

        garage = (Garage) getIntent().getSerializableExtra(ARG_GARAGE);
        auto = garage.getAuto();
        Tributo tributo = (Tributo) getIntent().getSerializableExtra("dettagliTributi");
        tipoTributo = tributo.getTipo();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //imposto il titolo della actionbar come da prototipo
        setTitle(auto.getModello() + " - " + auto.getNome());


        TextView sottotitolo = (TextView) findViewById(R.id.text_sottotitolo);
        sottotitolo.setText(tipoTributo);

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
        messaggio.setText(tributo.getMessaggio());


        Button bottone_paga = (Button) findViewById(R.id.button_paga);
        bottone_paga.setText("INSERISCI DATA RINNOVO");
        bottone_paga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


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

    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
    }

    public void dataAggiornata() {

        if (new String("ok").equals(output)) {
            Toast.makeText(getApplicationContext(), "La nuova data inserita è " + mDay + "/" + mMonth + "/" + mYear + ".", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MyCarActivity.class);
            intent.putExtra(GarageActivity.EXTRA_MESSAGE, garage.getAuto().getTarga());
            startActivity(intent);

        } else {
            Toast.makeText(this, "Errore", Toast.LENGTH_LONG).show();
        }
    }

    private class aggiornaDataThread extends AsyncTask<Void, Void, Void> {

        DettaglioTributiActivity parent;

        public aggiornaDataThread(DettaglioTributiActivity parent) {
            this.parent = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            pDialog = new ProgressDialog(DettaglioTributiActivity.this);
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
                output = sh.makeServiceCall(url, ServiceHandler.GET).toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (pDialog.isShowing())
                pDialog.dismiss();

            parent.dataAggiornata();
        }
    }

}


