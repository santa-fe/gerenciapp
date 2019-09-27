package cl.ingenieriasantafe.gerenciapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


public class MenuDrawer_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String apidieselseptima = "https://api.cne.cl/v3/combustibles/vehicular/estaciones?token=LTQUNwlIIu&region=07&tipo_combustible=petroleo%20diesel&distribuidor=Copec";
    public static final String apidieselsexta = "https://api.cne.cl/v3/combustibles/vehicular/estaciones?token=LTQUNwlIIu&region=06&tipo_combustible=petroleo%20diesel&distribuidor=Copec";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment = new Dashboard_fragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame,fragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        new Thread(new Runnable() {
            @Override
            public void run() {

                GetInfoPrecioPetroleoSeptima();
                GetInfoPrecioPetroleoSexta();
            }
        }).start();




    }


    private void GetInfoPrecioPetroleoSeptima(){

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, apidieselseptima, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.i("TAAAG",jsonArray.toString());

                    String precio_loncomilla="";
                    String precio_molina="";
                    String precio_viñales="";

                    for (int i=0; i<jsonArray.length();i++){

                        JSONObject  jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");

                        if (id.toString().equals("co710202")){
                            precio_viñales = jsonObject1.getJSONObject("precios").getString("petroleo diesel");
                        }
                        if (id.toString().equals("co730402 ")){
                            precio_molina = jsonObject1.getJSONObject("precios").getString("petroleo diesel");
                        }
                        if (id.toString().equals("co740603")){
                            precio_loncomilla = jsonObject1.getJSONObject("precios").getString("petroleo diesel");
                        }

                    }


                    SharedPreferences preferences = getSharedPreferences("precios_petroleo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("loncomilla",precio_loncomilla);
                    editor.putString("molina",precio_molina);
                    editor.putString("viñales",precio_viñales);
                    editor.commit();



                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
        mRequestQueue.add(mStringRequest);


    }

    private void GetInfoPrecioPetroleoSexta(){

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, apidieselsexta, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.i("TAAAG",jsonArray.toString());

                    String precio_poblacion="";

                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject  jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        if (id.toString().equals("co630701")){
                            precio_poblacion = jsonObject1.getJSONObject("precios").getString("petroleo diesel");

                        }
                    }

                    SharedPreferences preferences = getSharedPreferences("precios_petroleo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("poblacion",precio_poblacion);
                    editor.commit();



                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
        mRequestQueue.add(mStringRequest);


    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer_, menu);

        SharedPreferences preferences1 = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String nombre_usuario = preferences1.getString("nombre", "");

        TextView nusuario = (TextView)findViewById(R.id.txtnombreusuario_dd);
        nusuario.setText(nombre_usuario);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent intent = new Intent(MenuDrawer_Activity.this,Login_Activity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment fragment = null;
        boolean fragmentselect = false;

        if (id == R.id.nav_petroleo_pedidos){
            fragment = new Combustibles_pedidos_fragment();
            fragmentselect = true;
        }if (id == R.id.nav_petroleo_compras){
            fragment = new Combustibles_compras_fragment();
            fragmentselect = true;
        }if (id ==R.id.nav_petroleo_consumo){
            fragment = new Combustibles_consumo_fragment();
            fragmentselect = true;
        }

        if (fragmentselect == true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
