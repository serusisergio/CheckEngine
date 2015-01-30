package it.unica.checkengine;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;


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

    }

}
