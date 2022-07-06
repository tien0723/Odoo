package tdc.edu.vn.myodoo.Activity;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import tdc.edu.vn.myodoo.R;

public class EditContactActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageCamera;
    TextInputEditText edtName;
    TextView tvSave,tvCancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        setControl();

    }
    public void setControl(){
        imageCamera = findViewById(R.id.imageCamera);
        tvCancel = findViewById(R.id.tvCancel);
        edtName = findViewById(R.id.edtName);
        tvSave = findViewById(R.id.tvSave);
        tvSave.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        imageCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageCamera:
                Intent Intent3=new   Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(Intent3);
                break;
            case R.id.tvSave:
                Log.d("TAG", "name: "+edtName.getText().toString());
            case R.id.tvCancel:
                Log.d("TAG", "name: "+edtName.getText().toString());
        }
    }
}
