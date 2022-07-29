package tdc.edu.vn.myodoo.Activity;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    TextView tvSave,tvBack;
    DataBaseHomeOdoo dataBaseHomeOdoo = new DataBaseHomeOdoo();
    private String url, password, db;
    private int uid;

    TextView title,tvMess;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        //ánh xạ du lieu
        navigationView = findViewById(R.id.fragmentContact);
        mDrawerLayout = findViewById(R.id.drawerLayoutDetailContact);
        tvSave = findViewById(R.id.tvSave);
        tvBack = findViewById(R.id.tvBack);
        //lay intent
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
        //xu ly nut save
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = null;
                FragmentDetailContact fragmentDetailContact = (FragmentDetailContact) getSupportFragmentManager().findFragmentByTag("edit");
                FragmentAddContact fragmentAddContact = (FragmentAddContact) getSupportFragmentManager().findFragmentByTag("add");
                if (fragmentDetailContact != null && fragmentDetailContact.isVisible()) {
                    contact = fragmentDetailContact.getContactEdit();
                    Log.d("TAG", "update thanh cong: ");
                    showDialogUpdate("Ban co chac muon thay doi chinh sua",contact,url, db, password, uid);
                } else if (fragmentAddContact != null && fragmentAddContact.isVisible()) {
                    contact = fragmentAddContact.getContactAdd();
                    Log.d("TAG", "them thanh cong: ");
                    showDialogAdd("Ban co chac muon them mot contact",contact,url, db, password, uid);
                }
            }
        });
        //xu ly nut back
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

    //layout success
    private void showDialogUpdate(String message, Contact contact,String url,String db,String password, int uid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailContactActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(DetailContactActivity.this).inflate(
                R.layout.layout_success_dialog,
                findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        title = view.findViewById(R.id.textTitle);
        title.setText("Thong bao");
        tvMess = view.findViewById(R.id.textMessage);
        tvMess.setText(message);

        ((TextView) view.findViewById(R.id.btnYes)).setText(getResources().getString(R.string.Yes));
        ((TextView) view.findViewById(R.id.btnNo)).setText(getResources().getString(R.string.No));

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btnYes).setOnClickListener(v -> {
            alertDialog.dismiss();
            dataBaseHomeOdoo.updateContact(url, db, password, uid, contact);

        });
        view.findViewById(R.id.btnNo).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    private void showDialogAdd(String message, Contact contact,String url,String db,String password, int uid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailContactActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(DetailContactActivity.this).inflate(
                R.layout.layout_success_dialog,
                findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        title = view.findViewById(R.id.textTitle);
        title.setText("Thong bao");
        tvMess = view.findViewById(R.id.textMessage);
        tvMess.setText(message);

        ((TextView) view.findViewById(R.id.btnYes)).setText(getResources().getString(R.string.Yes));
        ((TextView) view.findViewById(R.id.btnNo)).setText(getResources().getString(R.string.No));

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btnYes).setOnClickListener(v -> {
            alertDialog.dismiss();
            dataBaseHomeOdoo.addContact(url, db, password, uid, contact);
            //showSuccessDialog("Them thanh cong");

        });
        view.findViewById(R.id.btnNo).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
