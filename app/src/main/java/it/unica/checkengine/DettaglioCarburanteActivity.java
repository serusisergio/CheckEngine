package it.unica.checkengine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class DettaglioCarburanteActivity extends ActionBarActivity {

    public static final String ARG_GARAGE = "garage";
    public static final int DIMENSIONE_SERBATOIO = 40;


    private Garage garage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_carburante);

        garage = (Garage) getIntent().getSerializableExtra(ARG_GARAGE);
        Auto auto = garage.getAuto();
        Log.d("DettaglioCarburante", auto.getNome());
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        setTitle(auto.getModello() + " - " + auto.getNome());

        int giorniRimanenti = (int) (auto.getCarburante() / auto.getConsumoMedio() * 100) / auto.getKmGiornalieri();
        int kmRimanenti = (int) (auto.getCarburante() / auto.getConsumoMedio() * 100);

        ImageView semaforo = (ImageView)findViewById(R.id.semaforo);
        ProgressBar progBarCarburante = (ProgressBar)findViewById(R.id.progressBar_carburante);
        progBarCarburante.setProgress((int) (auto.getCarburante()) * 100 / DIMENSIONE_SERBATOIO);
        if(auto.isCarburanteRed()){
            semaforo.setImageDrawable(this.getResources().getDrawable(R.drawable.semaforo_rosso));
            progBarCarburante.getIndeterminateDrawable().setColorFilter(0xFF0000,android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        else if(auto.isCarburanteOrange()){
            semaforo.setImageDrawable(this.getResources().getDrawable(R.drawable.semaforo_arancio));
            progBarCarburante.getIndeterminateDrawable().setColorFilter(0xFFA500,android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        else{
            semaforo.setImageDrawable(this.getResources().getDrawable(R.drawable.semaforo_verde));
            progBarCarburante.getIndeterminateDrawable().setColorFilter(0x50E828,android.graphics.PorterDuff.Mode.MULTIPLY);
        }


        TextView testoCarburanteResiduo = (TextView)findViewById(R.id.text_carburante_residuo);
        TextView testoRifornimentoPrevisto = (TextView)findViewById(R.id.text_rifornimento_previsto);
        TextView testoKmRimanenti = (TextView)findViewById(R.id.text_km_rimamenti);
        TextView testoSogliaPreavviso = (TextView)findViewById(R.id.text_preavviso);
        testoCarburanteResiduo.setText("Residuo: " + auto.getCarburante() + " litri");

        if (giorniRimanenti == 1) {
            testoRifornimentoPrevisto.setText("Rifornimento previsto tra: 1 giorno");
        } else {
            testoRifornimentoPrevisto.setText("Rifornimento previsto tra: " + giorniRimanenti + " giorni");
        }
        testoKmRimanenti.setText("Rifornimento previsto tra: " + kmRimanenti + " Km");
        testoSogliaPreavviso.setText("Soglia preavviso: ");




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
