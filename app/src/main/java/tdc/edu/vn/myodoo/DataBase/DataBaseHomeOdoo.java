package tdc.edu.vn.myodoo.DataBase;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import tdc.edu.vn.myodoo.Model.Contact;

public class DataBaseHomeOdoo {
    DataBaseLoginOdoo dataBaseLoginOdoo = new DataBaseLoginOdoo();
    // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là Contact)
    Moshi moshi = new Moshi.Builder().build();
    Type usersType = Types.newParameterizedType(List.class, Contact.class);
    JsonAdapter<List<Contact>> jsonAdapter = moshi.adapter(usersType);

    //lay ds contact
    public List<Contact> listContact(String url,String db,String password, int uid) {
        String serverUrl = dataBaseLoginOdoo.createServerURL(url);
        Object f = null;
        //lay model
         XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                try {
                    setServerURL(new URL(String.format("%s/xmlrpc/2/object", serverUrl)));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }});
        }};
        //lay du lieu trong model
        try {
            f = asList((Object[]) models.execute("execute_kw", asList(
                    db, uid, password,
                    "res.partner", "search_read",
                    emptyList(),
                    new HashMap() {{
                        put("fields", asList("image_128", "name", "city", "email", "id"));
                    }}
            )));
        } catch (XmlRpcException ex) {
            ex.printStackTrace();
        }
        //chuyen kieu object thanh contact
        final List<Contact> contacts = jsonAdapter.fromJsonValue(f);
        Log.d("TAG", "contactf: " + contacts);
        return contacts;
    }
}
