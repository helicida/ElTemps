package com.example.poblenou.eltemps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento = inflater.inflate(R.layout.fragment_main, container, false);

        items = new ArrayList<>();
        misDias = (TextView) fragmento.findViewById(R.id.misDias);
        miListaTiempo = (ListView) fragmento.findViewById(R.id.listaTiempo);
        myAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,items);
        miListaTiempo.setAdapter(myAdapter);
        miListaTiempo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                myAdapter.notifyDataSetChanged();
                return true;
            }
        });

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
