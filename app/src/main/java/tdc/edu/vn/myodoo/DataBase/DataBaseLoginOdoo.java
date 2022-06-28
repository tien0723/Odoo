package tdc.edu.vn.myodoo.DataBase;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tdc.edu.vn.myodoo.Activity.HomeActivity;
import tdc.edu.vn.myodoo.Adapter.AdapterContact;
import tdc.edu.vn.myodoo.Model.Contact;
import tdc.edu.vn.myodoo.R;


public class DataBaseLoginOdoo {
    //khai bao thu vien xml
    final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
    final XmlRpcClient client = new XmlRpcClient();

    //lấy user id đăng nhập
    public int Uid(String url, String userName, String password, String db) {
        String serverURL = createServerURL(url);
        int uid = 0;
        try {
            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", serverURL)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            uid = (int) client.execute(common_config, "authenticate", asList(db, userName, password, emptyMap()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uid;
    }

    //lấy ds database
    public List<String> dataBase(String url, List<String> listDataBase) {
        String serverURL = createServerURL(url);
        Object[] h;
        try {
            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/db", serverURL)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            h = (Object[]) client.execute(common_config, "list", emptyList());
            //chuyen mang oject thanh mang string
            String common[] = new String[h.length];

            for (int i = 0; i < h.length; i++) {
                common[i] = (String) h[i];
                listDataBase.add(common[i]);

            }
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return listDataBase;
    }

    //tạo đường dẫn url
    public String createServerURL(String server_url) {
        StringBuilder serverURL = new StringBuilder();
        if (!server_url.contains("http://") && !server_url.contains("https://")) {
            serverURL.append("https://");
        }
        serverURL.append(server_url);

        return serverURL.toString();
    }


}
