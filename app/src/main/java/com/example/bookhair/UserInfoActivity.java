package com.example.bookhair;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bookhair.databinding.ActivityUserInfoBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {
    private TextInputLayout layoutName, layoutLastName, layoutPhone, layoutAddress;
    private TextInputEditText txtName, txtLastName, txtPhone, txtAddress;
    private Button btn_continue;
    private TextView txtSelectedPhoto;
    private CircleImageView circleImageView;
    private static final int GALLERY_ADD_PROFILE = 1;
    private Bitmap bitmap = null;
    private SharedPreferences userPref;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_info);
        init();
    }
    private void init(){
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        layoutName = findViewById(R.id.txtLayoutNameUserInfo);
        layoutLastName = findViewById(R.id.txtLayoutLastNameUserInfo);
        layoutPhone = findViewById(R.id.txtLayoutPhoneUserInfo);
        layoutAddress = findViewById(R.id.txtLayoutAddressUserInfo);
        txtName = findViewById(R.id.txtNameUserInfo);
        txtLastName = findViewById(R.id.txtLastNameUserInfo);
        txtPhone = findViewById(R.id.txtPhoneUserInfo);
        txtAddress = findViewById(R.id.txtAddressUserInfo);
        txtSelectedPhoto = findViewById(R.id.txtSelectPhoto);
        btn_continue = findViewById(R.id.btn_Continue);
        circleImageView = findViewById(R.id.imgUserInfo);

        //pick photo from gallery
        txtSelectedPhoto.setOnClickListener(v->{
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i, GALLERY_ADD_PROFILE);
        });

        btn_continue.setOnClickListener(v->{
            if (validate()){
                if(!isValidUsername(txtName.getText().toString()))
                {
                    Toast.makeText(getApplication().getBaseContext(), " Tên Họ Sai", Toast.LENGTH_SHORT).show();
                }
                if(!isValidUsername(txtLastName.getText().toString()))
                {
                    Toast.makeText(getApplication().getBaseContext(), " Tên  Sai", Toast.LENGTH_SHORT).show();

                }
                if(!isValidMobileNo(txtPhone.getText().toString()))
                {
                    Toast.makeText(getApplication().getBaseContext(), " Số Điện Thoại Sai", Toast.LENGTH_SHORT).show();
                }
                else if(isValidUsername(txtName.getText().toString()) && isValidUsername(txtLastName.getText().toString()) && isValidMobileNo(txtPhone.getText().toString()) )
                {
                    saveuserinfo();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_ADD_PROFILE && resultCode == RESULT_OK){
            Uri imgUri = data.getData();
            circleImageView.setImageURI(imgUri);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validate(){
        if (txtName.getText().toString().isEmpty()){
            layoutName.setErrorEnabled(true);
            layoutName.setError("Họ không được để trống");
            return false;
        }
        if (txtLastName.getText().toString().isEmpty()){
            layoutLastName.setErrorEnabled(true);
            layoutLastName.setError("Tên không được để trống");
            return false;
        }
        if (txtPhone.getText().toString().isEmpty()){
            layoutPhone.setErrorEnabled(true);
            layoutPhone.setError("Số điện thoại không được để trống");
            return false;
        }
        if (txtAddress.getText().toString().isEmpty()){
            layoutName.setErrorEnabled(true);
            layoutAddress.setError("Địa chỉ không được để trống");
            return false;
        }

        return true;
    }

    public static boolean isValidMobileNo(String s)
    {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // The number should be of 10 digits.

        // Creating a Pattern class object
        Pattern p = Pattern.compile("^\\d{10}$");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression for which
        // object of Matcher class is created
        Matcher m = p.matcher(s);

        // Returning boolean value
        return (m.matches());
    }
    public static boolean isValidUsername(String txt)
    {

        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();
    }
    private void saveuserinfo(){
        dialog.setMessage("Đang lưu thông tin");
        dialog.show();
        String name = txtName.getText().toString().trim();
        String lastname = txtLastName.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, API.SAVE_USER_INFO_REGISTER, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){

                    startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        },error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            // add token to header

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }

            // add param

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("name", name);
                map.put("lastname", lastname);
                map.put("phone", phone);
                map.put("address", address);
                map.put("photo",bitmapToString(bitmap));
                return  map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(UserInfoActivity.this);
        queue.add(request);
    }

    private String bitmapToString(Bitmap bitmap) {
        if (bitmap != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte [] array = byteArrayOutputStream.toByteArray();
            return android.util.Base64.encodeToString(array, Base64.DEFAULT);

        }
        return "";
    }

}