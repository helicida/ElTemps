package com.example.poblenou.eltemps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.poblenou.eltemps.json.Forecast;
import com.example.poblenou.eltemps.json.List;

import java.util.ArrayList;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;

import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;



/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ArrayAdapter<String> myAdapter; //Adaptador per al listView
    private ArrayList<String> items;    ///ArrayList amb els items **provisional
    private ListView miListaTiempo; //ListView on mostrarem els items
    private TextView misDias;       //TestView donde mostraremos los dias
    private final String city = "Barcelona";
    private String BaseURL = "http://api.openweathermap.org/data/2.5/";
    private String apiID = "08d35d57782699eba8799fd29a029932";
    private OpenWeatherMapService service;

    public MainActivityFragment() {

    }

    //OpenWeather URL http://api.openweathermap.org/data/2.5/forecast/city?id=3120619&APPID=08d35d57782699eba8799fd29a029932

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);        //Aixo fa que mostri el menu. Com n'hi han fragments no grafics cal especificar-ho
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refresh(getContext());  //Fem que al presionar el Refresh cridi al metode refresh
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refresh(Context context){

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(context);
        String city = preferencias.getString("City", "Barcelona");

        Retrofit retrofit = new Retrofit.Builder()  //Retrofit
                .baseUrl(BaseURL)   //Primera parte de la url
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(OpenWeatherMapService.class);    //

        Call<Forecast> llamada = service.dailyForecast("Barcelona", "json", "metric", 14, apiID);
            llamada.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Response<Forecast> response, Retrofit retrofit) {
                Forecast forecast = response.body();

                ArrayList<String> arrayTemps = new ArrayList<>();
                    for (List list : forecast.getList()){
                     String tempsString = list.getWeather().get(0).getDescription();
                     arrayTemps.add(tempsString);
                }
                myAdapter.clear();
                myAdapter.addAll(arrayTemps);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento = inflater.inflate(R.layout.fragment_main, container, false);    //Fragment

        items = new ArrayList<>();     //array list que contindrà els dies

        misDias = (TextView) fragmento.findViewById(R.id.misDias);  //Asignem el ID
        miListaTiempo = (ListView) fragmento.findViewById(R.id.listaTiempo);    //Asignme el id

        myAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);  // Definim adaptador al layaout predefinit i al nostre array items
        miListaTiempo.setAdapter(myAdapter);    //Acoplem el adaptador

        //Si apretem un temps l'opció del ListView, la opció desapareixerà
        miListaTiempo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                myAdapter.notifyDataSetChanged();
                return true;
            }
        });

        //afegim diverses entrades al ListView
        myAdapter.add("Lunes 19/10/2015 - Nublado");
        myAdapter.add("Martes 20/10/2015 - Nublado");
        myAdapter.add("Miercoles 21/10/2015 - Nublado");
        myAdapter.add("Jueves 22/10/2015 - Nublado");
        myAdapter.add("Viernes 23/10/2015 - Nublado");
        myAdapter.add("Sabado 14/10/2015 - Nublado");
        myAdapter.add("Domingo 25/10/2015 - Nublado");

        return fragmento;
    }

    public interface OpenWeatherMapService{
        @GET("forecast/daily")
        Call<Forecast> dailyForecast(
                @Query("q") String city,
                @Query("mode") String format,
                @Query("units") String units,
                @Query("cnt") Integer num,
                @Query("appid") String appid);
    }
}
