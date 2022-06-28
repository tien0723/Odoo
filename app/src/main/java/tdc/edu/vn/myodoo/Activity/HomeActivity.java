package tdc.edu.vn.myodoo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.util.ArrayList;
import java.util.List;

import tdc.edu.vn.myodoo.Adapter.AdapterContact;
import tdc.edu.vn.myodoo.DataBase.DataBaseHomeOdoo;
import tdc.edu.vn.myodoo.DataBase.DataBaseLoginOdoo;
import tdc.edu.vn.myodoo.Model.Contact;
import tdc.edu.vn.myodoo.R;

public class HomeActivity extends AppCompatActivity {

    DataBaseHomeOdoo dataBaseHomeOdoo = new DataBaseHomeOdoo();
    List<Contact> contacts = new ArrayList<>();
    ListView lvContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //handle threah
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        setControl();
        listContact();
    }

    private void listContact() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String db = intent.getStringExtra("db");
        String password = intent.getStringExtra("password");
        int uid = intent.getIntExtra("uid",0);
        contacts = dataBaseHomeOdoo.listContact(url,db,password,uid);
        lvContact.setAdapter(new AdapterContact(HomeActivity.this, R.layout.item_contact_layout, contacts));
    }
        private void setControl () {
            lvContact = findViewById(R.id.lvContact);
        }


}
