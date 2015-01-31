package it.unica.checkengine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
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
        Manutenzione manutenzione = (Manutenzione)getIntent().getSerializableExtra("dettagliManutenzione");
        String tipoManutenzione = manutenzione.getTipo();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //imposto il titolo della actionbar come da prototipo
        setTitle(auto.getModello() + " - " + auto.getNome());


        TextView sottotitolo = (TextView) findViewById(R.id.text_sottotitolo);
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
        final Button bottoneChiama = (Button) findViewById(R.id.button_meccanico);

        switch (tipoManutenzione) {
            case "Gomme":
                bottoneChiama.setText("Chiama il gommista");
                bottoneChiama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Sto chiamando il gommista", Toast.LENGTH_SHORT).show();
                    String numM = "tel:" + garage.getNumGommista();
                    //commento le prossime 2 righe per il momento che mi partono chiamate quando testo il bottone
                    //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(numM));
                    //startActivity(intent);
                }
            });
                break;
            case "Tagliando":
            {
                bottoneChiama.setVisibility(View.INVISIBLE);
                break;
            }
            default:
                bottoneChiama.setText("Chiama il meccanico");
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
