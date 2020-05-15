package cl.ingenieriasantafe.gerenciapp;

import android.animation.ValueAnimator;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Base64;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class Combustibles_compras_fragment extends Fragment {



    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    public static final String url = "http://santafeinversiones.org/api/taes/copec";

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


        fechasrango.setText("Sincronizada con Copec");

        CardView vermas = (CardView)getView().findViewById(R.id.precios_Cardview);

        vermas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),Precios_copec_Activity.class);
                startActivity(i);
            }
        });

        new InfoTaes().execute();

    }


    private class InfoTaes extends AsyncTask<Void,Void,Void>{

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Cargando información");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        final TextView txtlitros = getView().findViewById(R.id.txtlts_compras);
                        final TextView totalcompras = getView().findViewById(R.id.txttotal_compras);
                        String json;
                        json = response.toString();
                        JSONArray jsonArray = null;
                        jsonArray = new JSONArray(json);

                        int ltstotal=0;
                        int ctotal=0;

                        for (int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String ltsingenieria = jsonObject.getString("litros_ingenieria").replace(".","");
                            String ltstransportes = jsonObject.getString("litros_transportes").replace(".","");
                            String totalingenieria = jsonObject.getString("total_ingenieria");
                            String totaltransportes = jsonObject.getString("total_transportes");

                            ltstotal = (Integer.parseInt(ltsingenieria.substring(0,ltsingenieria.length()-3))+Integer.parseInt(ltstransportes.substring(0,ltstransportes.length()-3)));
                            ctotal = (Integer.parseInt(totalingenieria.replace(".",""))+Integer.parseInt(totaltransportes.replace(".","")));
                        }

                        String pattern ="###,###,###.##";
                        DecimalFormat decimalFormat = new DecimalFormat(pattern);
                        final String totalgastado = decimalFormat.format(ctotal);
                        final String totallitros = decimalFormat.format(ltstotal);
                        txtlitros.setText(totallitros);
                        totalcompras.setText("$ "+totalgastado);

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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                Thread.sleep(2000);

                mProgressDialog.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


}
