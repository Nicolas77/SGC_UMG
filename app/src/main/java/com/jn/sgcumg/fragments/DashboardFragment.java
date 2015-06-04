package com.jn.sgcumg.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.jn.sgcumg.R;
import com.jn.sgcumg.Singleton_appControllerUMG;
import com.jn.sgcumg.adapters.DashboardAdapter;
import com.jn.sgcumg.models.Dashboard;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    private DashboardAdapter listAdapter;
    private View parentView;
    private ArrayList<Dashboard> listDashboard;
    private GridView gridView_dashboard;
    private SharedPreferences app_preferences;
    private String user="blank";
    private JSONObject obj_ws_json;
    ImageLoader imageLoader_volley ;
    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();

        return fragment;
    }

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        user=app_preferences.getString("user", user);
        listDashboard = new ArrayList<Dashboard>();
        listDashboard.add(new Dashboard("UMG_CLIENT", R.drawable.ic_perm_contact_calendar_white_48dp,"Clientes"));
        listDashboard.add(new Dashboard("UMG_CATALOGO", R.drawable.ic_file_download_white_48dp,"Catalogo"));
        listDashboard.add(new Dashboard("UMG_REC_DATA", R.drawable.ic_trending_up_white_48dp,"Recolección de datos"));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try {
            parentView=inflater.inflate(R.layout.fragment_dashboard, container, false);
            gridView_dashboard    = (GridView)parentView.findViewById(R.id.gridView_dashboard);

            if(Singleton_appControllerUMG.getInstance().check_Network_availability(getActivity())){
                try {
                    listAdapter = new DashboardAdapter(getActivity(),R.layout.row_dashboard1,listDashboard);

                    gridView_dashboard.setAdapter(listAdapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getActivity(), "Error conexion", Toast.LENGTH_SHORT).show();
            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return parentView;
    }



}
