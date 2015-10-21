package com.example.poblenou.eltemps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ArrayAdapter<String> myAdapter;
    private ArrayList<String> items;
    private ListView miListaTiempo;
    private TextView misDias;

    MenuItem refresh;

    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment, menu);
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
}
