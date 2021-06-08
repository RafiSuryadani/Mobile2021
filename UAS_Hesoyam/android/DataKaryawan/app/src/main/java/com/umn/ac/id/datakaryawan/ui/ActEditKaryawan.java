package com.umn.ac.id.datakaryawan.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umn.ac.id.datakaryawan.MainActivity;
import com.umn.ac.id.datakaryawan.R;
import com.umn.ac.id.datakaryawan.model.ResponseModel;
import com.umn.ac.id.datakaryawan.network.DataService;
import com.umn.ac.id.datakaryawan.network.NetworkConfig;
import com.umn.ac.id.datakaryawan.ui.ActDetailKaryawan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActEditKaryawan extends AppCompatActivity {

    private String userId;
    private EditText txtName;
    private EditText txtNik;
    private EditText txtUsername;
    private EditText txtEmail;
    private EditText txtAddress;
    private DataService dataService;
    private CircleImageView imgProfile;
    private Button btnSave;
    private Button btnAmbil;
    private Uri filePath;
    private String photo = "";

    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_karyawan);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userId= null;
            } else {
                userId = extras.getString("userId");
            }
        } else {
            userId = (String) savedInstanceState.getSerializable("userId");
        }

        txtName         = findViewById(R.id.txt_name);
        txtNik          = findViewById(R.id.txt_nik);
        txtUsername     = findViewById(R.id.txt_username);
        txtEmail        = findViewById(R.id.txt_email);
        txtAddress      = findViewById(R.id.txt_address);
        imgProfile      = findViewById(R.id.gambar);
        btnSave         = findViewById(R.id.btn_save);
        btnAmbil        = findViewById(R.id.Ambil);
        dataService     = NetworkConfig.getClient().create(DataService.class);

        btnAmbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        getDetailKaryawan();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKaryawan();
            }
        });
    }

    private void getDetailKaryawan(){
        Call<ResponseModel> call = dataService.detail(userId);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusResponse = response.body().getStatus();
                if (statusResponse.equals("done")){
                    String resultLogin = response.body().getResponse();
                    try {
                        JSONArray jArray = new JSONArray(resultLogin);
                        JSONObject jsonObject = jArray.getJSONObject(0);
                        String name         = jsonObject.getString("name");
                        String username     = jsonObject.getString("username");
                        String nik          = jsonObject.getString("nik");
                        String email        = jsonObject.getString("email");
                        String address      = jsonObject.getString("address");
                        String photo        = jsonObject.getString("photo");
                        String profile_picture  = jsonObject.getString("profile_picture");

                        txtName.setText(name);
                        txtUsername.setText(username);
                        txtNik.setText(nik);
                        txtEmail.setText(email);
                        txtAddress.setText(address);

                        if (photo.equals("null") || photo.isEmpty())
                            imgProfile.setBackgroundResource(R.drawable.ic_person);
                        else
                            Picasso.get().load(profile_picture).into(imgProfile);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    new SweetAlertDialog(ActEditKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Terjadi Kesalahan pada server.")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                new SweetAlertDialog(ActEditKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Terjadi Kesalahan pada server.")
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgProfile.setImageBitmap(bitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();

                photo = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateKaryawan(){
        Call<ResponseModel> call = dataService.update(userId, txtNik.getText().toString(), txtUsername.getText().toString(), txtName.getText().toString(), txtEmail.getText().toString(), photo, txtAddress.getText().toString());
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusResponse = response.body().getStatus();
                if (statusResponse.equals("done")){
                    new SweetAlertDialog(ActEditKaryawan.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Berhasil diperbarui!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(ActEditKaryawan.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            }
                        }).show();

                }else{
                    new SweetAlertDialog(ActEditKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Terjadi Kesalahan pada server.")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                new SweetAlertDialog(ActEditKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Terjadi Kesalahan pada server.")
                        .show();
            }
        });
    }
}