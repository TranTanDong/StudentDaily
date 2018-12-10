package com.example.woo.studentdaily.Diary;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Common.Popup;
import com.example.woo.studentdaily.More.EditInfAccountActivity;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddContentDiaryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtContent;
    private ImageView imgContent;

    private int idDiary;

    Bitmap bitmap =  null;
    String urlImage = "";
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReferenceFromUrl("gs://studentdiary-e027b.appspot.com");

    private static final int REQUEST_WRITE_PERMISSION = 10;
    private static final int REQUEST_CODE_GALLERY = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content_diary);
        addToolbar();
        addControls();
        requestPermission();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_save){
            processSaveEvent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void processSaveEvent() {

        String content = edtContent.getText().toString();
        String daycreate = Common.f_ymmddhh.format(Calendar.getInstance().getTime());
        if (content.isEmpty()){
            Toast.makeText(this, "Chưa nhập nội dung bài viết", Toast.LENGTH_SHORT).show();
        }else excecute(String.valueOf(idDiary), content, daycreate);

    }

    private void insertPostDiary(final String idDiary, final String content, final String daycreate, final String urlImage) {
        final Popup popup = new Popup(AddContentDiaryActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertPostDiary, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.e("DATA_POST_DIARY", response);
                AddContentDiaryActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //LoadData.loadDataDiary(AddContentDiaryActivity.this);
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                popup.hide();
                                finish();
                            }
                        };
                        timer.start();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("dd_iddiary", idDiary);
                hashMap.put("dd_content", content);
                hashMap.put("dd_daycreate", daycreate);
                hashMap.put("dd_attach", urlImage);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addControls() {
        edtContent = findViewById(R.id.edt_content_diary);
        imgContent = findViewById(R.id.img_content_diary);

        Intent mIntent = getIntent();
        idDiary = mIntent.getIntExtra("ID_DIARY", -1);
    }

    private void addEvents() {

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
            imgContent.setImageBitmap(bitmap);
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
        imgContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, REQUEST_CODE_GALLERY);
            }
        });

    }

    private void excecute(final String s, final String content, final String daycreate){
        if (bitmap != null){
            final StorageReference storageR = storageReference.child("Diary/" + Calendar.getInstance().getTime() + ".png");
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
                        urlImage = downloadUri.toString();
                        Log.e("URL_IMAGE", AddContentDiaryActivity.this.urlImage);
                        AddContentDiaryActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                insertPostDiary(s, content, daycreate, urlImage);
                            }
                        });

                    } else {
                        Toast.makeText(AddContentDiaryActivity.this, "Upload image error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else insertPostDiary(s, content, daycreate, urlImage);

    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Bài viết mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
