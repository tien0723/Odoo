package tdc.edu.vn.myodoo.DataBase;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import android.util.Log;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DataBaseOdoo {
    final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
    final XmlRpcClientConfigImpl common_config1 = new XmlRpcClientConfigImpl();
    final XmlRpcClient client = new XmlRpcClient();

    public int Uid(String url, String userName, String password, String db) {
        String serverURL = createServerURL(url);
        int a = 0;
        try {
            common_config1.setServerURL(new URL(String.format("%s/xmlrpc/2/common", serverURL)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            a = (int) client.execute(common_config1, "authenticate", asList(db, userName, password, emptyMap()));
            Log.d("TAG", "aaaaaaaaa: " + a);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

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

    private String createServerURL(String server_url) {
        StringBuilder serverURL = new StringBuilder();
        if (!server_url.contains("http://") && !server_url.contains("https://")) {
            serverURL.append("https://");
        }
        serverURL.append(server_url);

        return serverURL.toString();
    }
}
