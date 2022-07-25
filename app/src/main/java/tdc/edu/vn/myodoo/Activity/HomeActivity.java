package tdc.edu.vn.myodoo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Map;

import tdc.edu.vn.myodoo.DataBase.DataBaseHomeOdoo;
import tdc.edu.vn.myodoo.Fragment.ContactFragment;
import tdc.edu.vn.myodoo.Fragment.FragmentAddContact;
import tdc.edu.vn.myodoo.Fragment.MessegesFragment;
import tdc.edu.vn.myodoo.Fragment.SettingFragment;
import tdc.edu.vn.myodoo.Handle.BitmapUtils;
import tdc.edu.vn.myodoo.Model.Contact;
import tdc.edu.vn.myodoo.R;
import tdc.edu.vn.myodoo.Util.Many2One;
import tdc.edu.vn.myodoo.Util.OdooUtil;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DataBaseHomeOdoo dataBaseHomeOdoo = new DataBaseHomeOdoo();;
    DrawerLayout drawerLayout;
    //tao so thu tu cho fragment
    private static final int FRAGMENT_MESSAGES = 0;
    private static final int FRAGMENT_CONTACT = 1;
    private static final int FRAGMENT_SETTING = 2;
    //tao bien so sanh de lay fragment
    private int mCurrentFragment = FRAGMENT_CONTACT;
    FloatingActionButton btnAdd;
    ArrayList<String> listCompany = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //setControl (anh xa du lieu)
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        TextView tvName = navigationView.getHeaderView(0).findViewById(R.id.tvNameNavigation);
        TextView tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tvEmailNavigation);
        ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.imgUserNavigation);
        Toolbar toolbar = findViewById(R.id.toolBar);
        btnAdd = findViewById(R.id.btnAdd);
        //Su ly thanh toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //xu ly su kien thanh navigation
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new ContactFragment());
        navigationView.getMenu().findItem(R.id.menuContact).setChecked(true);

        //lay du lieu internt truyen tu login activity
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String db = intent.getStringExtra("db");
        String password = intent.getStringExtra("password");
        int uid = intent.getIntExtra("uid",0);


        //hien thi thong tin user
        Object[] result = (Object[]) dataBaseHomeOdoo.InfoUser(url,db,password,uid);
        if(result.length >0){
            for (Object object: result){
                String name = OdooUtil.getString((Map<String, Object>) object,"name");
                String email = OdooUtil.getString((Map<String, Object>) object,"email");
                String image = OdooUtil.getString((Map<String, Object>) object,"image_1920");
               // Log.d("TAG", "image: "+image);
                if(image.equals("")){
                    imageView.setImageResource(R.drawable.user_defaul);
                }else {
                    imageView.setImageBitmap(BitmapUtils.getBitmapImage(this,image));
                }
                tvEmail.setText(email);
                tvName.setText(name);
            }
        }

        //lay danh sach contact
        String model = "res.partner";
        Object result1 = dataBaseHomeOdoo.listContact(url,db,uid,password,model);
        Object[] objects = (Object[]) result1;

        if (objects.length > 0) {
            for (Object object : objects) {
                String name= OdooUtil.getString((Map<String, Object>) object, "name");
                Boolean is_company=OdooUtil.getBoolean((Map<String, Object>) object, "is_company");//2
                if(is_company == true){
                    listCompany.add(name);
                }
            }
        }
        //nut button add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(HomeActivity.this, DetailContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",url);
                bundle.putInt("uid",uid);
                bundle.putString("password",password);
                bundle.putString("db",db);
                bundle.putStringArrayList("listCompany",listCompany);
                intent1.putExtras(bundle);
               startActivity(intent1);
            }
        });

    }
    //xu ly thanh navigation de chon fragment
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuMessages:
                if (mCurrentFragment != FRAGMENT_MESSAGES) {
                    replaceFragment(new MessegesFragment());
                    mCurrentFragment = FRAGMENT_MESSAGES;
                    btnAdd.setVisibility(View.GONE);
                }
                break;
            case R.id.menuContact:
                if (mCurrentFragment != FRAGMENT_CONTACT) {
                    replaceFragment(new ContactFragment());
                    mCurrentFragment = FRAGMENT_CONTACT;
                    btnAdd.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.menuSetting:
                if (mCurrentFragment != FRAGMENT_SETTING) {
                    replaceFragment(new SettingFragment());
                    mCurrentFragment = FRAGMENT_SETTING;
                }
                break;

            case R.id.menuSupport:
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //xu ly su kien back
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    //lay id layout fragment
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}
