package tdc.edu.vn.myodoo.Fragment;

import static java.util.Collections.emptyList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdc.edu.vn.myodoo.Activity.DetailContactActivity;
import tdc.edu.vn.myodoo.Activity.HomeActivity;
import tdc.edu.vn.myodoo.Adapter.AdapterContact;
import tdc.edu.vn.myodoo.DataBase.DataBaseHomeOdoo;
import tdc.edu.vn.myodoo.Model.Contact;
import tdc.edu.vn.myodoo.R;
import tdc.edu.vn.myodoo.Util.Many2One;
import tdc.edu.vn.myodoo.Util.OdooUtil;

public class ContactFragment extends Fragment {

    DataBaseHomeOdoo dataBaseHomeOdoo = new DataBaseHomeOdoo();
    List<Contact> contacts = new ArrayList<>();
    ListView lvContact;
    // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là Contact)
//    Moshi moshi = new Moshi.Builder().build();
//    Type usersType = Types.newParameterizedType(List.class, Contact.class);
//    JsonAdapter<List<Contact>> jsonAdapter = moshi.adapter(usersType);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact,container,false);
        //setControl
        lvContact = view.findViewById(R.id.lvContact);
        //lay du lieu intent
        Intent intent = getActivity().getIntent();
        String url = intent.getStringExtra("url");
        String db = intent.getStringExtra("db");
        String password = intent.getStringExtra("password");
        int uid = intent.getIntExtra("uid", 0);
        String model = "res.partner";

        //lay danh sach contact
        Object result = dataBaseHomeOdoo.listContact(url,db,uid,password,model);
        Object[] objects = (Object[]) result;
        ArrayList<String> listCompany = new ArrayList<>();
        if (objects.length > 0) {
            for (Object object : objects) {
                String image= OdooUtil.getString((Map<String, Object>) object, "image_128");
                String name= OdooUtil.getString((Map<String, Object>) object, "name");
                String city= OdooUtil.getString((Map<String, Object>) object, "city");
                String email = OdooUtil.getString((Map<String, Object>) object, "email");
                int id= OdooUtil.getInteger((Map<String, Object>) object, "id");
                String website_id =OdooUtil.getString((Map<String, Object>) object, "website");
                String phone = OdooUtil.getString((Map<String, Object>) object, "phone");
                String mobile=OdooUtil.getString((Map<String, Object>) object, "mobile");
                String zip =OdooUtil.getString((Map<String, Object>) object, "zip");
                String  street =OdooUtil.getString((Map<String, Object>) object, "street");
                String street2=OdooUtil.getString((Map<String, Object>) object, "street2");
                Boolean is_company=OdooUtil.getBoolean((Map<String, Object>) object, "is_company");
                String parent_id= Many2One.getMany2One((Map<String, Object>) object, "parent_id").getName();
                //2
                if(is_company == true){
                    listCompany.add(name);
                }
                Contact contact = new Contact(city,name,email,image,website_id,phone,mobile,zip,street,
                        street2,id,listCompany,is_company,parent_id);
                Log.d("TAG", "parent_id: "+contact.getParent_id());
                contacts.add(contact);
            }
        }















        //xu ly listview truyen du lieu sang   FragmentDetailContact

        lvContact.setAdapter(new AdapterContact(getContext(), R.layout.item_contact_layout, contacts));
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(getContext(), DetailContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username",contacts.get(i).getName());
                bundle.putString("image_128",contacts.get(i).getImage_1920());
                bundle.putString("email",contacts.get(i).getEmail());
                bundle.putString("website",contacts.get(i).getWebsite());
                bundle.putString("phone",contacts.get(i).getPhone());
                bundle.putString("mobile",contacts.get(i).getMobile());
                bundle.putString("zip",contacts.get(i).getZip());
                bundle.putString("street",contacts.get(i).getStreet());
                bundle.putString("street2",contacts.get(i).getStreet2());
                bundle.putBoolean("is_company",contacts.get(i).getIs_company());
                bundle.putString("parent_id",contacts.get(i).getParent_id());
                //1
                bundle.putString("city",contacts.get(i).getCity());
                bundle.putStringArrayList("company",contacts.get(i).getListCompany());
                bundle.putString("url",url);
                bundle.putInt("uid",uid);
                bundle.putString("password",password);
                bundle.putString("db",db);
                bundle.putInt("id",contacts.get(i).getId());
                bundle.putString("edit","edit");
              //  Log.d("TAG", "Image 142: "+contacts.get(i).getImage_1920());
                intent1.putExtras(bundle);

                startActivity(intent1);
            }
        });
        return  view;
    }
}
