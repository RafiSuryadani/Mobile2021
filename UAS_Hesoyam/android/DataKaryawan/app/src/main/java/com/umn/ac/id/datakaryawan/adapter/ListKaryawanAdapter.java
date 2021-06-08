package com.umn.ac.id.datakaryawan.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.umn.ac.id.datakaryawan.ui.ActDetailKaryawan;
import com.umn.ac.id.datakaryawan.R;
import com.umn.ac.id.datakaryawan.model.ListKaryawanModel;

import java.util.List;

public class ListKaryawanAdapter extends RecyclerView.Adapter<ListKaryawanAdapter.ViewHolder>{

    private List<ListKaryawanModel> listKaryawan;
    private Context context;

    public ListKaryawanAdapter(Context context, List<ListKaryawanModel> data){
        this.context = context;
        this.listKaryawan = data;
    }

    @NonNull
    @Override
    public ListKaryawanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_list_karyawan, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListKaryawanAdapter.ViewHolder holder, int position) {
        ListKaryawanModel model = listKaryawan.get(position);

        holder.nameUser.setText(model.getName());
        holder.nik.setText(model.getNik());

        if (model.getPhoto() == "null")
            holder.profilePicture.setBackgroundResource(R.drawable.ic_person);
        else
            Picasso.get().load(model.getProfilePicture()).into(holder.profilePicture);

        if (model.getIsAdmin().equals("0"))
            holder.isAdmin.setText("Karyawan");
        else
            holder.isAdmin.setText("Admin");

        holder.nameUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActDetailKaryawan.class);
                intent.putExtra("userId", model.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKaryawan.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profilePicture;
        public TextView nameUser;
        public TextView nik;
        public TextView isAdmin;

        public ViewHolder(View view) {
            super(view);
            profilePicture = view.findViewById(R.id.gambar);
            nameUser = view.findViewById(R.id.txt_name);
            nik = view.findViewById(R.id.txt_nik);
            isAdmin = view.findViewById(R.id.txt_isAdmin);
        }
    }
}
