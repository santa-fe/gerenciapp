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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MenuDrawerCombustibles_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawercombustibles_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutcombustibles);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment = new Combustibles_pedidos_fragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame,fragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewcombustibles);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutcombustibles);
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
            Intent intent = new Intent(MenuDrawerCombustibles_Activity.this,Login_Activity.class);
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

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutcombustibles);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
