package com.example.penangtravel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminPlaceAdapter extends RecyclerView.Adapter<AdminPlaceAdapter.AdminViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Place> place;
    private AdminClickListener adminClickListener;

    AdminPlaceAdapter(Context context, ArrayList<Place> place, AdminClickListener adminClickListener){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.place = place;
        this.adminClickListener = adminClickListener;
    }

    @NonNull
    @Override
    public AdminPlaceAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminViewHolder(mInflater.inflate(R.layout.admin_list_item,parent,false), adminClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPlaceAdapter.AdminViewHolder holder, int position) {
        holder.setData(place.get(position)); //Set data for each holder
    }

    @Override
    public int getItemCount() {
        return place.size(); //Must know how many place in the array list
    }

    public interface AdminClickListener{
        void placeClicked(int position, int choice);
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView listTitle;
        private final View itemView;
        private final ImageButton delete;
        private AdminClickListener adminClickListener;

        public AdminViewHolder(View itemView, AdminClickListener adminClickListener){
            super(itemView);
            this.itemView = itemView;
            this.adminClickListener = adminClickListener;

            itemView.setOnClickListener(this);
            listTitle = itemView.findViewById(R.id.list_title);
            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(this);

        }

        //Set data for corresponding holder
        public void setData(Place place) {
            listTitle.setText(place.getName());
        }

        //When the item in recycler view is clicked
        @Override
        public void onClick(View view) {
            int choice;

            if(view.getId() == R.id.delete){
                choice = 1;
            }else {
                choice = 2;
            }
            adminClickListener.placeClicked(getAdapterPosition(), choice);

        }
    }
}
