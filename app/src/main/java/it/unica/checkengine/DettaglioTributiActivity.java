package it.unica.checkengine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class DettaglioTributiActivity extends ActionBarActivity {
    public static final String ARG_GARAGE = "garage";
    private Garage garage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_tributi);

        garage = (Garage) getIntent().getSerializableExtra(ARG_GARAGE);
        Auto auto = garage.getAuto();
        Tributo tributo = (Tributo)getIntent().getSerializableExtra("dettagliTributi");
        String tipoTributo = tributo.getTipo();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //imposto il titolo della actionbar come da prototipo
        setTitle(auto.getModello() + " - " + auto.getNome());


        TextView sottotitolo = (TextView) findViewById(R.id.text_sottotitolo);
        sottotitolo.setText(tipoTributo);

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
        messaggio.setText(tributo.getMessaggio());


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
