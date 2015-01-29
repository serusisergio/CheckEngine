package it.unica.checkengine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DettaglioCarburanteActivity extends ActionBarActivity {

    public static final String ARG_GARAGE = "garage";

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



        TextView testoCarburanteResiduo = (TextView)findViewById(R.id.text_carburante_residuo);
        TextView testoRifornimentoPrevisto = (TextView)findViewById(R.id.text_rifornimento_previsto);
        TextView testoKmRimanenti = (TextView)findViewById(R.id.text_km_rimamenti);
        TextView testoSogliaPreavviso = (TextView)findViewById(R.id.text_preavviso);
        testoCarburanteResiduo.setText("Carburante residuo: " + auto.getCarburante() + "litri");
        testoRifornimentoPrevisto.setText("Rifornimento previsto tra: " + auto.getCarburante() + "giorni");
        testoKmRimanenti.setText("Rifornimento previsto tra: " + auto.getCarburante() + "Km");
        testoSogliaPreavviso.setText("Soglia preavviso:          Km");




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
