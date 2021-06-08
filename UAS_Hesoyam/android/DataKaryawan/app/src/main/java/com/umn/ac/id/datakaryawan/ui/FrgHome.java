package com.umn.ac.id.datakaryawan.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.umn.ac.id.datakaryawan.R;
import com.umn.ac.id.datakaryawan.adapter.ListKaryawanAdapter;
import com.umn.ac.id.datakaryawan.model.ListKaryawanModel;
import com.umn.ac.id.datakaryawan.model.ResponseModel;
import com.umn.ac.id.datakaryawan.network.DataService;
import com.umn.ac.id.datakaryawan.network.NetworkConfig;
import com.umn.ac.id.datakaryawan.session.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrgHome extends Fragment {

    private View homeView;
    private UserSession userSession;
    private DataService dataService;
    private TextView txtTotalKaryawan;
    private Button btnAdd;
    private TextView txtIsAdmin;
    List<ListKaryawanModel> listKaryawanModels = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeView =  inflater.inflate(R.layout.frg_home, container, false);
        userSession         = new UserSession(getActivity());
        dataService         = NetworkConfig.getClient().create(DataService.class);
        txtTotalKaryawan    = homeView.findViewById(R.id.txt_totalKaryawan);
        recyclerView        = homeView.findViewById(R.id.recycleView);
        btnAdd              = homeView.findViewById(R.id.btn_add);
        txtIsAdmin          = homeView.findViewById(R.id.txt_isAdmin);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (userSession.getUserIsAdmin().equals("0")){
            txtIsAdmin.setText("Karyawan");
            btnAdd.setVisibility(View.GONE);
        }
        else{
            txtIsAdmin.setText("Admin");
            btnAdd.setVisibility(View.VISIBLE);
        }


        /* Total Karyawan */
        getTotalKarywana();

        /* list Karyawan */
        getListKarywana();

        /* Tambah Karyawan*/
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActAddKaryawan.class);
                startActivity(intent);
            }
        });

        return homeView;
    }

    private void getTotalKarywana(){
        Call<ResponseModel> call = dataService.getTotalKaryawan();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusResponse = response.body().getStatus();
                if (statusResponse.equals("done")){
                    String resultLogin = response.body().getResponse();
                    try {
                        JSONArray jArray = new JSONArray(resultLogin);
                        JSONObject jsonObject = jArray.getJSONObject(0);
                        String total = jsonObject.getString("total");
                        txtTotalKaryawan.setText(total);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Terjadi Kesalahan pada server.")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Terjadi Kesalahan pada server.")
                        .show();
            }
        });
    }

    private void getListKarywana(){
        Call<ResponseModel> call = dataService.getListKaryawan();
        call.enqueue(new Callback<ResponseModel>() {

            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusResponse = response.body().getStatus();
                if (statusResponse.equals("done")){
                    String resultLogin = response.body().getResponse();
                    try {
                        JSONArray jArray = new JSONArray(resultLogin);

                        if (jArray.length() > 0) {
                            for (int i = 0; i<jArray.length();i++){
                                ListKaryawanModel listKaryawanModel = new ListKaryawanModel();
                                JSONObject jObject = jArray.getJSONObject(i);
                                listKaryawanModel.setId(jObject.getString("id"));
                                listKaryawanModel.setName(jObject.getString("name"));
                                listKaryawanModel.setUsername(jObject.getString("username"));
                                listKaryawanModel.setEmail(jObject.getString("email"));
                                listKaryawanModel.setPhoto(jObject.getString("photo"));
                                listKaryawanModel.setNik(jObject.getString("nik"));
                                listKaryawanModel.setProfilePicture(jObject.getString("profile_picture"));
                                listKaryawanModel.setIsAdmin(jObject.getString("is_admin"));

                                listKaryawanModels.add(listKaryawanModel);
                            }

                            ListKaryawanAdapter adapter = new ListKaryawanAdapter(getActivity(), listKaryawanModels);
                            recyclerView.setAdapter(adapter);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Terjadi Kesalahan pada server.")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Terjadi Kesalahan pada server.")
                        .show();
            }
        });
    }
}