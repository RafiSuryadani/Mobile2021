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

import com.umn.ac.id.datakaryawan.MainActivity;
import com.umn.ac.id.datakaryawan.R;
import com.umn.ac.id.datakaryawan.model.ResponseModel;
import com.umn.ac.id.datakaryawan.network.DataService;
import com.umn.ac.id.datakaryawan.network.NetworkConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActAddKaryawan extends AppCompatActivity {

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
        setContentView(R.layout.act_add_karyawan);

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertKaryawan();
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

    private void insertKaryawan(){
        Call<ResponseModel> call = dataService.insert(txtNik.getText().toString(), txtUsername.getText().toString(), txtName.getText().toString(), txtEmail.getText().toString(), photo, txtAddress.getText().toString());
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusResponse = response.body().getStatus();
                if (statusResponse.equals("done")){
                    new SweetAlertDialog(ActAddKaryawan.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil diperbarui!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(ActAddKaryawan.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    finish();
                                }
                            }).show();

                }else{
                    new SweetAlertDialog(ActAddKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Terjadi Kesalahan pada server.")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                new SweetAlertDialog(ActAddKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Terjadi Kesalahan pada server.")
                        .show();
            }
        });
    }
}