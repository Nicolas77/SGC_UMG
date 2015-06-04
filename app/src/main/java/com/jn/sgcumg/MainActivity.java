package com.jn.sgcumg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.jn.sgcumg.activities.LoginActivity;
import com.jn.sgcumg.adapters.DrawerAdapter;
import com.jn.sgcumg.fragments.ClientesFragment;
import com.jn.sgcumg.fragments.DashboardFragment;
import com.jn.sgcumg.models.DrawerItem;
import com.jn.sgcumg.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    public static final String LEFT_MENU_OPTION = "com.jn.sgcumg.MainActivity";
    public static final String LEFT_MENU_USER_LOGIN = "UserLogin";
    public static final String LEFT_MENU_USER_LOGOUT = "UserLogout";
    private ImageLoader imageLoader_volley;
    private ListView mDrawerList;
    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public static ImageView image_perfil;
    public static TextView TextView_User;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private Handler mHandler;
    private SharedPreferences app_preferences;
    private SharedPreferences.Editor editor ;
    private String USUARIO="blank";
    private String PASSWORD="blank";
    private String EMAIL="blank";
    private String NOMBRE="blank";
    private String APELLIDO="blank";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app_preferences= PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_view);
        USUARIO=app_preferences.getString("USUARIO", USUARIO);
        PASSWORD=app_preferences.getString("PASSWORD", PASSWORD);

          if((USUARIO.contentEquals("blank"))&&(USUARIO.contentEquals("blank"))){
          Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        // return ;
        }

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        imageLoader_volley = AppController.getInstance().getImageLoader();
        prepareNavigationDrawerItems();
        setAdapter();

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        //mDrawerList.setAdapter(new DrawerAdapter(this, mDrawerItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mHandler = new Handler();
        if (savedInstanceState == null) {
            //mDrawerLayout.openDrawer(mDrawerList);
        }


        //implementa fragment dashboard

        try {
           Fragment fragment = DashboardFragment.newInstance();
            commitFragment(fragment);
        } catch (Exception e) {
            e.printStackTrace();
            //  Toast.makeText(getApplicationContext(),"error "+e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }


    }


    private void setAdapter() {
        String option = LEFT_MENU_USER_LOGIN;
       /* if((user.contentEquals("blank"))&&(pass.contentEquals("blank"))){
            option = LEFT_MENU_USER_LOGOUT;
        }else{
            option = LEFT_MENU_USER_LOGIN;
        }*/

        boolean isFirstType = true;

        View headerView = null;
        if (option.equals(LEFT_MENU_USER_LOGIN)) {
            try {
                getInfoUser();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (option.equals(LEFT_MENU_USER_LOGOUT)) {
            headerView = prepareHeaderViewLogout(R.layout.header_navigation_drawer_2,
                    "http://www.unicoos.com/unicoosWeb/img/profiles/default.png",
                    "Iniciar sesion");
            //isFirstType = false;
            mDrawerList.addHeaderView(headerView);//Add header before adapter (for pre-KitKat)
            BaseAdapter adapter = new DrawerAdapter(this, mDrawerItems, 3);


            mDrawerList.setAdapter(adapter);
        }





    }


    private void getInfoUser() throws Exception{
// Post params to be sent to the server
        Toast.makeText(getApplicationContext(), "entra", Toast.LENGTH_SHORT).show();
        EMAIL=app_preferences.getString("EMAIL", EMAIL);
        NOMBRE=app_preferences.getString("NOMBRE", NOMBRE);
        APELLIDO=app_preferences.getString("APELLIDO", APELLIDO);

        View headerView = null;
        headerView = prepareHeaderView(R.layout.header_navigation_drawer_1,
                "http://cdn.umg.edu.gt/images/logo/480.png",
                NOMBRE+" "+APELLIDO);
        mDrawerList.addHeaderView(headerView);//Add header before adapter (for pre-KitKat)
        BaseAdapter adapter = new DrawerAdapter(MainActivity.this, mDrawerItems, 3);

        mDrawerList.setAdapter(adapter);

    }

    private View prepareHeaderView(int layoutRes, String url, String user) {
        AppController.getInstance().getRequestQueue().getCache().invalidate(url, true);
        View headerView = getLayoutInflater().inflate(layoutRes, mDrawerList, false);
        image_perfil = (ImageView) headerView.findViewById(R.id.ImageView_perfil);
        TextView_User = (TextView) headerView.findViewById(R.id.TextView_User);
        ImageView imageView_settings = (ImageView)headerView.findViewById(R.id.imageView_settings);
        //ImageUtil.displayRoundImage(iv, url, null);
        //Picasso.with(MainActivity.this).load(url).placeholder(R.drawable.imagen_perfil_default).into(iv)

        imageLoader_volley.get(url, ImageLoader.getImageListener(
                image_perfil, R.drawable.imagen_perfil_default, R.drawable.imagen_perfil_default));
        /*imageLoader.get(url, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    iv.setImageBitmap(response.getBitmap());
                }
            }
        });*/


        TextView_User.setText(user);
        imageView_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Singleton_appControllerUMG.getInstance().check_Network_availability(MainActivity.this)){

                    // Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_SHORT).show();
                   // Intent intent = new Intent(MainActivity.this,PerfilActivity.class);
                    //startActivity(intent);
                    //mDrawerLayout.closeDrawer(mDrawerList);
                }

            }
        });
        image_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        headerView.findViewById(R.id.ImageView_backgroudDrawer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return headerView;
    }




    private View prepareHeaderViewLogout(int layoutRes, String url, String email) {
        View headerView = getLayoutInflater().inflate(layoutRes, mDrawerList, false);
        ImageView iv = (ImageView) headerView.findViewById(R.id.image);
        TextView tv = (TextView) headerView.findViewById(R.id.TextView_login);
        ImageUtil.displayRoundImage(iv, url, null);

        //tv.setText(email);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                //startActivity(intent);
                //mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        headerView.findViewById(R.id.ImageView_backgroudDrawer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return headerView;
    }

    private void prepareNavigationDrawerItems() {
        mDrawerItems = new ArrayList<DrawerItem>();
        mDrawerItems.add(
                new DrawerItem(
                        R.drawable.ic_launcher,
                        R.string.clientes,
                        DrawerItem.DRAWER_ITEM_TAG_CLIENTES));
        mDrawerItems.add(
                new DrawerItem(
                        R.drawable.ic_launcher,
                        R.string.catalogos,
                        DrawerItem.DRAWER_ITEM_TAG_CATALOGOS));
        mDrawerItems.add(
                new DrawerItem(
                        R.drawable.ic_launcher,
                        R.string.recoleccion_datos,
                        DrawerItem.DRAWER_ITEM_TAG_RECOLECCCION_DATOS));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position, mDrawerItems.get(position - 1).getTag());
        }
    }

    private void selectItem(int position, int drawerTag) {

        Fragment fragment = getFragmentByDrawerTag(drawerTag);
        commitFragment(fragment);

        // minus 1 because we have header that has 0 position
        if (position < 1) { //because we have header, we skip clicking on it
            return;
        }
        String drawerTitle = getString(mDrawerItems.get(position - 1).getTitle());
        //Toast.makeText(this, "You selected " + drawerTitle + " at position: " + position, Toast.LENGTH_SHORT).show();

        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerItems.get(position - 1).getTitle());
        mDrawerLayout.closeDrawer(mDrawerList);

    }



    private Fragment getFragmentByDrawerTag(int drawerTag) {
        Fragment fragment = null;
        if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_CLIENTES) {
            fragment = new Fragment();
            fragment = ClientesFragment.newInstance();

        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_CATALOGOS) {
            fragment = new Fragment();
            //fragment = ProgressBarsFragment.newInstance();
        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_RECOLECCCION_DATOS) {
            fragment = new Fragment();
            //fragment = Ubication_tab_fragment.newInstance();
            //replaceFragmentPastFuture(new ListUbicationFragment(), new Ubication_tab_fragment(), false);
        }else {
            fragment = new Fragment();
        }
        return fragment;
    }

    public void replaceFragmentPastFuture(Fragment fragPast,Fragment fragFuture, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentManager fm =getSupportFragmentManager();

        if(addToBackStack){
            ft.hide(fragPast);
        }else{
            ft.remove(fragPast);
            Log.i("cout pasado", "" + fm.getBackStackEntryCount());
            for(int i=0;i<fm.getBackStackEntryCount();i++){
                fm.popBackStack();
            }
            getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        ft.replace(R.id.content_frame,fragFuture,"fragment");
        if (addToBackStack) {
            ft.addToBackStack(fragPast.toString());
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        ft.commit();
    }


    public void addFragmentPastFuture(Fragment fragPast,Fragment fragFuture, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentManager fm =getSupportFragmentManager();

        if(addToBackStack){
            ft.hide(fragPast);
            ft.add(R.id.content_frame,fragFuture,"fragment");
        }else{
            ft.remove(fragPast);
            Log.i("cout pasado", ""+fm.getBackStackEntryCount());
            for(int i=0;i<fm.getBackStackEntryCount();i++){
                fm.popBackStack();
            }
            getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.replace(R.id.content_frame,fragFuture,"fragment");
        }

        if (addToBackStack) {
            ft.addToBackStack(fragPast.toString());
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        ft.commit();
    }



    private class CommitFragmentRunnable implements Runnable {

        private Fragment fragment;

        public CommitFragmentRunnable(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void run() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    private class CommitFragmentRunnableJ implements Runnable {

        private Fragment fragPast;
        private Fragment fragFuture;
        private boolean addToBackStack;

        public CommitFragmentRunnableJ(Fragment fragPast,Fragment fragFuture, boolean addToBackStack) {
            this.fragPast = fragPast;
            this.fragFuture = fragFuture;
            this.addToBackStack=addToBackStack;
        }

        @Override
        public void run() {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentManager fm =getSupportFragmentManager();

            if(addToBackStack){
                ft.hide(fragPast);
            }else{
                ft.remove(fragPast);
                Log.i("cout pasado", ""+fm.getBackStackEntryCount());
                for(int i=0;i<fm.getBackStackEntryCount();i++){
                    fm.popBackStack();
                }
                getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            ft.replace(R.id.content_frame,fragFuture,"fragment");
            if (addToBackStack) {
                ft.addToBackStack(fragPast.toString());
            }
            ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            ft.commit();


        }
    }

    public void commitFragment(Fragment fragment) {
        //Using Handler class to avoid lagging while
        //committing fragment in same time as closing
        //navigation drawer
        mHandler.post(new CommitFragmentRunnable(fragment));
    }

    public void commitFragmentJ(Fragment fragPast,Fragment fragFuture, boolean addToBackStack) {
        //Using Handler class to avoid lagging while
        //committing fragment in same time as closing
        //navigation drawer
        mHandler.post(new CommitFragmentRunnableJ(fragPast,fragFuture,addToBackStack));
    }




    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
