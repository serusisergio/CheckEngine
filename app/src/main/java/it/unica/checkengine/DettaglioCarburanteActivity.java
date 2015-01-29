package it.unica.checkengine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
    public static final int KM_GIORNALIERI = 50;
    public static final int SOGLIA_PREAVVISO = 100;
    public int valoreSogliaImpostato;

    private Garage garage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_carburante);


        garage = (Garage) getIntent().getSerializableExtra(ARG_GARAGE);
        Auto auto = garage.getAuto();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //imposto il titolo della actionbar come da prototipo
        setTitle(auto.getModello() + " - " + auto.getNome());

        TextView testoCarburanteResiduo = (TextView)findViewById(R.id.text_carburante_residuo);
        TextView testoRifornimentoPrevisto = (TextView)findViewById(R.id.text_rifornimento_previsto);
        TextView testoKmRimanenti = (TextView)findViewById(R.id.text_km_rimamenti);
        TextView testoSogliaPreavviso = (TextView)findViewById(R.id.text_preavviso);
        EditText sogliaPreavviso = (EditText)findViewById(R.id.edittext_preavviso);
        SeekBar seekbarSoglia = (SeekBar)findViewById(R.id.seekBar_preavviso);

        //a seconda della quantità di carburante imposto il colore del semaforo e della progress bar
        ImageView semaforo = (ImageView)findViewById(R.id.semaforo);
        ProgressBar progBarCarburante = (ProgressBar)findViewById(R.id.progressBar_carburante);

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

        //imposto le informazioni relative alle statistiche del carburante. Al momento la soglia preavviso è impostata manualmente
        testoCarburanteResiduo.setText("Residuo: " + auto.getCarburante() + " litri");
        testoRifornimentoPrevisto.setText("Rifornimento previsto tra: " + (int)(auto.getCarburante()/KM_GIORNALIERI) + " giorni");
        testoKmRimanenti.setText("Km rimanenti stimati: " + auto.getCarburante()*auto.getConsumoMedio() + " Km");
        testoSogliaPreavviso.setText("Soglia preavviso: ");
        sogliaPreavviso.setText(String.valueOf(SOGLIA_PREAVVISO));

        //La seekbar rispecchia il valore della soglia del settext "sogliaPreavviso"
        //TO DO: quando si agigorna il set text si sposta anche la barra e vice versa
        valoreSogliaImpostato = Integer.parseInt(sogliaPreavviso.getText().toString());
        seekbarSoglia.setProgress(valoreSogliaImpostato);


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

}
