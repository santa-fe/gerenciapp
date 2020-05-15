package cl.ingenieriasantafe.gerenciapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {

    Button login;

    EditText usuario;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        login = (Button)findViewById(R.id.Btnlogin);
        getSupportActionBar().hide();
        usuario = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(Login_Activity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                }

                if (usuario.getText().toString().equals("igodoy") && password.getText().toString().equals("2233")){
                    SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("nombre","Ignacio Godoy");
                    editor.commit();
                    Intent intent = new Intent(Login_Activity.this,MenuDrawer_Activity.class);
                    startActivity(intent);
                    finish();
                }
                if (usuario.getText().toString().equals("pgarrido") && password.getText().toString().equals("3069")){
                    SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("nombre","Pablo Garrido");
                    editor.commit();
                    Intent intent = new Intent(Login_Activity.this,MenuDrawerCombustibles_Activity.class);
                    startActivity(intent);
                    finish();
                }
                if (usuario.getText().toString().equalsIgnoreCase("fgonzalez") && password.getText().toString().equals("8970")){
                    SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("nombre","Freddy Gonzalez");
                    editor.commit();
                    Intent intent = new Intent(Login_Activity.this,MenuDrawerAsfaltos_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
