package cl.ingenieriasantafe.gerenciapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class Asfaltos_stock_fragment extends Fragment {

    public static final String apiasfaltosstock = "http://santafeinversiones.org/api/asfalto/stock";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;


    public Asfaltos_stock_fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asfaltos_stock_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getInfoStock();
    }

    private void getInfoStock(){
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, apiasfaltosstock, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    //RECURSOS
                    final TextView txtpolvoroca = getView().findViewById(R.id.txtpolvoroca);
                    final TextView txtgravilla12 = getView().findViewById(R.id.txtgravilla12);
                    final TextView txtgravilla34 = getView().findViewById(R.id.txtgravilla34);
                    final TextView txtc24 = getView().findViewById(R.id.txtc24);
                    final TextView txtcomb = getView().findViewById(R.id.txtcomb);

                    String json;
                    json = response.toString();
                    JSONObject jsonObject = null;
                    jsonObject = new JSONObject(json);
                    JSONObject recursos = jsonObject.getJSONObject("recursos");

                    double tpr=0;
                    double tg12=0;
                    double tg34=0;
                    double tc24=0;
                    double tcomb=0;

                    for (int i=0; i<recursos.length(); i++){
                        tpr= Double.parseDouble(recursos.getString("total_polvoroca"));
                        tg12= Double.parseDouble(recursos.getString("total_gravilla12"));
                        tg34= Double.parseDouble(recursos.getString("total_gravilla34"));
                        tc24= Double.parseDouble(recursos.getString("total_ca24"));
                        tcomb = Double.parseDouble(recursos.getString("total_petroleo"));


                    }

                    String pattern ="###,###,###.##";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);

                    final String totalpr = decimalFormat.format(tpr);
                    final String totalg12 = decimalFormat.format(tg12);
                    final String totalg34 = decimalFormat.format(tg34);
                    final String totalc24 = decimalFormat.format(tc24/1000);
                    final String totalcomb = decimalFormat.format(tcomb);
                    txtpolvoroca.setText("Polvo Roca: "+totalpr+" m3");
                    txtgravilla12.setText("Gravilla 1/2: "+totalg12+" m3");
                    txtgravilla34.setText("Gravilla 3/4: "+totalg34+" m3");
                    txtc24.setText("CA24: "+totalc24+" m3");
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
