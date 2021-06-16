package com.example.vaksinasishedule;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
//    private ArrayList<String> kodeTempat;
//    private ArrayList<TempatJadwal> tJList;
//    private Context context;
//
//    public RecycleAdapter(ArrayList<String> kodeTempat, ArrayList<TempatJadwal> tJList, Context context) {
//        this.kodeTempat = kodeTempat;
//        this.tJList = tJList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.custom_design, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bind(kodeTempat.get(position), tJList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return kodeTempat.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView txtKodeRS, txtNamaRS;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtKodeRS = itemView.findViewById(R.id.kodeRStxt);
//            txtNamaRS = itemView.findViewById(R.id.namaRStxt);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "bisa" + )
//                }
//            });
//        }
//
//        public void bind(String s, TempatJadwal tempatJadwal) {
//            txtKodeRS.setText(s);
//            txtNamaRS.setText(tempatJadwal.namaRS);
//        }
//    }
//}

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
//        Picasso.get().load(imgList.get(position)).into(holder.picIMG);
//        holder.picIMG.setImageBitmap(bitmap);
        holder.txtKodeRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked on " + kodeTempat.get(position), Toast.LENGTH_LONG).show();
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtKodeRS = itemView.findViewById(R.id.kodeRStxt);
            txtNamaRS = itemView.findViewById(R.id.namaRStxt);
            picIMG = itemView.findViewById(R.id.rsFotoField);

        }
    }
}
