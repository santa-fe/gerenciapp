package cl.ingenieriasantafe.gerenciapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.text.DecimalFormat;

public class Asfaltos_entradas_fragment extends Fragment {


    public static final String apiasfaltosingresos = "http://santafeinversiones.org/api/asfalto/ingresos";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asfaltos_entradas_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getInfoIngresos();
    }

    private void getInfoIngresos(){
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, apiasfaltosingresos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    final TextView txtgravilla34= getView().findViewById(R.id.txtgravilla34);
                    final TextView txtgravilla12 =getView().findViewById(R.id.txtgravilla12);
                    final TextView txtpolvoroca = getView().findViewById(R.id.txtgravillapr);
                    final TextView txtca24 = getView().findViewById(R.id.txtca24);
                    final TextView txtcomb = getView().findViewById(R.id.txtcomb);

                    String json;
                    json = response.toString();
                    JSONArray jsonArray = null;
                    jsonArray = new JSONArray(json);
                    double gravilla34=0;
                    double gravilla12=0;
                    double pr=0;
                    double ca24=0;
                    int comb=0;

                    for (int i = 0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String recurso = jsonObject.getString("recurso");
                        double t34 =0;
                        double t12=0;
                        double tpr=0;
                        double tca24=0;
                        int tcomb=0;
                        if (recurso.equalsIgnoreCase("GRAVILLA 3/4")){
                            t34 = Double.parseDouble(jsonObject.getString("cantidad"));
                        }
                        if (recurso.equalsIgnoreCase("GRAVILLA 1/2")){
                            t12= Double.parseDouble(jsonObject.getString("cantidad"));
                        }
                        if (recurso.equalsIgnoreCase("POLVO ROCA")){
                            tpr = Double.parseDouble(jsonObject.getString("cantidad"));
                        }
                        if (recurso.equalsIgnoreCase("CA24")){
                            tca24 = Double.parseDouble(jsonObject.getString("cantidad"));
                        }
                        if (recurso.equalsIgnoreCase("PETROLEO")){
                            tcomb = Integer.parseInt(jsonObject.getString("cantidad"));
                        }
                        gravilla34= gravilla34+t34;
                        gravilla12= gravilla12+t12;
                        pr=pr+tpr;
                        ca24=ca24+tca24;
                        comb=comb+tcomb;
                    }

                    String pattern ="###,###,###.##";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    final String totalgravilla34 = decimalFormat.format(gravilla34);
                    final String totalgravilla12 = decimalFormat.format(gravilla12);
                    final String totalpr = decimalFormat.format(pr);
                    final String totalca24 = decimalFormat.format(ca24/1000);
                    final String totalcomb = decimalFormat.format(comb);
                    txtgravilla34.setText("Gravilla 3/4: "+totalgravilla34+" m3");
                    txtgravilla12.setText("Gravilla 1/2: "+totalgravilla12+" m3");
                    txtpolvoroca.setText("Polvo Roca: "+totalpr+" m3");
                    txtca24.setText("CA24: "+totalca24+" m3");
                    txtcomb.setText("Combustibles: "+totalcomb+" lts");

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
