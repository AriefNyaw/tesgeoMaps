package com.example.tespitjarus.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tespitjarus.Activity.VisitActivity;
import com.example.tespitjarus.Model.Room.Toko;
import com.example.tespitjarus.Model.Room.TokoDb;
import com.example.tespitjarus.R;

import java.util.ArrayList;

public class ListLocationAdapter extends RecyclerView.Adapter<ListLocationAdapter.ListViewHolder> {
    ArrayList<Toko> dataToko;
    Context context;
    TokoDb tokoDb;
    private int checkCardview, checkData;
    String check;
    TextView namaToko, success;

    public ListLocationAdapter(ArrayList<Toko> dataToko, Context context) {
        this.dataToko = dataToko;
        this.context = context;

        tokoDb = Room.databaseBuilder(context.getApplicationContext(), TokoDb.class, "toko")
                .allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        checkCardview = ((Activity)context).getIntent().getIntExtra("isSelected", 0);
        check = ((Activity)context).getIntent().getStringExtra("isKlik");
        namaToko.setText(dataToko.get(position).getStore_name());
        success.setVisibility(View.GONE);
        try {
            if (Integer.parseInt(check) == Integer.parseInt(dataToko.get(position).getStore_id())){
                if (checkCardview == 1){
                    success.setVisibility(View.VISIBLE);
                }else {
                    success.setVisibility(View.GONE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.cardLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VisitActivity.class);
                intent.putExtra("isSelected", 1);
                intent.putExtra("isKlik", dataToko.get(position).getStore_id());
                holder.itemView.getContext().startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataToko.size();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder {

        CardView cardLocation;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            namaToko = itemView.findViewById(R.id.tv_toko);
            cardLocation = itemView.findViewById(R.id.cv_list_location);
            success = itemView.findViewById(R.id.tv_success);

        }
    }
}
