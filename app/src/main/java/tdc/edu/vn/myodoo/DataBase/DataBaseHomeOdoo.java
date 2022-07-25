package tdc.edu.vn.myodoo.DataBase;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
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
    public Object listContact(String url, String db, int user_id, String password, String objectModel) {
        Map<String, Object> fields = new HashMap() {{
            put("fields", asList("image_128", "name", "city",
                    "email", "website", "phone", "mobile", "street",
                    "street2", "zip", "is_company","company_id","parent_id"));//3
        }};
        //InternalNote,"country_id","company_id"
        XmlRpcClient models = Model(url);
        Object result = null;
        try {
            result = models.execute("execute_kw", asList(db, user_id, password, objectModel, "search_read", emptyList(), fields));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return result;
    }

    //lay thong tin user dang nhap
    public Object InfoUser(String url, String db, String password, int uid) {
        XmlRpcClient models = Model(url);
        Object ids = null;
        try {
            ids = models.execute(
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
    private XmlRpcClient Model(String url) {
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

    //Add
    public int addContact(String url, String db, String password, int uid, Contact contact){
        XmlRpcClient models = Model(url);
        int id = 0;
        try {
            id = (Integer) models.execute("execute_kw", asList(
                    db, uid, password,
                    "res.partner", "create",
                    asList(new HashMap() {{
                               put("image_1920", contact.getImage_1920());
                               put("name", contact.getName());
                               put("street", contact.getStreet());
                               put("street2", contact.getStreet2());
                               put("zip", contact.getZip());
                               put("city", contact.getCity());
                               put("email", contact.getEmail());
                               put("website", contact.getWebsite());
                               put("phone", contact.getPhone());
                               put("mobile", contact.getMobile());
                               put("is_company", contact.getIs_company());
                               put("parent_id", contact.getParent_id());
                           }}
                    )
            ));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return id;
    }

    //Update
    public Object updateContact(String url, String db, String password, int uid, Contact contact) {
        XmlRpcClient models = Model(url);
        Object update = null;
        try {
            models.execute("execute_kw", asList(
                    db, uid, password,
                    "res.partner", "write",
                    asList(
                            asList(contact.getId()),
                            new HashMap() {{
                                put("image_1920", contact.getImage_1920());
                                put("name", contact.getName());
                                put("street", contact.getStreet());
                                put("street2", contact.getStreet2());
                                put("zip", contact.getZip());
                                put("city", contact.getCity());
                                put("email", contact.getEmail());
                                put("website", contact.getWebsite());
                                put("phone", contact.getPhone());
                                put("mobile", contact.getMobile());
                                put("is_company", contact.getIs_company());
                                put("parent_id", contact.getParent_id());

                            }}
                    )
            ));
            // get record name after having changed it
            update = asList((Object[]) models.execute("execute_kw", asList(
                    db, uid, password,
                    "res.partner", "name_get",
                    asList(asList(contact.getId()))
            )));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return update;
    }
    //lay danh sach is_company = true
    public Object getIsCompany(String url ,String db,String password,int uid){
        XmlRpcClient models = Model(url);
        Object company = null;
        try {
            company = models.execute("execute_kw", asList(
                    db, 8, password,
                    "res.partner", "search_read",
                    asList(asList(
                            asList("is_company", "=", true))),
                    new HashMap() {{
                        put("fields", asList("name"));

                    }}
            ));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }

        return company;
    }
}
