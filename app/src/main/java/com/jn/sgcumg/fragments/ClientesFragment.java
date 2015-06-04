package com.jn.sgcumg.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jn.sgcumg.AppController;
import com.jn.sgcumg.R;
import com.jn.sgcumg.Singleton_appControllerUMG;
import com.jn.sgcumg.adapters.ExampleContactAdapter;
import com.jn.sgcumg.models.Cliente;
import com.jn.sgcumg.utils.ClienteItemInterface;
import com.jn.sgcumg.utils.ExampleContactListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientesFragment extends Fragment  implements View.OnClickListener,TextWatcher {

    private View parentView;
    private ExampleContactListView listview;

    private EditText searchBox;
    private String searchString;

    private Object searchLock = new Object();
    boolean inSearchMode = false;
    private ProgressBar updateProgress;
    private ProgressDialog progress;
    private JSONObject obj_ws_json;
    List<ClienteItemInterface> contactList;
    List<ClienteItemInterface> filterList;
    private SearchListTask curSearchTask = null;

    public static ClientesFragment newInstance() {
        ClientesFragment fragment = new ClientesFragment();

        return fragment;
    }

    public ClientesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try {
            parentView=inflater.inflate(R.layout.fragment_clientes, container, false);
            listview = (ExampleContactListView) parentView.findViewById(R.id.listview);
            searchBox = (EditText) parentView.findViewById(R.id.input_search_query);
            updateProgress=(ProgressBar)parentView.findViewById(R.id.updateProgress);
            listview.setFastScrollEnabled(true);
            searchBox.addTextChangedListener(this);
            filterList = new ArrayList<ClienteItemInterface>();
            contactList= new ArrayList<ClienteItemInterface>();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return parentView;

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        try {
            if(Singleton_appControllerUMG.getInstance().check_Network_availability(getActivity())){
                getClientes();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onActivityCreated(savedInstanceState);
    }

    private void getClientes() throws  Exception{

        progress = ProgressDialog.show(getActivity(), getString(R.string.espere),
                getString(R.string.cargando), true);
        Singleton_appControllerUMG.getInstance().arrayCliente.clear();


        //  mParams = new HashMap<String, String>();
        //  mParams.put("usuario", user);
        //  mParams.put("pass", password);


        final String json_test =  "{\n" +
                "\"cliente\":\n" +
                "\t{\n" +
                "\t\"direcCliente\":\"San Francisco 1\",\n" +
                "\t\"dpi\":\"123123123123\",\t\n" +
                "\t\"idCliente\":\"1\",\t\n" +
                "\t\"nombreDelCliente\":\"DANILO ANDRES ESCOLIN RODRIGUEZ\"\n" +
                "\n" +
                "\t}\n" +
                "}";


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Singleton_appControllerUMG.getInstance().TEST_WS,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progress.dismiss();
                        Log.d("prueba ws", jsonObject.toString());

                        try {
                            obj_ws_json = new JSONObject(json_test.toString());
                            Singleton_appControllerUMG.getInstance().arrayCliente=parseJsonCliente(obj_ws_json);
                            contactList=Singleton_appControllerUMG.getInstance().arrayCliente;
                            ExampleContactAdapter adapter = new ExampleContactAdapter(getActivity(), R.layout.example_contact_item, Singleton_appControllerUMG.getInstance().arrayCliente);

                            listview.setAdapter(adapter);

                        } /*catch (JSONException e) {
                            e.printStackTrace();
                        } */catch (Exception e) {
                            e.printStackTrace();
                        }


                        //Toast.makeText(getApplication(),"bien"+obj_ubication.getServicios().get(0).getNombre(),Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("prueba ws", "Error: " + error.getMessage());
                progress.dismiss();
                Toast.makeText(getActivity(),"Error"+error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Priority getPriority() {
                return super.getPriority();
            }
            /*  @Override
                  public Map<String, String> getParams() {
                      return mParams;
                  }
      */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, ClientesFragment.class.getName());
    }

    private ArrayList<ClienteItemInterface> parseJsonCliente(JSONObject obj_ws_json) throws Exception {
        ArrayList<ClienteItemInterface> result = new ArrayList<ClienteItemInterface>();
        try {

            JSONObject obj_cliente = new JSONObject(obj_ws_json.getString("cliente"));
            Cliente cliente = new Cliente();
            cliente.setIdCliente(obj_cliente.getString("idCliente"));
            cliente.setDirecCliente(obj_cliente.getString("direcCliente"));
            cliente.setNombreDelCliente(obj_cliente.getString("nombreDelCliente"));
            result.add(cliente);
            cliente=null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        searchString = searchBox.getText().toString().trim().toUpperCase();

        if(curSearchTask!=null && curSearchTask.getStatus() != AsyncTask.Status.FINISHED)
        {
            try{
                curSearchTask.cancel(true);
            }
            catch(Exception e){
                Log.i("ClientesFragment", "Fail to cancel running search task");
            }

        }
        curSearchTask = new SearchListTask();
        curSearchTask.execute(searchString);

    }

    private class SearchListTask extends AsyncTask<String, Void, String> {


        /**
         * Se ejecuta en el hilo de interfaz antes de doInBackground
         * */
        @Override
        protected String doInBackground(String... params) {
            filterList.clear();

            String keyword = params[0];

            inSearchMode = (keyword.length() > 0);

            if (inSearchMode) {
                for (ClienteItemInterface item : contactList) {
                    Cliente contact = (Cliente)item;

                    if ((contact.getNombreDelCliente().toUpperCase().indexOf(keyword) > -1) ) {
                        filterList.add(item);
                    }

                }


            }
            return null;
        }



        protected void onPostExecute(String result) {

            synchronized(searchLock)
            {

                if(inSearchMode){

                    ExampleContactAdapter adapter = new ExampleContactAdapter(getActivity(), R.layout.example_contact_item, filterList);
                    adapter.setInSearchMode(true);
                    listview.setInSearchMode(true);
                    listview.setAdapter(adapter);
                }
                else{
                    ExampleContactAdapter adapter = new ExampleContactAdapter(getActivity(), R.layout.example_contact_item, contactList);
                    adapter.setInSearchMode(false);
                    listview.setInSearchMode(false);
                    listview.setAdapter(adapter);
                }
            }

        }
    }
}