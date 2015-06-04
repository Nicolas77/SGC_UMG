package com.jn.sgcumg.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jn.sgcumg.MainActivity;
import com.jn.sgcumg.R;
import com.jn.sgcumg.fragments.ClientesFragment;
import com.jn.sgcumg.fragments.DashboardFragment;
import com.jn.sgcumg.models.Dashboard;

import java.util.ArrayList;

/**
 * Created by julionicolas on 3/2/15.
 */
public class DashboardAdapter extends ArrayAdapter<Dashboard>{
    private Context context;
    private ArrayList<Dashboard> items;
    private int layoutResourceId;
    private Dashboard dataList;

    /**
     * Contructor inicializador de variables
     * @param context del app
     * @param layout id del xml fila
     * @param items array de objetos tipo BeansBiBooks
     * */
    public DashboardAdapter(Context context, int layout, ArrayList<Dashboard> items){
        super(context,layout,items);
        this.context=context;
        this.layoutResourceId=layout;
        this.items=items;
    }



    /**
     * Obtener una vista que muestra los datos en la posición especificada en el conjunto de datos.
     * @param position La posición del elemento dentro de los datos del adaptador establecidos de la tarea cuyos vista que queremos.
     * @param conertView El punto de vista de edad reutilizar, si es posible.
     * @param parent el padre de la vista fila
     * @return Una vista correspondiente a los datos en la posición especificada.
     * */
    @Override
    public View getView(final int position,View conertView,ViewGroup parent){
        View row=conertView;
        ViewHolder holder=null;
        if(row==null){
            LayoutInflater inflater =((Activity)context).getLayoutInflater();
            row=inflater.inflate(layoutResourceId,parent,false);
            holder=new ViewHolder();
            holder.textView_title=(TextView)row.findViewById(R.id.textView_title);
            holder.imageView_dashboard=(ImageView)row.findViewById(R.id.imageView_dashboard);
            row.setTag(holder);
        }else{
            holder=(ViewHolder)row.getTag();
        }
        dataList=items.get(position);
        holder.textView_title.setText(dataList.getName());
        holder.imageView_dashboard.setImageResource(dataList.getImage());

        holder.imageView_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList=items.get(position);
                //Toast.makeText(context,""+dataList.getId_dashboard(),Toast.LENGTH_SHORT).show();
                if(dataList.getId_dashboard().contentEquals("UMG_CLIENT")){
                    ((MainActivity)context).replaceFragmentPastFuture(DashboardFragment.newInstance(), ClientesFragment.newInstance(),true);
                   // ((MainActivity)context).addFragmentPastFuture(DashboardFragment.newInstance(), ProductosFragment.newInstance(),true);
                }
            }
        });

        return row;
    }

    /**
     * Clase se declaran las vistas hijas de la fila
     * */
    public class ViewHolder{
        TextView textView_title;
        ImageView imageView_dashboard;
    }


}
