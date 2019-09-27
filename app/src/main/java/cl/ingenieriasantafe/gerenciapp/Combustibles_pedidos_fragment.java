package cl.ingenieriasantafe.gerenciapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Combustibles_pedidos_fragment extends Fragment {


    public static final String apisolicitados = "http://santafeinversiones.com/services/listadogeneral";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    String fecha = dateFormat.format(date);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_combustibles_pedidos,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPedidosInfo();

        TextView fechamostrar = (TextView)getView().findViewById(R.id.txtfechas);

        fechamostrar.setText("Dia en curso: "+fecha);

        CardView cardasignados = getView().findViewById(R.id.asignados_combustibles);

        cardasignados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Combustibles_detalle_asignados_Activity.class);
                startActivity(intent);
            }
        });


    }

    private void getPedidosInfo(){
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, apisolicitados + "/" + fecha + "/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    TextView txtsolicitados = getView().findViewById(R.id.txtsolicitadoshoy);
                    TextView txtasignados = getView().findViewById(R.id.txtasignadoshoy);


                    String json;
                    json = response.toString();

                    Log.i("TAG",response);

                    JSONArray jsonArray = null;
                    jsonArray = new JSONArray(json);
                    int totalsolicitados = 0;
                    int totalasignados = 0;

                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int totalsol = Integer.parseInt(jsonObject.getString("litros"));
                        String lasig = jsonObject.getString("lasignados");
                        int totalasig=0;
                        if (lasig.equals("")){
                            totalasig = 0;
                        }else{
                            totalasig = Integer.parseInt(jsonObject.getString("lasignados"));
                        }
                        totalsolicitados = totalsolicitados+totalsol;
                        totalasignados = totalasignados+totalasig;
                    }

                    String pattern ="###,###,###.##";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    final String solicitados = decimalFormat.format(totalsolicitados);
                    final String asignados = decimalFormat.format(totalasignados);
                    txtsolicitados.setText(solicitados);
                    txtasignados.setText(asignados);
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
