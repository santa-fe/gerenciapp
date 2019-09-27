package cl.ingenieriasantafe.gerenciapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Precios_copec_Activity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.precios_copec_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences preferences1 = getSharedPreferences("precios_petroleo", Context.MODE_PRIVATE);
        String loncomilla = preferences1.getString("loncomilla", "No disponible");
        String molina = preferences1.getString("molina", "No disponible");
        String poblacion = preferences1.getString("poblacion", "No disponible");
        String viñales = preferences1.getString("viñales", "No disponible");

        TextView txtloncomilla = (TextView) findViewById(R.id.txtloncomilla);
        TextView txtmolina = (TextView) findViewById(R.id.txtmolina);
        TextView txtpoblacion = (TextView) findViewById(R.id.txtpoblacion);
        TextView txtviñales = (TextView) findViewById(R.id.txtviñales);

        txtloncomilla.setText("Loncomilla: $ "+loncomilla);
        txtmolina.setText("Molina: $ "+molina);
        txtpoblacion.setText("Población: $ "+poblacion);
        txtviñales.setText("Viñales: $ "+viñales);



    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
