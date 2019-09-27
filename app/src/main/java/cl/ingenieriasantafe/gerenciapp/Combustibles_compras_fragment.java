package cl.ingenieriasantafe.gerenciapp;

import android.animation.ValueAnimator;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.text.DecimalFormatSymbols;


public class Combustibles_compras_fragment extends Fragment {



    public static final String apitaes = "http://santafeinversiones.org/api/taes";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_combustibles_compras,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView fechasrango = (TextView)getView().findViewById(R.id.txtfechas);
        SharedPreferences preferences1 = getContext().getSharedPreferences("tae", Context.MODE_PRIVATE);
        String fecha_ultima_tae = preferences1.getString("fecha", "Sin compras");

        fechasrango.setText("Ultima tae: "+fecha_ultima_tae);

        CardView vermas = (CardView)getView().findViewById(R.id.precios_Cardview);

        vermas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),Precios_copec_Activity.class);
                startActivity(i);
            }
        });

        getInfoTaes();
    }

    private void getInfoTaes(){
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, apitaes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    final TextView txtlitros = getView().findViewById(R.id.txtlts_compras);
                    final TextView totalcompras = getView().findViewById(R.id.txttotal_compras);
                    String json;
                    json = response.toString();
                    Log.i("TAG",response);
                    JSONArray jsonArray = null;
                    jsonArray = new JSONArray(json);
                    int total_lts_comprados = 0;
                    int costototal_taes = 0;

                    int count = jsonArray.length();

                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int ltstae = Integer.parseInt(jsonObject.getString("litrost"));
                        int ctotal = Integer.parseInt(jsonObject.getString("valort"));
                        total_lts_comprados = total_lts_comprados+ltstae;
                        costototal_taes = costototal_taes+ctotal;

                        if (i == count-1){
                            String fecha = jsonObject.getString("fechat");
                            SharedPreferences preferences = getContext().getSharedPreferences("tae", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("fecha",fecha);
                            editor.commit();
                        }

                    }

                    String pattern ="###,###,###.##";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    final String output = decimalFormat.format(total_lts_comprados);
                    final String output2 = decimalFormat.format(costototal_taes);
                    txtlitros.setText(output);
                    totalcompras.setText(output2);

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
