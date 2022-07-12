package tdc.edu.vn.myodoo.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import tdc.edu.vn.myodoo.Fragment.FragmentAddContact;
import tdc.edu.vn.myodoo.Fragment.FragmentDetailContact;

import tdc.edu.vn.myodoo.R;


public class DetailContactActivity extends AppCompatActivity  {
    private  String fragment;
    private String url,password,db;
    private int uid,id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        Intent intent = getIntent();
        url=intent.getStringExtra("url");
        db=intent.getStringExtra("db");
        password=intent.getStringExtra("password");
        uid=intent.getIntExtra("uid",0);
        id=intent.getIntExtra("id",0);
        fragment = intent.getStringExtra("edit");

        if(fragment == "edit"){

            replaceFragment(new FragmentAddContact());
        }else {

            replaceFragment(new FragmentAddContact());
        }

    }
    //lay id layout fragment
    private void replaceFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        fragment = new FragmentAddContact();
//        Bundle bundle = new Bundle();
//        bundle.putString("url",url);
//        bundle.putInt("uid",uid);
//        bundle.putString("db",db);
//        bundle.putString("password",password);
//        bundle.putInt("id",id);
//        fragment.setArguments(bundle);
        transaction.replace(R.id.fragmentContact, fragment);
        transaction.commit();
    }
}
