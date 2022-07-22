package tdc.edu.vn.myodoo.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;

import tdc.edu.vn.myodoo.DataBase.DataBaseHomeOdoo;
import tdc.edu.vn.myodoo.Handle.BitmapUtils;
import tdc.edu.vn.myodoo.Model.Contact;
import tdc.edu.vn.myodoo.R;

public class FragmentAddContact extends Fragment {
    ImageView imageBackgroundUser, imageCamera;
    TextView tvUserName,tvSave;
    EditText edtName, edtCountry, edtEmail, edtWebsite, edtPhone, edtMobile, edtStreet,
            edtStreet2, edtZip, edtInternalNote, edtCity;
    CheckBox checkBoxIsCompany;
    Bitmap bitmap;
    String url,db,password;
    int uid,id;
    private Contact contact;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact,container,false);
        imageCamera = view.findViewById(R.id.imageCamera);
        tvSave= view.findViewById(R.id.tvSave);
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
       Bundle bundle = getActivity().getIntent().getExtras();

         if(bundle!=null){
             url = bundle.getString("url");
             db = bundle.getString("db");
             password = bundle.getString("password");
             uid = bundle.getInt("uid",0);
         }

        Log.d("TAG", "getInfo: "+url+db+password+uid);

//        tvSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(bitmap!=null){
//                    String image = BitmapUtils.conVert(bitmap);
//                    Log.d("TAG", "imageBitmap: "+image);
//
//                    Contact contact = new Contact(
//                            edtCity.getText().toString(),
//                            edtName.getText().toString(),
//                            edtEmail.getText().toString(),
//                            image,
//                            edtWebsite.getText().toString(),
//                            edtPhone.getText().toString(),
//                            edtMobile.getText().toString(),
//                            edtZip.getText().toString(),
//                            edtStreet.getText().toString(),
//                            edtStreet2.getText().toString());
//                      if(  dataBaseHomeOdoo.addContact(url,db,password,uid,contact)!=0){
//                          dataBaseHomeOdoo.addContact(url,db,password,uid,contact);
//                      }else {
//                          Log.d("TAG", "Loi them contact : ");
//                      }
//                }else {
//                    Toast.makeText(getActivity(),"Loi chua chon anh",Toast.LENGTH_LONG).show();
//                    Log.d("TAG", "loi chua chon anh: ");
//                }
//            }
//        });
    }
    public Contact getContactAdd() {
        String image= "";
        if (bitmap != null) {
             image = BitmapUtils.conVert(bitmap);
            Log.d("TAG", "imageBitmap1: " + image);

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
                checkBoxIsCompany.isChecked()
                );
        return contact;
    }
}
