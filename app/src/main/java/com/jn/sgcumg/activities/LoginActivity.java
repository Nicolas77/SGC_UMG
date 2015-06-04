package com.jn.sgcumg.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jn.sgcumg.AppController;
import com.jn.sgcumg.MainActivity;
import com.jn.sgcumg.R;
import com.jn.sgcumg.Singleton_appControllerUMG;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class LoginActivity extends Activity implements View.OnClickListener{

    private TextView login;
    private SharedPreferences app_preferences;
    private SharedPreferences.Editor editor ;
    private EditText EditText_user;
    private EditText EditText_pass;
    private ProgressDialog progress;
    private Map<String, String> mParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app_preferences= PreferenceManager.getDefaultSharedPreferences(this);
        login           = (TextView) findViewById(R.id.login);
        EditText_user   = (EditText) findViewById(R.id.EditText_user);
        EditText_pass   = (EditText) findViewById(R.id.EditText_pass);
        login.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    @Override
    public void onClick(View view) {
        if(view==login){

            if(TextUtils.isEmpty(EditText_user.getText().toString().trim())){
                Toast.makeText(getApplicationContext(), R.string.not_blank_user, Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(EditText_pass.getText().toString().trim())){
                Toast.makeText(getApplicationContext(), R.string.not_blank_password, Toast.LENGTH_SHORT).show();
            }else{

                try {
                    if(Singleton_appControllerUMG.getInstance().check_Network_availability(getApplicationContext())){
                        ValidationLogin(EditText_user.getText().toString(),EditText_pass.getText().toString());
                    }else{

                        Toast.makeText(getApplicationContext(), R.string.error_conection, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        }
    }


    private void ValidationLogin(final String user,final String password) throws  Exception{

        progress = ProgressDialog.show(LoginActivity.this, getString(R.string.espere),
                getString(R.string.ingresando), true);

        // Post params to be sent to the server
        JSONObject params = new JSONObject();
        try {
            params.put("usuario",user);
            params.put("pass",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  mParams = new HashMap<String, String>();
        //  mParams.put("usuario", user);
        //  mParams.put("pass", password);



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Singleton_appControllerUMG.getInstance().TEST_WS,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progress.dismiss();
                        Log.d("prueba ws", jsonObject.toString());

                        try {

                            Toast.makeText(getApplicationContext(),"ingreso exitoso",Toast.LENGTH_SHORT).show();

                            editor = app_preferences.edit();
                            //editor.putInt("PK_COD_ASESOR", jsonObject.getInt("PK_COD_ASESOR"));
                            editor.putString("USUARIO", jsonObject.getString("USUARIO"));
                            //editor.putString("PASSWORD", jsonObject.getString("PASSWORD"));
                            editor.putString("EMAIL", jsonObject.getString("EMAIL"));
                            editor.putString("NOMBRE", jsonObject.getString("NOMBRE"));
                            editor.putString("APELLIDO", jsonObject.getString("APELLIDO"));
                            editor.putString("PK_COD_SUCURSAL", jsonObject.getString("PK_COD_SUCURSAL"));
                            editor.commit();

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                           /* if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
                            {
                                finishAffinity();
                            }else{
                                ActivityCompat.finishAffinity(LoginActivity.this);
                            }*/



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
                Toast.makeText(getApplicationContext(),"Error"+error.toString(),Toast.LENGTH_SHORT).show();
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
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }*/

            @Override
            public byte[] getBody() {
                String httpPostBody ="{usuario:\""+user+"\",pass:\""+password+"\"}";
                Log.d("body", httpPostBody);
                return httpPostBody.getBytes();
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, LoginActivity.class.getName());
    }


}
