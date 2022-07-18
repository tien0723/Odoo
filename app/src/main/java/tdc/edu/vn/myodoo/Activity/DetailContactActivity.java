package tdc.edu.vn.myodoo.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import tdc.edu.vn.myodoo.DataBase.DataBaseHomeOdoo;
import tdc.edu.vn.myodoo.Fragment.FragmentAddContact;
import tdc.edu.vn.myodoo.Fragment.FragmentDetailContact;
import tdc.edu.vn.myodoo.Model.Contact;
import tdc.edu.vn.myodoo.R;


public class DetailContactActivity extends AppCompatActivity {
    //khoi tao
    private String fragment = "add";
    private Fragment selectFragment;
    private static final int FRAGMENT_EDIT = 0;
    private static final int FRAGMENT_ADD = 1;
    private int mCurrentFragment = FRAGMENT_ADD;
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    TextView tvSave;
    DataBaseHomeOdoo dataBaseHomeOdoo = new DataBaseHomeOdoo();
    private String url, password, db;
    private int uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        navigationView = findViewById(R.id.fragmentContact);
        mDrawerLayout = findViewById(R.id.drawerLayoutDetailContact);
        tvSave = findViewById(R.id.tvSave);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        db = intent.getStringExtra("db");
        password = intent.getStringExtra("password");
        uid = intent.getIntExtra("uid", 0);
        fragment = intent.getStringExtra("edit");
        //hien thi fragment duoc chon
        if (fragment != null) {
            if (fragment.equals("edit") && mCurrentFragment != FRAGMENT_EDIT) {
                mCurrentFragment = FRAGMENT_EDIT;
                selectFragment = new FragmentDetailContact();
                fragment = "edit";
            }
        } else {
            selectFragment = new FragmentAddContact();
            mCurrentFragment = FRAGMENT_ADD;
            fragment = "add";
        }
        //danh dau fragment duoc chon
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContact, selectFragment, fragment).commit();
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = null;
                FragmentDetailContact fragmentDetailContact = (FragmentDetailContact) getSupportFragmentManager().findFragmentByTag("edit");
                FragmentAddContact fragmentAddContact = (FragmentAddContact) getSupportFragmentManager().findFragmentByTag("add");
                if (fragmentDetailContact != null && fragmentDetailContact.isVisible()) {
                    contact = fragmentDetailContact.getContactEdit();
                    dataBaseHomeOdoo.updateContact(url, db, password, uid, contact);

                } else if (fragmentAddContact != null && fragmentAddContact.isVisible()) {
                    contact = fragmentAddContact.getContactAdd();
                    dataBaseHomeOdoo.addContact(url, db, password, uid, contact);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }
}
