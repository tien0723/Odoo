package tdc.edu.vn.myodoo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

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
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ImageView imgMenu= findViewById(R.id.imgMenu);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
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
