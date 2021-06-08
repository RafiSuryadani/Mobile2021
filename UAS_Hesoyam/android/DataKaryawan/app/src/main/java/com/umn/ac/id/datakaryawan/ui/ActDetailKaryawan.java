package com.umn.ac.id.datakaryawan.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umn.ac.id.datakaryawan.MainActivity;
import com.umn.ac.id.datakaryawan.R;
import com.umn.ac.id.datakaryawan.model.ResponseModel;
import com.umn.ac.id.datakaryawan.network.DataService;
import com.umn.ac.id.datakaryawan.network.NetworkConfig;
import com.umn.ac.id.datakaryawan.session.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActDetailKaryawan extends AppCompatActivity {
    private String userId;
    private TextView txtName;
    private TextView txtNik;
    private TextView txtUsername;
    private TextView txtEmail;
    private TextView txtAddress;
    private DataService dataService;
    private CircleImageView imgProfile;
    private Button btnDelete;
    private Button btnEdit;
    private LinearLayout llBtnEdit;
    private TextView txtIsAdmin;
    private UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_karyawan);

        txtName         = findViewById(R.id.txt_name);
        txtNik          = findViewById(R.id.txt_nik);
        txtUsername     = findViewById(R.id.txt_username);
        txtEmail        = findViewById(R.id.txt_email);
        txtAddress      = findViewById(R.id.txt_address);
        imgProfile      = findViewById(R.id.gambar);
        btnDelete       = findViewById(R.id.btn_delete);
        btnEdit         = findViewById(R.id.btn_edit);
        llBtnEdit       = findViewById(R.id.ll_btnEdit);
        txtIsAdmin      = findViewById(R.id.txt_isAdmin);
        dataService     = NetworkConfig.getClient().create(DataService.class);
        userSession     = new UserSession(ActDetailKaryawan.this);

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

        if (userSession.getUserIsAdmin().equals("0"))
            llBtnEdit.setVisibility(View.GONE);
        else
            llBtnEdit.setVisibility(View.VISIBLE);

        getDetailKaryawan();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(ActDetailKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Apakah kamu yakin?")
                        .setContentText("Data akan dihapus!")
                        .setConfirmText("Ya, Hapus ini!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                deleteKaryawan();
                            }
                        })
                        .show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActDetailKaryawan.this, ActEditKaryawan.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
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
                        String isAdmin      = jsonObject.getString("is_admin");

                        txtName.setText(name);
                        txtUsername.setText(username);
                        txtNik.setText(nik);
                        txtEmail.setText(email);
                        txtAddress.setText(address);

                        if (photo.equals("null") || photo.isEmpty())
                           imgProfile.setBackgroundResource(R.drawable.ic_person);
                        else
                        Picasso.get().load(profile_picture).into(imgProfile);

                        if (isAdmin.equals("0"))
                            txtIsAdmin.setText("Karyawan");
                        else
                            txtIsAdmin.setText("Admin");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    new SweetAlertDialog(ActDetailKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Terjadi Kesalahan pada server.")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                new SweetAlertDialog(ActDetailKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Terjadi Kesalahan pada server.")
                        .show();
            }
        });
    }

    private void deleteKaryawan(){
        Call<ResponseModel> call = dataService.delete(userId);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusResponse = response.body().getStatus();
                if (statusResponse.equals("done")){
                    new SweetAlertDialog(ActDetailKaryawan.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil dihapus!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(ActDetailKaryawan.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    finish();
                                }
                            }).show();

                }else{
                    new SweetAlertDialog(ActDetailKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Terjadi Kesalahan pada server.")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                new SweetAlertDialog(ActDetailKaryawan.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Terjadi Kesalahan pada server.")
                        .show();
            }
        });
    }
}