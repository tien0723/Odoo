package tdc.edu.vn.myodoo.Activity;


import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;




import androidx.fragment.app.FragmentTransaction;



import tdc.edu.vn.myodoo.Fragment.FragmentDetailContact;

import tdc.edu.vn.myodoo.R;


public class DetailContactActivity extends AppCompatActivity  {
    //tao so thu tu cho fragment
    private static final int FRAGMENT_DETAIL = 0;
    private static final int FRAGMENT_ADD = 1;
    //tao bien so sanh de lay fragment
    private int mCurrentFragment ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);

        //set fragment hien thi
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContact, new FragmentDetailContact());
        transaction.commit();

    }


}
