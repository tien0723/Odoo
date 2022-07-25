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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import tdc.edu.vn.myodoo.DataBase.DataBaseHomeOdoo;
import tdc.edu.vn.myodoo.Handle.BitmapUtils;
import tdc.edu.vn.myodoo.Model.Company;
import tdc.edu.vn.myodoo.Model.Contact;
import tdc.edu.vn.myodoo.R;
import tdc.edu.vn.myodoo.Util.OdooUtil;

public class FragmentAddContact extends Fragment {
    //khoi tao
    ImageView imageBackgroundUser, imageCamera;
    TextView tvUserName, tvSave;
    EditText edtName, edtCountry, edtEmail, edtWebsite, edtPhone, edtMobile, edtStreet,
            edtStreet2, edtZip, edtInternalNote, edtCity;
    CheckBox checkBoxIsCompany;
    Spinner spnCompany;
    ArrayList<String> listCompany = new ArrayList<>();
    Bitmap bitmap;
    String url, db, password;
    int uid, id;
    private Contact contact;
    private  ArrayList<Company> listCOMPANY = new ArrayList<>();
    private DataBaseHomeOdoo dataBaseHomeOdoo = new DataBaseHomeOdoo();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        //anh xa du lieu
        imageCamera = view.findViewById(R.id.imageCamera);
        tvSave = view.findViewById(R.id.tvSave);
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
        getInfo();
        //Xu ly nut camera lay hinh anh
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
        //xu ly su kien click checkbox
        checkBoxIsCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxIsCompany.isChecked()==true){
                    spnCompany.setVisibility(View.GONE);
                }else {
                    spnCompany.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    //lay thong tin tu ContactFragment
    public void getInfo() {
        //get intent
        Intent intent = getActivity().getIntent();

        url = intent.getStringExtra("url");
        db = intent.getStringExtra("db");
        password = intent.getStringExtra("password");
        uid = intent.getIntExtra("uid", 0);
        listCompany = intent.getStringArrayListExtra("listCompany");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listCompany);
        spnCompany.setAdapter(adapter);

        //lay danh sach contact tu database
        Object result = dataBaseHomeOdoo.getIsCompany(url,db,password,uid);
        Object[] objects = (Object[]) result;

        if (objects.length > 0) {
            for (Object object : objects) {
                String name1= OdooUtil.getString((Map<String, Object>) object, "name");
                int id1= OdooUtil.getInteger((Map<String, Object>) object, "id");
                Company company =new Company(id1,name1);
                listCOMPANY.add(company);
            }
        }

    }
    //lay danh sach ContactADd
    public Contact getContactAdd() {
        String image = "";
        if (bitmap != null) {
            image = BitmapUtils.conVert(bitmap);
            //Log.d("TAG", "imageBitmap1: " + image);
        }
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
                checkBoxIsCompany.isChecked(),
                spnCompany.getSelectedItem().toString()
        );
        return contact;
    }
}
