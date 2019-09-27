package cl.ingenieriasantafe.gerenciapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Combustibles_detalle_asignados_Activity extends AppCompatActivity {


    public static final String apisolicitados = "http://santafeinversiones.com/services/listadogeneral";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    String fecha = dateFormat.format(date);

    private BarChart barChart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combustibles_detalle_asignados_activity);
        barChart = (BarChart)findViewById(R.id.barchart_detalle_asignados);
        BarDataSet barDataSet = new BarDataSet(getData(), "Inducesmile");
        barDataSet.setBarBorderWidth(0.9f);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun","Jan", "Feb", "Mar", "Apr", "May", "Jun","Jan", "Feb", "Mar", "Apr", "May", "Jun"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(1000, 1000);
        barChart.invalidate();

    }

    private ArrayList getData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        entries.add(new BarEntry(4f, 70f));
        entries.add(new BarEntry(5f, 60f));
        entries.add(new BarEntry(6f, 30f));
        entries.add(new BarEntry(7f, 80f));
        entries.add(new BarEntry(8f, 60f));
        entries.add(new BarEntry(9f, 50f));
        entries.add(new BarEntry(10f, 70f));
        entries.add(new BarEntry(11f, 60f));
        entries.add(new BarEntry(12f, 30f));
        entries.add(new BarEntry(13f, 80f));
        entries.add(new BarEntry(14f, 60f));
        entries.add(new BarEntry(15f, 50f));
        entries.add(new BarEntry(16f, 70f));
        entries.add(new BarEntry(17f, 60f));
        return entries;
    }



}
