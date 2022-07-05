package tdc.edu.vn.myodoo.Fragment;

import static java.util.Collections.emptyList;

import android.content.Intent;
import android.os.Bundle;
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
        lvContact = view.findViewById(R.id.lvContact);
        Intent intent = getActivity().getIntent();
        String url = intent.getStringExtra("url");
        String db = intent.getStringExtra("db");
        String password = intent.getStringExtra("password");
        int uid = intent.getIntExtra("uid", 0);
        String model = "res.partner";
      //  Map<String,List> fields = new HashMap() {{put ("fields", Arrays.asList("image_128", "name", "city", "email", "id")); }};

        Object result = dataBaseHomeOdoo.listContact(url,db,uid,password,model);

        Object[] objects = (Object[]) result;
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
                Contact contact = new Contact(city,name,email,image,website_id,phone,mobile,id);
                contacts.add(contact);
                //Log.d("TAG", "abc: "+contact);
            }
        }
        lvContact.setAdapter(new AdapterContact(getContext(), R.layout.item_contact_layout, contacts));
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(getContext(), DetailContactActivity.class);
                intent1.putExtra("username",contacts.get(i).getName());
                intent1.putExtra("image_128",contacts.get(i).getImage_128());
                intent1.putExtra("email",contacts.get(i).getEmail());
                intent1.putExtra("website",contacts.get(i).getWebsite());
                intent1.putExtra("phone",contacts.get(i).getPhone());
                intent1.putExtra("mobile",contacts.get(i).getMobile());
                startActivity(intent1);
            }
        });
        return  view;
    }
}
