package tdc.edu.vn.myodoo.Activity;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import tdc.edu.vn.myodoo.Adapter.AdapterContact;
import tdc.edu.vn.myodoo.Model.Contact;
import tdc.edu.vn.myodoo.R;

public class HomeActivity extends AppCompatActivity {
    final XmlRpcClient client = new XmlRpcClient();
    final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();

    String url = "https://android.t4erp.cf",
            db = "bitnami_odoo",
            userName = "TienNM.19.TDC@t4Intership.cf",
            password = "123456aA@";
    ListView lvContact;

    AdapterContact adapterContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setControl();
        setEvent();
    }

    private void setEvent() {

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là Contact)
        Moshi moshi = new Moshi.Builder().build();
        Type usersType = Types.newParameterizedType(List.class, Contact.class);
        final JsonAdapter<List<Contact>> jsonAdapter = moshi.adapter(usersType);
        try {
            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                try {
                    setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }});
        }};

        Thread gfgThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //   Object a = client.execute(common_config, "version", emptyList());
                    int uid = (int) client.execute(common_config, "authenticate", asList(db, userName, password, emptyMap()));


                    Object f = asList((Object[]) models.execute("execute_kw", asList(
                            db, uid, password,
                            "res.partner", "search_read",
                            emptyList(),
                            new HashMap() {{
                                put("fields", asList("image_128","name", "city" ,"email","id"));
                            }}
                    )));

                    final List<Contact> contacts = jsonAdapter.fromJsonValue(f);
                    Log.d("TAG", "contact: "+contacts);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lvContact.setAdapter(new AdapterContact(HomeActivity.this, R.layout.item_contact_layout, contacts));
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        gfgThread.start();
    }


    private void setControl() {
        lvContact = findViewById(R.id.lvContact);
    }

}
