package com.aa183.ErsaPutra;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ListHolder extends RecyclerView.ViewHolder{

    public TextView tvJudul, tvPenyayi, tvTahun, tvGenre, tvJenis, tvAlbum;
    public CardView cvList;

    public ListHolder(View itemView){
        super(itemView);
        cvList = itemView.findViewById(R.id.cvList);
        tvJudul = itemView.findViewById(R.id.tvJudul);
        tvPenyayi = itemView.findViewById(R.id.tvPenyayi);
        tvTahun = itemView.findViewById(R.id.tvTahun);
        tvGenre = itemView.findViewById(R.id.tvGenre);
        tvJenis = itemView.findViewById(R.id.tvJenis);
        tvAlbum = itemView.findViewById(R.id.tvAlbum);
        tvPenyayi = itemView.findViewById(R.id.tvPenyayi);
    }

}
