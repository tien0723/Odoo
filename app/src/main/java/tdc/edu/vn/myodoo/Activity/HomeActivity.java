package tdc.edu.vn.myodoo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.android.material.navigation.NavigationView;

import java.util.Map;

import tdc.edu.vn.myodoo.DataBase.DataBaseHomeOdoo;
import tdc.edu.vn.myodoo.Fragment.ContactFragment;
import tdc.edu.vn.myodoo.Fragment.MessegesFragment;
import tdc.edu.vn.myodoo.Fragment.SettingFragment;
import tdc.edu.vn.myodoo.Handle.BitmapUtils;
import tdc.edu.vn.myodoo.R;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        TextView tvName = navigationView.getHeaderView(0).findViewById(R.id.tvNameNavigation);
        TextView tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tvEmailNavigation);
        ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.imgUserNavigation);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new ContactFragment());
        navigationView.getMenu().findItem(R.id.menuContact).setChecked(true);


        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String db = intent.getStringExtra("db");
        String password = intent.getStringExtra("password");
        int uid = intent.getIntExtra("uid",0);


        Object[] result = (Object[]) dataBaseHomeOdoo.InfoUser(url,db,password,uid);
        if(result.length >0){
            for (Object object: result){
                String name = OdooUtil.getString((Map<String, Object>) object,"name");
                String email = OdooUtil.getString((Map<String, Object>) object,"email");
                String image = OdooUtil.getString((Map<String, Object>) object,"image_128");
                Log.d("TAG", "image: "+image);
                if(image.equals("")){
                    imageView.setImageResource(R.drawable.user_defaul);
                }else {
                    imageView.setImageBitmap(BitmapUtils.getBitmapImage(this,image));
                }
                tvEmail.setText(email);
                tvName.setText(name);
            }
        }
        Log.d("TAG", "onCreate: "+result);
    }


    //xu ly thanh navigation de chon fragment
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuMessages:
                if (mCurrentFragment != FRAGMENT_MESSAGES) {
                    replaceFragment(new MessegesFragment());
                    mCurrentFragment = FRAGMENT_MESSAGES;
                }
                break;
            case R.id.menuContact:
                if (mCurrentFragment != FRAGMENT_CONTACT) {
                    replaceFragment(new ContactFragment());
                    mCurrentFragment = FRAGMENT_CONTACT;
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

    //
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
