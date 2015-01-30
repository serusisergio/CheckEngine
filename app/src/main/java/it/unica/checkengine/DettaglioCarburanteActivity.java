package it.unica.checkengine;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
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

public class DettaglioCarburanteActivity extends ActionBarActivity {

    public static final String ARG_GARAGE = "garage";
    //provvisoio fino a che non impostiamo la scrittura sul DB
    public static final int SOGLIA_AVVISO_MAX = 200;
    public static final int DIMENSIONE_SERBATOIO = 40;
    public int valoreSogliaUtente = 100;
    private Garage garage;
    private SeekBar seekbarSoglia;
    private TextView textSogliaPreavviso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_carburante);

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


        //TO DO: bottone salva quando sarà possibile inviare i dati al DB nel caso venisse cambiata la soglia
        Button indietro = (Button) findViewById(R.id.buttonBack);
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("dettaglioCarburante", "premuto back");
                Intent intent = new Intent(getApplicationContext(), MyCarActivity.class);
                intent.putExtra(GarageActivity.EXTRA_MESSAGE, garage.getAuto().getTarga());
                startActivity(intent);
            }
        });



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





}
