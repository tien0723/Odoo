package tdc.edu.vn.myodoo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import tdc.edu.vn.myodoo.Handle.BitmapUtils;
import tdc.edu.vn.myodoo.Model.Contact;
import tdc.edu.vn.myodoo.R;


public class AdapterContact extends ArrayAdapter {
    private Context context;
    private int resource;
    private List<Contact> data;

    public AdapterContact(@NonNull Context context, int resource, List<Contact> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        Contact contact = data.get(position);
        ImageView imgConTact = convertView.findViewById(R.id.imgContact);

        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvDiaChi = convertView.findViewById(R.id.tvDiaChi);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);

        //lay anh bang picasso
//        String image_url_1920 =  base_url + "/web/image?" + "model=res.partner&id=" + contact.getId() + "&field=image_128&unique=06072022153053";
//        Picasso.get()
//                .load(String.valueOf(bitmap))
//                .placeholder(R.drawable.ic_baseline_person_outline_24)
//                .error(R.drawable.ic_baseline_error_24)
//                .fit()
//                .into(imgConTact);


        //lay anh bang bitmap
        //lay anh bang bitmap
        Bitmap bitmap = BitmapUtils.getBitmapImage(context, contact.getImage_128());
        imgConTact.setImageBitmap(bitmap);

        tvTen.setText( contact.getName());
        tvDiaChi.setText( contact.getEmail());
        tvEmail.setText(String.valueOf(contact.getId()));
        return convertView;
    }
}
