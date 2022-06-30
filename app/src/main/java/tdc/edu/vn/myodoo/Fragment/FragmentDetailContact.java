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

import tdc.edu.vn.myodoo.R;

public class FragmentDetailContact extends Fragment {
    TextView tvUserName;
    LinearLayout imageBackgroundUser;
    EditText extWebsite, edtEmail, edtPhone, edtMobile, edtCountry, edtInternalNote;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_contact, container, false);

        tvUserName = view.findViewById(R.id.tvUserName);
        imageBackgroundUser = view.findViewById(R.id.imageBackgroundUser);
        extWebsite = view.findViewById(R.id.edtWebsite);
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
        tvUserName.setText(user);
    }
}
