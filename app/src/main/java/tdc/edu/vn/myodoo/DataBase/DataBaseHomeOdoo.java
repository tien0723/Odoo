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
import java.util.Map;

import tdc.edu.vn.myodoo.Model.Contact;

public class DataBaseHomeOdoo {
    //khai bao thu vien xml
    final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
    final XmlRpcClient client = new XmlRpcClient();
    DataBaseLoginOdoo dataBaseLoginOdoo = new DataBaseLoginOdoo();
    // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là Contact)
//    Moshi moshi = new Moshi.Builder().build();
//    Type usersType = Types.newParameterizedType(List.class, Contact.class);
//    JsonAdapter<List<Contact>> jsonAdapter = moshi.adapter(usersType);

    //lay ds contact
//    public List<Contact> listContact(String url,String db,String password, int uid) {
//        String serverUrl = dataBaseLoginOdoo.createServerURL(url);
//        Object f = null;
//        //lay model
//         XmlRpcClient models = new XmlRpcClient() {{
//            setConfig(new XmlRpcClientConfigImpl() {{
//                try {
//                    setServerURL(new URL(String.format("%s/xmlrpc/2/object", serverUrl)));
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//            }});
//        }};
//        //lay du lieu trong model
//        try {
//            f = asList((Object[]) models.execute("execute_kw", asList(
//                    db, uid, password,
//                    "res.partner", "search_read",
//                    emptyList(),
//                    new HashMap() {{
//                        put("fields", asList("image_128", "name", "city", "email", "id"));
//                    }}
//            )));
//        } catch (XmlRpcException ex) {
//            ex.printStackTrace();
//        }
//        //chuyen kieu object thanh contact
//        final List<Contact> contacts = jsonAdapter.fromJsonValue(f);
//        Log.d("TAG", "contactf: " + contacts);
//        return contacts;
//    }
    //Lay danh sach contact tu csdl
    public Object listContact(String url,String db, int user_id, String password, String objectModel) {
       Map<String,Object> fields = new HashMap () {{put ("fields", asList ("image_128", "name", "city",
               "email","website","phone","mobile", "street","street2","zip","is_company")); }};
       //InternalNote,"country_id","company_id"
       XmlRpcClient models= Model(url);
        Object result = null;
        try {
            result = models.execute("execute_kw", asList(db, user_id, password, objectModel, "search_read",emptyList(), fields));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return result;
    }
    //lay thong tin user dang nhap
    public Object InfoUser(String url,String db,String password,int uid){
       XmlRpcClient models= Model(url);
        Object ids = null;
        try {
           ids =  models.execute(
                    "execute_kw", asList(
                            db, uid, password,
                            "res.users", "search_read",
                            asList(asList(
                                    asList("id", "=", uid))),
                            new HashMap() {{
                                put("fields", asList("name", "email", "image_128"));
                            }}));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }

        return ids;
    }
    //lay model
    private XmlRpcClient Model(String url){
        String serverUrl = dataBaseLoginOdoo.createServerURL(url);
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
        return models;
    }
}
