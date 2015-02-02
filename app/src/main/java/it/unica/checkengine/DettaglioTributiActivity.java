package it.unica.checkengine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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



        Button bottone_paga = (Button) findViewById(R.id.button_paga);
        bottone_paga.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlert(v);
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

    private void openAlert(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DettaglioTributiActivity.this);

        alertDialogBuilder.setTitle(this.getTitle()+ " decision");

        alertDialogBuilder.setMessage("Are you sure?");

        // set positive button: Yes message

        alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // go to a new activity of the app
                Intent positveActivity = new Intent(getApplicationContext(),
                        PagaTributoAlertDialogActivity.class);
                startActivity(positveActivity);
            }
        });
        // set negative button: No message

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // cancel the alert box and put a Toast to the user
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "You chose a negative answer",
                        Toast.LENGTH_LONG).show();
            }
        });
        // set neutral button: Exit the app message

        alertDialogBuilder.setNeutralButton("Exit the app",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int id) {

                // exit the app and go to the HOME

                DettaglioTributiActivity.this.finish();

            }

        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}


