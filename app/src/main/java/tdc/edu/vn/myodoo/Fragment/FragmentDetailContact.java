package tdc.edu.vn.myodoo.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;

import tdc.edu.vn.myodoo.DataBase.DataBaseHomeOdoo;
import tdc.edu.vn.myodoo.Handle.BitmapUtils;
import tdc.edu.vn.myodoo.Model.Contact;
import tdc.edu.vn.myodoo.R;


public class FragmentDetailContact extends Fragment {
    //khoi tao
    ImageView imageBackgroundUser, imageCamera, imageEdit;
    TextView tvUserName;
    EditText edtName, edtCountry, edtEmail, edtWebsite, edtPhone, edtMobile, edtStreet,
            edtStreet2, edtZip, edtInternalNote, edtCity;
    CheckBox checkBoxIsCompany;
    Bitmap bitmap;
    ArrayList<String> listCompany;
    Spinner spnCompany;
    private Contact contact;
    int id;
    String image123;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_contact, container, false);
        //Anh xa du lieu
        imageCamera = view.findViewById(R.id.imageCamera);
       // imageEdit = view.findViewById(R.id.imageEdit);
        tvUserName = view.findViewById(R.id.tvUserName);
        imageBackgroundUser = view.findViewById(R.id.imageBackgroundUser);
        edtName = view.findViewById(R.id.edtName);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtMobile = view.findViewById(R.id.edtMobile);
        edtStreet = view.findViewById(R.id.edtStreet);
        edtStreet2 = view.findViewById(R.id.edtStreet2);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtCountry = view.findViewById(R.id.edtCountry);
        edtWebsite = view.findViewById(R.id.edtWebsite);
        edtCity = view.findViewById(R.id.edtCity);
        edtZip = view.findViewById(R.id.edtZip);
        edtInternalNote = view.findViewById(R.id.edtInternalNote);
        checkBoxIsCompany = view.findViewById(R.id.checkboxCompany);
        spnCompany = view.findViewById(R.id.spnCompany);
        ////////////
        getInfo();
        //lay hinh anh tu intent
        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), intent.getData());
                                //setBitmap imageview
                                imageBackgroundUser.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        ////////////////////
        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                //Set type
                intent.setType("image/*");

                //Lauch Intent
                resultLauncher.launch(intent);
            }
        });

        return view;
    }

    //lay thong tin tu ContactFragment
    public void getInfo() {
        //get intent
        Intent intent = getActivity().getIntent();
         id = intent.getIntExtra("id",1);
        String name = intent.getStringExtra("username");
         image123 = intent.getStringExtra("image_128");
        String street = intent.getStringExtra("street");
        String street2 = intent.getStringExtra("street2");
        String country = intent.getStringExtra("country");
        String email = intent.getStringExtra("email");
        String website = intent.getStringExtra("website");
        String phone = intent.getStringExtra("phone");
        String mobile = intent.getStringExtra("mobile");
        String zip = intent.getStringExtra("zip");
        String city = intent.getStringExtra("city");
        Boolean is_company = intent.getBooleanExtra("is_company",false);
        listCompany = intent.getStringArrayListExtra("company");
        String company = intent.getStringExtra("parent_id");
        ////////////////////////////////////////
        tvUserName.setText(name);
        imageBackgroundUser.setImageBitmap(BitmapUtils.getBitmapImage(getActivity(), image123));
        edtName.setText(name);
        edtStreet.setText(street);
        edtStreet2.setText(street2);
        edtCountry.setText(country);
        edtEmail.setText(email);
        edtWebsite.setText(website);
        edtPhone.setText(phone);
        edtMobile.setText(mobile);
        edtZip.setText(zip);
        edtCity.setText(city);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,listCompany);
        spnCompany.setAdapter(adapter);
      //  Log.d("TAG", "getList: "+listCompany.toArray().length);
      //  Log.d("TAG", "getList: "+company);
        for (int i=0;i<listCompany.toArray().length;i++){
         Log.d("TAG", "abc: "+i);
            if(listCompany.get(i).equals(company)){
              //  Log.d("TAG", "deef: "+i);
                spnCompany.setSelection(i);
            }
        }


        if (is_company == true){
            checkBoxIsCompany.setChecked(true);
        }else {
            checkBoxIsCompany.setChecked(false);
        }

    }
    public Contact getContactEdit() {
        if (bitmap != null) {
            String image = BitmapUtils.conVert(bitmap);
            Log.d("TAG", "imageBitmap1: " + image);

            contact = new Contact(
                    edtCity.getText().toString(),
                    edtName.getText().toString(),
                    edtEmail.getText().toString(),
                    image,
                    edtWebsite.getText().toString(),
                    edtPhone.getText().toString(),
                    edtMobile.getText().toString(),
                    edtZip.getText().toString(),
                    edtStreet.getText().toString(),
                    edtStreet2.getText().toString(), id,
                    checkBoxIsCompany.isChecked());
        } else {
            // String image1 = BitmapUtils.conVert(image123);
            Log.d("TAG", "imageBitmap2: " + image123);
             contact = new Contact(
                    edtCity.getText().toString(),
                    edtName.getText().toString(),
                    edtEmail.getText().toString(),
                    image123,
                    edtWebsite.getText().toString(),
                    edtPhone.getText().toString(),
                    edtMobile.getText().toString(),
                    edtZip.getText().toString(),
                    edtStreet.getText().toString(),
                    edtStreet2.getText().toString(), id,
                     checkBoxIsCompany.isChecked());

        }
        return contact;
    }



    //Xu ly su kien click chuot
    //   @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.tvEmail:
//                String dialEmail = "mailto:" + tvEmail.getText().toString();
//                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(dialEmail)));
//                break;
//            case R.id.tvPhone:
//                String dial = "tel:" + tvPhone.getText().toString();
//                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
//                break;
//            case R.id.tvMobile:
//                String dial1 = "tel:" + tvMobile.getText().toString();
//                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial1)));
//                break;
//            case R.id.tvWebsite:
//                String wed = tvWebsite.getText().toString();
//                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH );
//                intent.putExtra(SearchManager.QUERY, wed);
//                startActivity(intent);
//                break;
//        }
//    }

}
