package com.example.himatiftrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import com.google.android.material.card.MaterialCardView;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {

    private List<Mahasiswa> mahasiswaList;
    private Context context;
    private Typeface comfortaa, comfortaaBold;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Mahasiswa m);
    }

    public MahasiswaAdapter(Context context, List<Mahasiswa> list, OnItemClickListener listener) {
        this.context = context;
        this.mahasiswaList = list;
        this.listener = listener;
        comfortaa = ResourcesCompat.getFont(context, R.font.comfortaa_regular);
        comfortaaBold = ResourcesCompat.getFont(context, R.font.comfortaa_bold);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mahasiswa m = mahasiswaList.get(position);
        holder.nama.setText(m.getNama());
        holder.nim.setText(m.getNim());

        holder.nama.setTypeface(comfortaaBold);
        holder.nim.setTypeface(comfortaa);

        // Click listener untuk seluruh card - action yang sama
        View.OnClickListener clickListener = v -> {
            if (listener != null) {
                listener.onItemClick(m);
            }
        };

        // Set listener untuk card dan button dengan action yang sama
        holder.cardMahasiswa.setOnClickListener(clickListener);
        holder.btnEdit.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardMahasiswa;
        TextView nama, nim;
        ImageView btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardMahasiswa = itemView.findViewById(R.id.cardMahasiswa);
            nama = itemView.findViewById(R.id.tvNama);
            nim = itemView.findViewById(R.id.tvNIM);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}