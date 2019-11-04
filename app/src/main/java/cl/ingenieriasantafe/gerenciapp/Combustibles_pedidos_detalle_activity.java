package cl.ingenieriasantafe.gerenciapp;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Combustibles_pedidos_detalle_activity extends AppCompatActivity {

    public static final String apisolicitados = "http://santafeinversiones.com/services/listadogeneral";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    String fecha = dateFormat.format(date);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combustible_pedidos_detalle_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView fechaencurso = (TextView)findViewById(R.id.txtfechas);
        fechaencurso.setText("Día en curso: "+fecha);


        getPedidosInfo();


    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }



    private void getPedidosInfo(){
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, apisolicitados + "/" + fecha + "/",  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    String json;
                    json = response.toString();
                    JSONArray jsonArray = null;
                    jsonArray = new JSONArray(json);
                    ArrayList<String>unegocios = new ArrayList<>();
                    ArrayList<String>unegocios2 = new ArrayList<>();

                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String une = jsonObject.getString("unegocio");
                        if (!unegocios.contains(une)){
                            unegocios.add(une);
                        }
                    }

                    for (int j = 0; j<unegocios.size();j++){
                        String unegocio =unegocios.get(j);
                        int totalasignados = 0;
                        for (int x=0; x<jsonArray.length(); x++){
                            JSONObject jsonObject = jsonArray.getJSONObject(x);
                            String unego = jsonObject.getString("unegocio");
                            if (unegocio.equalsIgnoreCase(unego)){
                                String lasig = jsonObject.getString("lasignados");
                                int totalasig=0;
                                if (lasig.equals("")){
                                    totalasig = 0;
                                }else{
                                    totalasig = Integer.parseInt(jsonObject.getString("lasignados"));
                                }

                                totalasignados = totalasignados+totalasig;
                            }


                        }

                        String pattern ="###,###,###.##";
                        DecimalFormat decimalFormat = new DecimalFormat(pattern);
                        final String asignados = decimalFormat.format(totalasignados);

                        String msj= unegocio+" : <font color=#000000>"+asignados+" Lts </font>";

                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);
                        TextView txtunegocios = new TextView(Combustibles_pedidos_detalle_activity.this);
                        txtunegocios.setPadding(10,0,0,0);
                        txtunegocios.setTextSize(16);
                        txtunegocios.setTypeface(null, Typeface.BOLD);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txtunegocios.setText(Html.fromHtml(msj,Html.FROM_HTML_MODE_LEGACY));
                        }
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
