package cl.ingenieriasantafe.gerenciapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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
import java.util.Date;
import java.util.Locale;

public class detalle_combustible_consumidos_unegocio_Activity extends AppCompatActivity {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_combustible_consumidos_unegocio_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getInfoConsumos();

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }


    private void getInfoConsumos(){

        final TextView txttransportes = (TextView)findViewById(R.id.txttransportes);
        final TextView txtparedones = (TextView)findViewById(R.id.txtparedones);
        final TextView txtpichilemu = (TextView)findViewById(R.id.txtpichilemu);
        final TextView txtpencahueetapa1 = (TextView)findViewById(R.id.txtpencahue1);
        final TextView txtgrupo31 = (TextView)findViewById(R.id.txtgrupo31);
        final TextView txtmapel = (TextView)findViewById(R.id.txtmapel);
        final TextView txtcentrovial = (TextView)findViewById(R.id.txtcentrovial);
        final TextView txtcauquenescosta = (TextView)findViewById(R.id.txtcauquenescosta_etapa3);
        final TextView txtasfaltos = (TextView)findViewById(R.id.txtasfaltos);
        final TextView txtcuricosur2 = (TextView)findViewById(R.id.txtcuricosur2);
        final TextView txtnivelservicio = (TextView)findViewById(R.id.txtnivelservicio);
        final TextView txtmaquinaria = (TextView)findViewById(R.id.txtmaquinaria);
        final TextView txthuencuecho = (TextView)findViewById(R.id.txthuencuecho);
        final TextView txtaridos = (TextView)findViewById(R.id.txtaridos);
        final TextView txttalcanorte = (TextView)findViewById(R.id.txttalcanorte2);
        final TextView txtsauzal = (TextView)findViewById(R.id.txtm40);
        final TextView txthuasocampesino = (TextView)findViewById(R.id.txthuasocampesino);
        final TextView txtpavimentacion = (TextView)findViewById(R.id.txtpavimentacion);
        final TextView txtempedrado = (TextView)findViewById(R.id.txtempedrado);
        final TextView txtoficinacentral = (TextView)findViewById(R.id.txtoficinacentral);
        final TextView txtvariasobras = (TextView)findViewById(R.id.txtvariasobras);
        final TextView txtmelipeuco = (TextView)findViewById(R.id.txticalma);
        final TextView txtrutasvariaspencahue = (TextView)findViewById(R.id.txtrutasvariaspencahue);
        final TextView txtrincondelobos = (TextView)findViewById(R.id.txtrincondelobos);
        final TextView txtrutak25 = (TextView)findViewById(R.id.txtk25);
        final TextView txttaller = (TextView)findViewById(R.id.txttaller);
        final TextView txttrucktal = (TextView)findViewById(R.id.txttrucktal);
        final TextView txtlolol = (TextView)findViewById(R.id.txtlolol);
        final TextView txtmayser = (TextView)findViewById(R.id.txtmayser);


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, apiconsumos + mesencurso + "-01" + "/" + fechaactual, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    String json;
                    json = response.toString();
                    JSONArray jsonArray = null;
                    jsonArray = new JSONArray(json);
                    int transportes =0;
                    int paredones = 0;
                    int pichilemu = 0;
                    int pencahueetapaI= 0;
                    int grupo31 =0;
                    int mapel = 0;
                    int centrovial =0;
                    int cauquenescosta=0;
                    int asfaltos=0;
                    int curicosur2=0;
                    int nivelservicio=0;
                    int maquinaria=0;
                    int huencuecho=0;
                    int aridos=0;
                    int talcanorte2=0;
                    int sauzalm40=0;
                    int huasocampesino=0;
                    int pavimentacion=0;
                    int empedrado=0;
                    int oficinacentral=0;
                    int variasobras = 0;
                    int icalma=0;
                    int rutasvariaspencahue=0;
                    int rincondelobos=0;
                    int k25=0;
                    int taller=0;
                    int trucktal=0;
                    int lolol=0;
                    int mayser=0;

                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String une = jsonObject.getString("unegocio");
                        int transp = 0;
                        if (une.toString().equals("TRANSPORTES SANTA FE S.A.")){
                            double t = Double.parseDouble(jsonObject.getString("lcargados"));
                            transp = (int) t;
                        }
                        int pare = 0;
                        if (une.toString().equals("CARDENAL CARO (PAREDONES)")){
                            double p = Double.parseDouble(jsonObject.getString("lcargados"));
                            pare = (int)p;
                        }
                        int pichi= 0;
                        if (une.toString().equals("CARDENAL CARO (PICHILEMU)")){
                            double pi = Double.parseDouble(jsonObject.getString("lcargados"));
                            pichi = (int)pi;
                        }
                        int penc1=0;
                        if (une.toString().equals("PENCAHUE,  ETAPA I")){
                            double pen = Double.parseDouble(jsonObject.getString("lcargados"));
                            penc1 = (int)pen;
                        }
                        int g31 =0;
                        if (une.toString().equals("GRUPO 31")){
                            double g3 = Double.parseDouble(jsonObject.getString("lcargados"));
                            g31 = (int)g3;
                        }
                        int map = 0;
                        if (une.toString().equals("MAPEL")){
                            double mape = Double.parseDouble(jsonObject.getString("lcargados"));
                            map= (int)mape;
                        }
                        int cen = 0;
                        if (une.toString().equals("CENTROVIAL")){
                            double cent = Double.parseDouble(jsonObject.getString("lcargados"));
                            cen = (int)cent;
                        }
                        int caucosta=0;
                        if (une.toString().equals("CAUQUENES COSTA, ETAPA III")){
                            double cau = Double.parseDouble(jsonObject.getString("lcargados"));
                            caucosta = (int)cau;
                        }
                        int asfa=0;
                        if (une.toString().equals("ASFALTOS SANTA FE S.A.")){
                            double as = Double.parseDouble(jsonObject.getString("lcargados"));
                            asfa=(int)as;
                        }
                        int cur=0;
                        if (une.toString().equals("CURICO SUR II")){
                            double c = Double.parseDouble(jsonObject.getString("lcargados"));
                            cur = (int)c;
                        }
                        int ns=0;
                        if (une.toString().equals("NIVEL DE SERVICIO")){
                            double ni = Double.parseDouble(jsonObject.getString("lcargados"));
                            ns=(int)ni;
                        }
                        int maq=0;
                        if (une.toString().equals("MAQUINARIA SANTA FE")){
                            double m = Double.parseDouble(jsonObject.getString("lcargados"));
                            maq=(int)m;
                        }
                        int hue=0;
                        if (une.toString().equals("HUENCUECHO – ASTILLERO")){
                            double h = Double.parseDouble(jsonObject.getString("lcargados"));
                            hue=(int)h;
                        }
                        int ari=0;
                        if (une.toString().equals("ARIDOS SANTA FE S.A.")){
                            double ar = Double.parseDouble(jsonObject.getString("lcargados"));
                            ari=(int)ar;
                        }
                        int tn=0;
                        if (une.toString().equals("TALCA NORTE II")){
                            double tal= Double.parseDouble(jsonObject.getString("lcargados"));
                            tn=(int)tal;
                        }
                        int sm=0;
                        if (une.toString().equals("M-40 SAUZAL")){
                            double sa = Double.parseDouble(jsonObject.getString("lcargados"));
                            sm=(int) sa;
                        }
                        int hc=0;
                        if (une.toString().equals("HUASO  CAMPESINO")){
                            double huaso = Double.parseDouble(jsonObject.getString("lcargados"));
                            hc=(int)huaso;
                        }
                        int pav =0;
                        if (une.toString().equals("PAVIMENTACION ASFALTICA")){
                            double p = Double.parseDouble(jsonObject.getString("lcargados"));
                            pav=(int)p;
                        }
                        int em=0;
                        if (une.toString().equals("EMPEDRADO")){
                            double e = Double.parseDouble(jsonObject.getString("lcargados"));
                            em=(int)e;
                        }
                        int oc=0;
                        if (une.toString().equals("OFICINA CENTRAL")){
                            double o = Double.parseDouble(jsonObject.getString("lcargados"));
                            oc=(int)o;
                        }
                        int vo=0;
                        if (une.toString().equals("VARIAS OBRAS (LAB. TOP)")){
                            double v = Double.parseDouble(jsonObject.getString("lcargados"));
                            vo=(int)v;
                        }
                        int ical=0;
                        if (une.toString().equals("MELIPEUCO - ICALMA")){
                            double ic = Double.parseDouble(jsonObject.getString("lcargados"));
                            ical=(int)ic;
                        }
                        int rvp=0;
                        if (une.toString().equals("RUTAS VARIAS, PENCAHUE")){
                            double rv = Double.parseDouble(jsonObject.getString("lcargados"));
                            rvp=(int)rv;
                        }
                        int rincon=0;
                        if (une.toString().equals("CONSERVACION RINCON DE LOBOS")){
                            double rl = Double.parseDouble(jsonObject.getString("lcargados"));
                            rincon=(int)rl;
                        }
                        int rk25=0;
                        if (une.toString().equals("RUTA K-25")){
                            double rk = Double.parseDouble(jsonObject.getString("lcargados"));
                            rk25=(int)rk;
                        }
                        int ta=0;
                        if (une.toString().equals("TALLER")){
                            double t = Double.parseDouble(jsonObject.getString("lcargados"));
                            ta=(int)t;
                        }
                        int tru=0;
                        if (une.toString().equals("TRUCKTAL S.A.")){
                            double tr = Double.parseDouble(jsonObject.getString("lcargados"));
                            tru=(int)tr;
                        }
                        int lol=0;
                        if (une.toString().equals("LOLOL ETAPA III")){
                            double l = Double.parseDouble(jsonObject.getString("lcargados"));
                            lol=(int)l;
                        }
                        int may=0;
                        if (une.toString().equals("MAYSER SPA")){
                            double m = Double.parseDouble(jsonObject.getString("lcargados"));
                            may=(int)m;
                        }
                        transportes = transportes+transp;
                        paredones = paredones+pare;
                        pichilemu = pichilemu+pichi;
                        pencahueetapaI = pencahueetapaI+penc1;
                        grupo31 = grupo31+g31;
                        mapel = mapel+map;
                        centrovial = centrovial+cen;
                        cauquenescosta = cauquenescosta+caucosta;
                        asfaltos = asfaltos+asfa;
                        curicosur2 = curicosur2+cur;
                        nivelservicio = nivelservicio+ns;
                        maquinaria = maquinaria+maq;
                        huencuecho = huencuecho+hue;
                        aridos = aridos+ari;
                        talcanorte2=talcanorte2+tn;
                        sauzalm40 = sauzalm40+sm;
                        huasocampesino = huasocampesino+hc;
                        pavimentacion = pavimentacion+pav;
                        empedrado = empedrado+em;
                        oficinacentral = oficinacentral+oc;
                        variasobras = variasobras+vo;
                        icalma = icalma+ical;
                        rutasvariaspencahue = rutasvariaspencahue+rvp;
                        rincondelobos = rincondelobos+rincon;
                        k25 = k25+rk25;
                        taller = taller+ta;
                        trucktal = trucktal+tru;
                        lolol = lolol+lol;
                        mayser = mayser+may;
                    }

                    String pattern ="###,###,###.##";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    final String totaltransportes = decimalFormat.format(transportes);
                    final String totalparedones = decimalFormat.format(paredones);
                    final String totalpichilemu = decimalFormat.format(pichilemu);
                    final String totalpencahue1 = decimalFormat.format(pencahueetapaI);
                    final String totalgrupo31 = decimalFormat.format(grupo31);
                    final String totalmapel = decimalFormat.format(mapel);
                    final String totalcentrovial = decimalFormat.format(centrovial);
                    final String totalcauquenescosta = decimalFormat.format(cauquenescosta);
                    final String totalasfaltos = decimalFormat.format(asfaltos);
                    final String totalcuricosur2 = decimalFormat.format(curicosur2);
                    final String totalnivelservicio = decimalFormat.format(nivelservicio);
                    final String totalmaquinaria = decimalFormat.format(maquinaria);
                    final String totalhuencuecho = decimalFormat.format(huencuecho);
                    final String totalaridos = decimalFormat.format(aridos);
                    final String totaltalcanorte2 = decimalFormat.format(talcanorte2);
                    final String totalsauzaulm40 = decimalFormat.format(sauzalm40);
                    final String totalhuasocampesino = decimalFormat.format(huasocampesino);
                    final String totalpavimentacion = decimalFormat.format(pavimentacion);
                    final String totalempedrado = decimalFormat.format(empedrado);
                    final String totaloficinacentral = decimalFormat.format(oficinacentral);
                    final String totalvariasobras = decimalFormat.format(variasobras);
                    final String totalicalma = decimalFormat.format(icalma);
                    final String totalrvp = decimalFormat.format(rutasvariaspencahue);
                    final String totalrincondelobos = decimalFormat.format(rincondelobos);
                    final String totalrutak25 = decimalFormat.format(k25);
                    final String totaltaller = decimalFormat.format(taller);
                    final String totaltrucktal = decimalFormat.format(trucktal);
                    final String totallolol = decimalFormat.format(lolol);
                    final String totalmayser = decimalFormat.format(mayser);
                    txttransportes.setText("Transportes Santa Fe: "+totaltransportes);
                    txtparedones.setText("Cardenal Caro Paredones: "+totalparedones);
                    txtpichilemu.setText("Cardenal Caro Pichilemu: "+totalpichilemu);
                    txtpencahueetapa1.setText("Pencahue, Etapa I: "+totalpencahue1);
                    txtgrupo31.setText("Grupo 31: "+totalgrupo31);
                    txtmapel.setText("Mapel: "+totalmapel);
                    txtcentrovial.setText("Centrovial: "+totalcentrovial);
                    txtcauquenescosta.setText("Cauquenes Costa, Etapa III: "+totalcauquenescosta);
                    txtasfaltos.setText("Asfaltos Santa Fe: "+totalasfaltos);
                    txtcuricosur2.setText("Curico Sur, Etapa II: "+totalcuricosur2);
                    txtnivelservicio.setText("Nivel de Servicio: "+totalnivelservicio);
                    txtmaquinaria.setText("Maquinaria Santa Fe: "+totalmaquinaria);
                    txthuencuecho.setText("Huencuecho - Astillero: "+totalhuencuecho);
                    txtaridos.setText("Aridos Santa Fe: "+totalaridos);
                    txttalcanorte.setText("Talca norte, Etapa II: "+totaltalcanorte2);
                    txtsauzal.setText("Sauzal M-40: "+totalsauzaulm40);
                    txthuasocampesino.setText("Huaso Campesino: "+totalhuasocampesino);
                    txtpavimentacion.setText("Pavimentacion Asfaltica: "+totalpavimentacion);
                    txtempedrado.setText("Empedrado: "+totalempedrado);
                    txtoficinacentral.setText("Oficina Central: "+totaloficinacentral);
                    txtvariasobras.setText("Varias Obras: "+totalvariasobras);
                    txtmelipeuco.setText("Melipeuco - Icalma: "+totalicalma);
                    txtrutasvariaspencahue.setText("Rutas varias, Pencahue: "+totalrvp);
                    txtrincondelobos.setText("Rincon de Lobos: "+totalrincondelobos);
                    txtrutak25.setText("Ruta K-25: "+totalrutak25);
                    txttaller.setText("Taller: "+totaltaller);
                    txttrucktal.setText("Trucktal: "+totaltrucktal);
                    txtlolol.setText("Lolol, Etapa III: "+totallolol);
                    txtmayser.setText("Mayser: "+totalmayser);
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
