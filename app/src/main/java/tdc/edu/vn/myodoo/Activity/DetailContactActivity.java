package tdc.edu.vn.myodoo.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import tdc.edu.vn.myodoo.Fragment.FragmentDetailContact;
import tdc.edu.vn.myodoo.R;

public class DetailContactActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBack,imageEdit;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        drawerLayout = findViewById(R.id.drawerLayoutDetailContact);
        imgBack = findViewById(R.id.imageBack);
        imageEdit= findViewById(R.id.imageEdit);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContactDetail, new FragmentDetailContact());
        transaction.commit();
        imageEdit.setOnClickListener(this);
        imgBack.setOnClickListener(this);

    }
    //su kien click chuot thanh navbar
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
            case R.id.imageEdit:
                //
        }
    }
}
