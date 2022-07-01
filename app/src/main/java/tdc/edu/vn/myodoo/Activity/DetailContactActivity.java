package tdc.edu.vn.myodoo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import tdc.edu.vn.myodoo.Fragment.FragmentDetailContact;
import tdc.edu.vn.myodoo.R;

public class DetailContactActivity extends AppCompatActivity {
    ImageView imgBack;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContactDetail, new FragmentDetailContact());
        transaction.commit();
        setControl();
        setEvent();
    }

    public void setEvent() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailContactActivity.this, HomeActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    public void setControl() {
        drawerLayout = findViewById(R.id.drawerLayoutDetailContact);
        imgBack = findViewById(R.id.imageBack);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }
}
