package tdc.edu.vn.myodoo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import tdc.edu.vn.myodoo.Handle.BitmapUtils;
import tdc.edu.vn.myodoo.R;
import tdc.edu.vn.myodoo.Util.OdooUtil;

public class FragmentDetailContact extends Fragment {
    TextView tvUserName;
    ImageView imageBackgroundUser;
    EditText edtWebsite, edtEmail, edtPhone, edtMobile, edtCountry, edtInternalNote;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_contact, container, false);

        tvUserName = view.findViewById(R.id.tvUserName);
        imageBackgroundUser = view.findViewById(R.id.imageBackgroundUser);
        edtWebsite = view.findViewById(R.id.edtWebsite);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtMobile = view.findViewById(R.id.edtMobile);
        edtCountry = view.findViewById(R.id.edtCountry);
        edtInternalNote = view.findViewById(R.id.edtInternalNote);
        getInfo();
        return view;
    }
    public void getInfo(){
        Intent intent = getActivity().getIntent();
        String user=intent.getStringExtra("username");
        String email=intent.getStringExtra("email");
        String image=intent.getStringExtra("image_128");
        String website=intent.getStringExtra("website");
        String phone=intent.getStringExtra("phone");
        String mobile=intent.getStringExtra("mobile");
        tvUserName.setText(user);
        imageBackgroundUser.setImageBitmap(BitmapUtils.getBitmapImage(getActivity(),image));
        edtEmail.setText(email);
        edtWebsite.setText(website);
        edtPhone.setText(phone);
        edtMobile.setText(mobile);
    }
}
