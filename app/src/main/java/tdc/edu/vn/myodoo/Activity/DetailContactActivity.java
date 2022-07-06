package tdc.edu.vn.myodoo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import tdc.edu.vn.myodoo.Fragment.FragmentDetailContact;
import tdc.edu.vn.myodoo.R;

public class DetailContactActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageEdit;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        //setControl
        drawerLayout = findViewById(R.id.drawerLayoutDetailContact);
        imageEdit= findViewById(R.id.imageEdit);
        imageEdit.setOnClickListener(this);
        //set fragment hien thi
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContactDetail, new FragmentDetailContact());
        transaction.commit();


    }
    //su kien click chuot thanh navbar
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.imageEdit:
               Intent intent = new Intent(DetailContactActivity.this,EditContactActivity.class);
               startActivity(intent);
        }
    }
}
