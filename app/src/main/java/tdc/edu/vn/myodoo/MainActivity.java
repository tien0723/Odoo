package tdc.edu.vn.myodoo;


import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tdc.edu.vn.myodoo.Activity.HomeActivity;
import tdc.edu.vn.myodoo.DataBase.ConectDataBase;

public class MainActivity extends AppCompatActivity {
    EditText edtURL, edtName, edtPass;
    Spinner spnDataBase;
    Button btnLogin;
    List<String> listDataBase = new ArrayList<>();
    final XmlRpcClient client = new XmlRpcClient();
    final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
    private String url = "",
            db = "",
            userName = "",
            password = "";
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
        edtURL = findViewById(R.id.edtURL);
        edtName = findViewById(R.id.edtName);
        edtPass = findViewById(R.id.edtPassword);
        spnDataBase = findViewById(R.id.spnNameDataBase);
        btnLogin = findViewById(R.id.btnLogin);
        edtURL.setText("https://android.t4erp.cf");
        edtName.setText("TienNM.19.TDC@t4Intership.cf");
        edtPass.setText("123456aA@");
    }

    private void setEvent() {
        if (edtURL.getText() != null) {
            url = edtURL.getText() + "";
        } else {
            Toast.makeText(this, "Ten dang nhap k dc bo trong", Toast.LENGTH_LONG).show();
        }
        try {
            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (edtName.getText() != null) {
            userName = edtName.getText() + "";
        } else {
            Toast.makeText(this, "Ten dang nhap k dc bo trong", Toast.LENGTH_LONG).show();
        }
        if (edtPass.getText() != null) {
            password = edtPass.getText() + "";
        } else {
            Toast.makeText(this, "Mat khau k dc bo trong", Toast.LENGTH_LONG).show();
        }
        dataBase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,listDataBase);
        spnDataBase.setAdapter(adapter);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = spnDataBase.getSelectedItem().toString();
                login(url,userName,password,db);
            }
        });

    }
    public void dataBase(){
        Object[] h;
        try {
            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/db", url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            h = (Object[]) client.execute(common_config, "list", emptyList());
            String common[] = new String[h.length];

            for (int i = 0; i < h.length; i++) {
                common[i] = (String) h[i];
                listDataBase.add(common[i]);
              //  Log.d("TAG", "zzzzz: " + common[i]);

            }
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
    }
    public void login(String url,String userName, String password, String db) {
        int uid = Uid(url, userName, password, db);
        Log.d("TAG", "login: " + uid);
        if(uid>0){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(MainActivity.this,"Loi sai login",Toast.LENGTH_LONG).show();
        }
    }

    public int Uid(String url, String userName, String password, String db) {
        int a=0;
        try {
            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            a = (int) client.execute(common_config, "authenticate", asList(db, userName, password, emptyMap()));
            Log.d("TAG", "aaaaaaaaa: " + a);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
}