package it.unica.checkengine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DettaglioManutenzioneActivity extends ActionBarActivity {
    public static final String ARG_GARAGE = "garage";

    private Garage garage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_manutenzione);


        garage = (Garage) getIntent().getSerializableExtra(ARG_GARAGE);
        Auto auto = garage.getAuto();
        Manutenzione manutenzione = new Manutenzione();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //imposto il titolo della actionbar come da prototipo
        setTitle(auto.getModello() + " - " + auto.getNome());

        TextView sottotitolo = (TextView) findViewById(R.id.text_sottotitolo);
        String tipoManutenzione = getIntent().getStringExtra("nomeManutenzione");
        sottotitolo.setText(tipoManutenzione);

        ImageView semaforo = (ImageView) findViewById(R.id.semaforo);
        String coloreSemaforo = getIntent().getStringExtra("coloreSemaforo");
        switch(coloreSemaforo){
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


        Button indietro = (Button) findViewById(R.id.button_indietro);
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyCarActivity.class);
                intent.putExtra(GarageActivity.EXTRA_MESSAGE, garage.getAuto().getTarga());
                startActivity(intent);
            }
        });

        Button meccanico = (Button) findViewById(R.id.button_meccanico);
        meccanico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sto chiamando il meccanico", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
