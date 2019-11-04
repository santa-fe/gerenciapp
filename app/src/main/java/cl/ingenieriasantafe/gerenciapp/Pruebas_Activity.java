package cl.ingenieriasantafe.gerenciapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
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
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Pruebas_Activity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    public static final String apiconsumos = "http://santafeinversiones.com/services/solicitudes/";

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    Date date = new Date();
    String mesencurso = dateFormat.format(date);

    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date2 = new Date();
    String fechaactual = dateFormat2.format(date2);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.pruebas_activity);

        getInfoConsumos();

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }


    private void getInfoConsumos(){
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, apiconsumos + mesencurso + "-01" + "/" + fechaactual, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    String json;
                    json = response.toString();
                    JSONArray jsonArray = null;
                    jsonArray = new JSONArray(json);

                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String unegocio = jsonObject.getString("unegocio");
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);
                        TextView txtunegocios = new TextView(Pruebas_Activity.this);
                        txtunegocios.setText(unegocio);
                        txtunegocios.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        linearLayout.addView(txtunegocios);


                    }

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

}
