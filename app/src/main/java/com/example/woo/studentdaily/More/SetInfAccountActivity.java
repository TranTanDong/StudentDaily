package com.example.woo.studentdaily.More;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.Popup;
import com.example.woo.studentdaily.More.Model.User;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetInfAccountActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnUpdateInf;
    private RadioGroup radioGroupSet;
    private RadioButton radioMale, radioFemale;
    private EditText edtNameSet;
    private TextView tvBirthdaySet, tvEmailSet;
    private CircleImageView imgImageSet;

    Bitmap bitmap =  null;
    String urlAvatar = "default";
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReferenceFromUrl("gs://studentdiary-e027b.appspot.com");

    private static final int REQUEST_WRITE_PERMISSION = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    private String gender = "";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_inf_account);
        addToolbar();
        addControls();
        requestPermission();
        addEvents();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void addControls() {
        btnUpdateInf    = findViewById(R.id.btn_update_inf);
        edtNameSet      = findViewById(R.id.edt_name_set);
        radioGroupSet   = findViewById(R.id.radio_group_set);
        radioFemale     = findViewById(R.id.radio_female_set);
        radioMale       = findViewById(R.id.radio_male_set);
        tvBirthdaySet   = findViewById(R.id.tv_birthday_set);
        tvEmailSet      = findViewById(R.id.tv_email_set);
        imgImageSet     = findViewById(R.id.img_user_set);
        bitmap          = ((BitmapDrawable) imgImageSet.getDrawable()).getBitmap();

        setInfUser();

    }

    private void setInfUser() {
        user = Common.getUser(getApplicationContext());
        edtNameSet.setText(user.getName());
        tvEmailSet.setText(user.getEmail());
        tvBirthdaySet.setText(Common.moveSlashTo(user.getBirthDay(), "-", "/"));
        if (!user.getGender().equals("1")){
            radioMale.setChecked(true);
            radioFemale.setChecked(false);
        }else {
            radioFemale.setChecked(true);
            radioMale.setChecked(false);
        }
        //Load hình
        Glide.with(getApplicationContext()).load(user.getImage())
                .apply(RequestOptions
                        .overrideOf(120, 120)
                        .placeholder(R.drawable.ic_account_circle)
                        .error(R.drawable.ic_account_circle)
                        .formatOf(DecodeFormat.PREFER_RGB_565)
                        .timeout(3000)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)).into(imgImageSet);
    }

    private void addEvents() {

        // Xử lý radio giới tính
        // 0 false nữ
        // 1 true nam
        radioGroupSet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                if (id == R.id.radio_male_set){
                    gender = "0";
                }else gender = "1";
            }
        });
        // Xử lý chọn ngày sinh
        tvBirthdaySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.processBirthDay(SetInfAccountActivity.this, tvBirthdaySet);
            }
        });

        btnUpdateInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Upload và lấy link avatar
                excecute();
            }
        });

    }

    private void updateDataUser(final String code, final String name, final String image, final String isFemale, final String birthDay) {
        Log.i("BUGLOL", code+name+image+isFemale+birthDay);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchUpdateUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SetInfAccountActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("UPDATE_USER", response+image);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SetInfAccountActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("u_code", code);
                hashMap.put("u_name", name);
                hashMap.put("u_image", image);
                hashMap.put("u_gender", isFemale);
                hashMap.put("u_birthday", Common.moveSlashTo(birthDay, "/", "-"));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri image = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(image, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            bitmap = BitmapFactory.decodeFile(filePath);
            imgImageSet.setImageBitmap(bitmap);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            addEvent();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            addEvent();
        }
    }

    private void addEvent() {
        imgImageSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, REQUEST_CODE_GALLERY);
            }
        });

    }

    private void excecute(){
        final Popup popup = new Popup(SetInfAccountActivity.this);
        popup.createLoadingDialog();
        popup.show();
        final StorageReference storageR = storageReference.child("User/" + Common.getUser(getApplicationContext()).getCode() + ".png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 5, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageR.putBytes(data);
        final Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return storageR.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    urlAvatar = downloadUri.toString();
                    SetInfAccountActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateDataUser(user.getCode(), edtNameSet.getText().toString().trim(), urlAvatar, gender, tvBirthdaySet.getText().toString());
                            User u1 = new User(user.getCode(), edtNameSet.getText().toString().trim(), urlAvatar, user.getEmail(), gender, Common.moveSlashTo(tvBirthdaySet.getText().toString(), "/", "-"));
                            Common.setCurrentUser(getApplicationContext(), u1);
                            finish();
                            popup.hide();
                        }
                    });
                } else {
                    Toast.makeText(SetInfAccountActivity.this, "Upload image error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Cập nhật thông tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
