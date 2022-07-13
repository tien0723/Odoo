package tdc.edu.vn.myodoo.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import tdc.edu.vn.myodoo.Fragment.ContactFragment;
import tdc.edu.vn.myodoo.Fragment.FragmentAddContact;
import tdc.edu.vn.myodoo.Fragment.FragmentDetailContact;

import tdc.edu.vn.myodoo.R;


public class DetailContactActivity extends AppCompatActivity  {
    private  String fragment="add";
    private static final int FRAGMENT_EDIT = 0;
    private static final int FRAGMENT_ADD = 1;
    private int mCurrentFragment = FRAGMENT_ADD;
    DrawerLayout mDrawerLayout;

    private String url,password,db;
    private int uid,id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        mDrawerLayout = findViewById(R.id.drawerLayoutDetailContact);
        Intent intent = getIntent();
        url=intent.getStringExtra("url");
        db=intent.getStringExtra("db");
        password=intent.getStringExtra("password");
        uid=intent.getIntExtra("uid",0);
        id=intent.getIntExtra("id",0);
        fragment = intent.getStringExtra("edit");
        Log.d("TAG", "onCreate: "+fragment);

        if(fragment != null){
            if (fragment.equals("edit")&&mCurrentFragment!=FRAGMENT_EDIT) {
                replaceFragment(new FragmentDetailContact());
                mCurrentFragment = FRAGMENT_EDIT;
            }
        }else {
            replaceFragment(new FragmentAddContact());
            mCurrentFragment=FRAGMENT_ADD;
        }



    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    //lay id layout fragment
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContact, fragment);
        transaction.commit();
    }
}
