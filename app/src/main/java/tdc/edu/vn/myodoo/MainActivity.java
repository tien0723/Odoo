package tdc.edu.vn.myodoo;

import static java.util.Collections.emptyList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tdc.edu.vn.myodoo.Activity.HomeActivity;
import tdc.edu.vn.myodoo.DataBase.DataBaseOdoo;


public class MainActivity extends AppCompatActivity {
    EditText edtURL, edtName, edtPass;
    Spinner spnDataBase;
    Button btnLogin;
    List<String> listDataBase = new ArrayList<>();
    DataBaseOdoo dataBaseOdoo = new DataBaseOdoo();
    private String url = "", db = "", userName = "", password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //handle threah
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setControl();
        setEvent();


    }

    private void setControl() {
        edtURL = findViewById(R.id.edtSelfHostedURL);
        edtName = findViewById(R.id.edtUserName);
        edtPass = findViewById(R.id.edtPassWord);
        spnDataBase = findViewById(R.id.spinnerDatabaseList);
        btnLogin = findViewById(R.id.btnLogin);

    }

    //    private void setEvent() {
//        userName = edtName.getText().toString();
//        password = edtPass.getText().toString();
//        checkServer();
//
//        Log.d("TAG", "setEvent: "+db);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                StringBuilder serverURL = new StringBuilder();
//                if (!url.contains("http://") && !url.contains("https://")) {
//                    serverURL.append("https://");
//                }
//                serverURL.append(url);
//                try {
//                    common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//                db = spnDataBase.getSelectedItem().toString();
//                login(url, userName, password, db);
//            }
//        });
//
//    }
//    public void checkServer(){
//        edtURL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if(!b){
//                    dataBase();
//                    findViewById(R.id.serverURLCheckProgress);
//                    findViewById(R.id.imgValidURL);
//                }
//            }
//        });
//        edtURL.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (keyEvent != null  && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
//                        || i == EditorInfo.IME_ACTION_NEXT) {
//                    dataBase();
//                    findViewById(R.id.serverURLCheckProgress);
//                    findViewById(R.id.imgValidURL);
//                }
//                return false;
//            }
//        });
//    }
//    public void dataBase() {
//        url = edtURL.getText().toString();
//        StringBuilder serverURL = new StringBuilder();
//        if (!url.contains("http://") && !url.contains("https://")) {
//            serverURL.append("https://");
//        }
//        serverURL.append(url);
//        Object[] h;
//        try {
//            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/db", serverURL.toString())));
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            h = (Object[]) client.execute(common_config, "list", emptyList());
//            String common[] = new String[h.length];
//
//            for (int i = 0; i < h.length; i++) {
//                common[i] = (String) h[i];
//                listDataBase.add(common[i]);
//                  Log.d("TAG", "zzzzz: " + common[i]);
//            }
//        } catch (XmlRpcException e) {
//            e.printStackTrace();
//        }
//        if(listDataBase.size()>0 ){
//            findViewById(R.id.layoutBorderDB).setVisibility(View.VISIBLE);
//            findViewById(R.id.layoutDatabase).setVisibility(View.VISIBLE);
//            listDataBase.add(0,"Select Database");
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,listDataBase);
//            spnDataBase.setAdapter(adapter);
//        }
//        url=serverURL.toString();
//    }

    public void setEvent() {

//        url = edtURL.getText().toString();
        checkServer();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.setError(null);
                edtPass.setError(null);
                if (TextUtils.isEmpty(edtName.getText())) {
                    edtName.setError("Loi Username");
                    edtName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(edtPass.getText())) {
                    edtPass.setError("Loi password");
                    edtPass.requestFocus();
                    return;
                }
                userName = edtName.getText().toString();
                password = edtPass.getText().toString();
                Log.d("TAG", "onClick: "+url+userName+password+db);
                db = spnDataBase.getSelectedItem().toString();
                login(url, userName, password, db);
            }
        });
    }

    public void checkServer() {
            //kiem tra click chuot khoi edittext
            edtURL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    //Tao thoi gian check server
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            url = edtURL.getText().toString();
                            listDataBase.clear();
                            dataBaseOdoo.dataBase(url, listDataBase);
                            //kiem tra edittext
                            if (!hasFocus && view.getId() == R.id.edtSelfHostedURL) {
                                    Log.d("TAG", "zzzzz: " + listDataBase);
                                    //kiem tra database
                                    if(listDataBase.size()>1){
                                        findViewById(R.id.imgValidURL).setVisibility(View.VISIBLE);
                                        findViewById(R.id.serverURLCheckProgress).setVisibility(View.GONE);
                                        showDatabases(listDataBase);
                                    }else if (listDataBase.size() == 1){
                                        findViewById(R.id.imgValidURL).setVisibility(View.VISIBLE);
                                        findViewById(R.id.serverURLCheckProgress).setVisibility(View.GONE);
                                    }else {
                                        edtURL.setError("Loi sai url");
                                        edtURL.requestFocus();
                                        findViewById(R.id.serverURLCheckProgress).setVisibility(View.GONE);
                                        return;
                                    }

                            }
                            //Neu click khoi edittext se kiem tra lai url
                            else {
                                findViewById(R.id.imgValidURL).setVisibility(View.GONE);
                                findViewById(R.id.serverURLCheckProgress).setVisibility(View.VISIBLE);
                                findViewById(R.id.layoutBorderDB).setVisibility(View.GONE);
                                findViewById(R.id.layoutDatabase).setVisibility(View.GONE);
                            }
                        }
                    }, 500);

                }
            });
            //kiem tra nut chuyen dong trong ban phim dt
            edtURL.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                            || i == EditorInfo.IME_ACTION_NEXT) {
                        url = edtURL.getText().toString();
                        dataBaseOdoo.dataBase(url, listDataBase);
                        findViewById(R.id.imgValidURL).setVisibility(View.VISIBLE);
                        findViewById(R.id.serverURLCheckProgress).setVisibility(View.GONE);
                        showDatabases(listDataBase);
                    }
                    return false;
                }
            });



    }

    private void showDatabases(List<String> listDB) {
        Log.d("TAG", "showDatabases: "+listDataBase.size());
        if (listDB.size() > 1) {
            findViewById(R.id.layoutBorderDB).setVisibility(View.VISIBLE);
            findViewById(R.id.layoutDatabase).setVisibility(View.VISIBLE);
            spnDataBase = (Spinner) findViewById(R.id.spinnerDatabaseList);
            listDataBase.add(0, "Select Database");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listDataBase);
            spnDataBase.setAdapter(adapter);
        }
    }


    public void login(String url, String userName, String password, String db) {
        int uid = dataBaseOdoo.Uid(url, userName, password, db);
        Log.d("TAG", "login: " + uid);
        if (uid > 0) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Loi sai login", Toast.LENGTH_LONG).show();
        }
    }
}