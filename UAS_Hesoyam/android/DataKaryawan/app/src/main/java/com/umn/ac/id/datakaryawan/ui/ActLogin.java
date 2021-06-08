package com.umn.ac.id.datakaryawan.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActLogin extends AppCompatActivity {

    private ImageView btnShowPassword, btnHidePassword;
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private String userName, password;
    private SweetAlertDialog sweetAlertDialog = null;
    private UserSession userSession;
    private DataService dataService;

    @Override
    protected void onPause() {
        super.onPause();
        /* Hide Dialog*/
        if (sweetAlertDialog != null)
            sweetAlertDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        btnShowPassword = findViewById(R.id.btn_showPass);
        btnHidePassword = findViewById(R.id.btn_hidePass);
        etUsername      = findViewById(R.id.et_username);
        etPassword      = findViewById(R.id.et_password);
        btnLogin        = findViewById(R.id.btn_login);
        userSession     = new UserSession(this);
        dataService     = NetworkConfig.getClient().create(DataService.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Show dialog loading*/
                sweetAlertDialog = new SweetAlertDialog(ActLogin.this, SweetAlertDialog.PROGRESS_TYPE);
                sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#7977E7"));
                sweetAlertDialog.setTitleText("Tunggu Sebentar ya...");
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.show();

                userName = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if(userName.isEmpty()||password.isEmpty()){
                    sweetAlertDialog.dismiss();
                    new SweetAlertDialog(ActLogin.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Mohon mengisi field username dan password!")
                            .show();
                }else{
                    getMyLogin(userName, password);
                }
            }
        });

        /* Show Password */
        btnShowPassword.setOnClickListener(v -> {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btnShowPassword.setVisibility(View.GONE);
            btnHidePassword.setVisibility(View.VISIBLE);
        });
        /* Hide Password */
        btnHidePassword.setOnClickListener(v -> {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btnShowPassword.setVisibility(View.VISIBLE);
            btnHidePassword.setVisibility(View.GONE);
        });
    }

    private void getMyLogin(String username, String pass) {
        sweetAlertDialog.dismiss();
        Call<ResponseModel> call = dataService.login(username, pass);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusResponse = response.body().getStatus();
                if (statusResponse.equals("done")){
                    String resultLogin = response.body().getResponse();
                    try {
                        JSONArray jArray = new JSONArray(resultLogin);
                        JSONObject jsonObject = jArray.getJSONObject(0);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String username = jsonObject.getString("username");
                        String isAdmin = jsonObject.getString("is_admin");

                        saveUserSession(id, name, username, isAdmin);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    new SweetAlertDialog(ActLogin.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(response.body().getResponse())
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d("Error", "Error: " + t.toString());
                new SweetAlertDialog(ActLogin.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Terjadi Kesalahan pada server.")
                    .show();
            }
        });
    }

    private void saveUserSession(String id,String name,String username, String isAdmin) {
        userSession.setIsUserLogin(true);
        userSession.setUserId(id);
        userSession.setName(name);
        userSession.setUserName(username);
        userSession.setUserIsAdmin(isAdmin);

        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}