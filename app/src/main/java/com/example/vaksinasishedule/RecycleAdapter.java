package com.example.vaksinasishedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private ArrayList<String> kodeTempat;
    private ArrayList<TempatJadwal> tJList;
    Context context;

    public RecycleAdapter(ArrayList<String> kodeT, ArrayList<TempatJadwal> tJList, Context context) {
        this.kodeTempat = kodeT;
        this.tJList = tJList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_design, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        holder.txtKodeRS.setText(kodeTempat.get(position));
        holder.txtNamaRS.setText(tJList.get(position).namaRS);
        Glide.with(context).load(tJList.get(position).uriImage).into(holder.picIMG);
        holder.cntCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), DetailJadwalActivity.class);
                intent.putExtra("coba", kodeTempat.get(position));
                v.getContext().startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return kodeTempat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtKodeRS, txtNamaRS;
        ImageView picIMG;
        CardView cntCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtKodeRS = itemView.findViewById(R.id.kodeRStxt);
            txtNamaRS = itemView.findViewById(R.id.namaRStxt);
            picIMG = itemView.findViewById(R.id.rsFotoField);
            cntCard = itemView.findViewById(R.id.cardContent);

        }
    }
}
