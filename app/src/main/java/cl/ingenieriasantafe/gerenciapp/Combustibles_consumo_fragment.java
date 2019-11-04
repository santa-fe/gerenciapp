package cl.ingenieriasantafe.gerenciapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class Combustibles_consumo_fragment extends Fragment {


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    public static final String apiconsumos = "http://santafeinversiones.com/services/solicitudes/";

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    Date date = new Date();
    String mesencurso = dateFormat.format(date);

    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date2 = new Date();
    String fechaactual = dateFormat2.format(date2);

    SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy",Locale.getDefault());
    Date date3 = new Date();
    String anio = dateFormat3.format(date3);

    SimpleDateFormat dateFormat4 = new SimpleDateFormat("MM",Locale.getDefault());
    Date date4 = new Date();
    String mes = dateFormat4.format(date4);

    SimpleDateFormat dateFormat5 = new SimpleDateFormat("dd",Locale.getDefault());
    Date date5 = new Date();
    String dia = dateFormat5.format(date5);

    int me = (Integer.parseInt(mes))-1;
    String mesanterior = anio+"-"+"0"+String.valueOf(me)+"-"+dia;
    String mesanteriorsindia=anio+"-"+"0"+String.valueOf(me);



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_combustibles_consumo,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView rangofechas = (TextView)getView().findViewById(R.id.txtfechas);
        rangofechas.setText(mesencurso+"-01"+" / "+fechaactual);

        CardView consumidoscomb = (CardView)getView().findViewById(R.id.consumidos_combustibles);

        consumidoscomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),detalle_combustible_consumidos_unegocio_Activity.class);
                startActivity(i);
            }
        });

        getInfoConsumos();

    }

    private void getInfoConsumos(){

                final TextView ltsconsumidos = (TextView)getView().findViewById(R.id.txtlts_consumidos);
                final TextView generadorestxt = (TextView)getView().findViewById(R.id.txtgeneradores_detalle);
                final TextView ltscamiones = (TextView)getView().findViewById(R.id.txtcamiones_detalle);
                final TextView ltsmaquinaria = (TextView)getView().findViewById(R.id.txtmaquinaria_detalle);
                final TextView ltsflotaliviana = (TextView)getView().findViewById(R.id.txtflotaliviana_detalle);
                final TextView ltsotros = (TextView)getView().findViewById(R.id.txtotros_detalle);
                final TextView promediodinero = (TextView)getView().findViewById(R.id.txtpromediocomprado);

                mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                mStringRequest = new StringRequest(Request.Method.GET, apiconsumos + mesencurso + "-01" + "/" + fechaactual, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            String json;
                            json = response.toString();
                            Log.i("TAG",response);
                            JSONArray jsonArray = null;
                            jsonArray = new JSONArray(json);
                            int consumidos = 0;
                            int generadores= 0;
                            int camiones = 0;
                            int maquinaria =0;
                            int flotaliviana=0;
                            int otros=0;

                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String consumi = jsonObject.getString("lcargados");
                                int consum=0;
                                if (consumi.equals("")){
                                    consum=0;
                                }else{

                                    double con = Double.parseDouble(jsonObject.getString("lcargados"));
                                    consum = (int)con;
                                }

                                String macro = jsonObject.getString("macrotipo");

                                int ttt = 0;
                                if (macro.toString().equals("GENERADORES Y PLANTA")){
                                    double tt = Double.parseDouble(jsonObject.getString("lcargados"));
                                    ttt = (int)tt;

                                }
                                int cam = 0;
                                if (macro.toString().equals("CAMIONES")){
                                    double c = Double.parseDouble(jsonObject.getString("lcargados"));
                                    cam = (int) c;
                                }
                                int maq = 0;
                                if (macro.toString().equals("MAQUINARIA")){
                                    double m = Double.parseDouble(jsonObject.getString("lcargados"));
                                    maq = (int) m;
                                }
                                int fl = 0;
                                if (macro.toString().equals("FLOTA LIVIANA")){
                                    double f = Double.parseDouble(jsonObject.getString("lcargados"));
                                    fl = (int)f;
                                }
                                int ot = 0;
                                if (macro.toString().equals("OTROS")){
                                    double o = Double.parseDouble(jsonObject.getString("lcargados"));
                                    ot = (int) o;
                                }
                                generadores = generadores+ttt;
                                consumidos = consumidos+consum;
                                camiones = camiones+cam;
                                maquinaria = maquinaria+maq;
                                flotaliviana = flotaliviana+fl;
                                otros = otros+ot;
                            }

                            SharedPreferences preferences1 = getActivity().getSharedPreferences("precios_petroleo", Context.MODE_PRIVATE);
                            int loncomilla = Integer.parseInt(preferences1.getString("loncomilla", "No disponible"));
                            String pattern ="###,###,###.##";
                            DecimalFormat decimalFormat = new DecimalFormat(pattern);
                            final String totalconsumidos = decimalFormat.format(consumidos);
                            final String ttgene = decimalFormat.format(generadores);
                            final String totalcamiones = decimalFormat.format(camiones);
                            final String totalmaquinaria = decimalFormat.format(maquinaria);
                            final String totalflotaliviana = decimalFormat.format(flotaliviana);
                            final String totalotros = decimalFormat.format(otros);
                            ltsconsumidos.setText(totalconsumidos);
                            generadorestxt.setText("Generadores: "+ttgene);
                            ltscamiones.setText("Camiones: "+totalcamiones);
                            ltsmaquinaria.setText("Maquinaria: "+totalmaquinaria);
                            ltsflotaliviana.setText("Flota liviana: "+totalflotaliviana);
                            ltsotros.setText("Otros: "+totalotros);
                            int formula= (int) ((loncomilla*consumidos)/1.37);
                            final String promedio = decimalFormat.format(formula);
                            promediodinero.setText("($ "+promedio+")");
                            getDiferencia(consumidos,generadores,camiones,maquinaria,flotaliviana,otros);

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


    private void getDiferencia(final int actual, final int generadoractual, final int camionesactual, final int maquinariaactual, final int flotalivianaactual, final int otrosactual){

        final TextView diferencia = (TextView)getView().findViewById(R.id.txtdiferencia);

        final TextView generadorestext = (TextView)getView().findViewById(R.id.txtgeneradores_detalle);
        final TextView camionestext = (TextView)getView().findViewById(R.id.txtcamiones_detalle);
        final TextView maquinariatext = (TextView)getView().findViewById(R.id.txtmaquinaria_detalle);
        final TextView flotalivianatext = (TextView)getView().findViewById(R.id.txtflotaliviana_detalle);
        final TextView otrostext = (TextView)getView().findViewById(R.id.txtotros_detalle);

        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, apiconsumos+mesanteriorsindia+"-01" + "/" + mesanterior, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    String json;
                    json = response.toString();
                    Log.i("TAG",response);
                    JSONArray jsonArray = null;
                    jsonArray = new JSONArray(json);
                    int consumidosmes = 0;
                    int generadores= 0;
                    int camiones = 0;
                    int maquinaria =0;
                    int flotaliviana=0;
                    int otros=0;
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String consumi = jsonObject.getString("lcargados");
                        int consum=0;
                        if (consumi.equals("")){
                            consum=0;
                        }else{

                            double con = Double.parseDouble(jsonObject.getString("lcargados"));
                            consum = (int)con;
                        }
                        String macro = jsonObject.getString("macrotipo");
                        int ttt = 0;
                        if (macro.toString().equals("GENERADORES Y PLANTA")){
                            double tt = Double.parseDouble(jsonObject.getString("lcargados"));
                            ttt = (int)tt;
                        }
                        int cam = 0;
                        if (macro.toString().equals("CAMIONES")){
                            double c = Double.parseDouble(jsonObject.getString("lcargados"));
                            cam = (int) c;
                        }
                        int maq = 0;
                        if (macro.toString().equals("MAQUINARIA")){
                            double m = Double.parseDouble(jsonObject.getString("lcargados"));
                            maq = (int) m;
                        }
                        int fl = 0;
                        if (macro.toString().equals("FLOTA LIVIANA")){
                            double f = Double.parseDouble(jsonObject.getString("lcargados"));
                            fl = (int)f;
                        }
                        int ot = 0;
                        if (macro.toString().equals("OTROS")){
                            double o = Double.parseDouble(jsonObject.getString("lcargados"));
                            ot = (int) o;
                        }
                        generadores = generadores+ttt;
                        camiones = camiones+cam;
                        maquinaria = maquinaria+maq;
                        flotaliviana = flotaliviana+fl;
                        otros = otros+ot;
                        consumidosmes = consumidosmes+consum;

                    }

                    String pattern ="###,###,###.##";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    final String ttgene = decimalFormat.format(generadores);
                    final String totalcamiones = decimalFormat.format(camiones);
                    final String totalmaquinaria = decimalFormat.format(maquinaria);
                    final String totalflotaliviana = decimalFormat.format(flotaliviana);
                    final String totalotros = decimalFormat.format(otros);
                    final String ttgen = decimalFormat.format(generadoractual);
                    final String ttcam = decimalFormat.format(camionesactual);
                    final String ttmaq = decimalFormat.format(maquinariaactual);
                    final String ttfl = decimalFormat.format(flotalivianaactual);
                    final String ttotros = decimalFormat.format(otrosactual);

                    int difgenerador = (generadoractual-generadores);
                    int difcamiones = (camionesactual-camiones);
                    int difmaquinaria= (maquinariaactual-maquinaria);
                    int difflotaliviana = (flotalivianaactual-flotaliviana);
                    int difotros = (otrosactual-otros);

                    final String ttdifgene = decimalFormat.format(difgenerador);
                    final String ttdifcamiones = decimalFormat.format(difcamiones);
                    final String ttdifmaquinaria = decimalFormat.format(difmaquinaria);
                    final String ttdifl = decimalFormat.format(difflotaliviana);
                    final String ttdifotros = decimalFormat.format(difotros);


                    generadorestext.setText("Generadores: "+ttgen+" / Dif: "+ttdifgene);
                    camionestext.setText("Camiones: "+ttcam+" / Dif: "+ttdifcamiones);
                    maquinariatext.setText("Maquinaria: "+ttmaq+" / Dif: "+ttdifmaquinaria);
                    flotalivianatext.setText("Flota liviana: "+ttfl+" / Dif: "+ttdifl);
                    otrostext.setText("Otros: "+ttotros+" / Dif: "+ ttdifotros);

                    int dif = actual-consumidosmes;
                    final String diferenciatotal = decimalFormat.format(dif);

                    diferencia.setText(diferenciatotal);

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
