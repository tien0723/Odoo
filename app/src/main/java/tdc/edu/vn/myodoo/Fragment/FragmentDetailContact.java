package tdc.edu.vn.myodoo.Fragment;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import tdc.edu.vn.myodoo.Handle.BitmapUtils;
import tdc.edu.vn.myodoo.R;
import tdc.edu.vn.myodoo.Util.OdooUtil;

public class FragmentDetailContact extends Fragment implements View.OnClickListener {
    ImageView imageBackgroundUser;
    TextView tvWebsite, tvEmail, tvPhone, tvMobile, tvCountry, tvInternalNote,tvUserName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_contact, container, false);

        tvUserName = view.findViewById(R.id.tvUserName);
        imageBackgroundUser = view.findViewById(R.id.imageBackgroundUser);
        tvWebsite = view.findViewById(R.id.tvWebsite);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvMobile = view.findViewById(R.id.tvMobile);
        tvCountry = view.findViewById(R.id.tvCountry);
        tvInternalNote = view.findViewById(R.id.tvInternalNote);
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
        tvEmail.setText(email);
        tvWebsite.setText(website);
        tvPhone.setText(phone);
        tvMobile.setText(mobile);
        tvEmail.setOnClickListener(this);
        tvWebsite.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        tvMobile.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvEmail:
                String dialEmail = "mailto:" + tvEmail.getText().toString();
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(dialEmail)));
                break;
            case R.id.tvPhone:
                String dial = "tel:" + tvPhone.getText().toString();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                break;
            case R.id.tvMobile:
                String dial1 = "tel:" + tvMobile.getText().toString();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial1)));
                break;
            case R.id.tvWebsite:
                String wed = tvWebsite.getText().toString();
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH );
                intent.putExtra(SearchManager.QUERY, wed);
                startActivity(intent);
                break;
        }
    }
}
