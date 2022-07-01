package com.example.penangtravel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserPlaceAdapter extends RecyclerView.Adapter<UserPlaceAdapter.UserViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Place> place;
    private UserPlaceAdapter.UserClickListener userClickListener;

    UserPlaceAdapter(Context context, ArrayList<Place> place, UserPlaceAdapter.UserClickListener userClickListener){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.place = place;
        this.userClickListener = userClickListener;
    }

    @NonNull
    @Override
    public UserPlaceAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserPlaceAdapter.UserViewHolder(mInflater.inflate(R.layout.user_list_item,parent,false), userClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPlaceAdapter.UserViewHolder holder, int position) {
        holder.setData(place.get(position)); //Set data for each holder
    }

    @Override
    public int getItemCount() {
        return place.size(); //Must know how many place in the array list
    }

    public interface UserClickListener{
        void placeClicked(int position);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView listTitle;
        private final View itemView;
        private UserPlaceAdapter.UserClickListener userClickListener;

        public UserViewHolder(View itemView, UserPlaceAdapter.UserClickListener userClickListener){
            super(itemView);
            this.itemView = itemView;
            this.userClickListener = userClickListener;

            itemView.setOnClickListener(this);
            listTitle = itemView.findViewById(R.id.list_title);
        }

        //Set data for corresponding holder
        public void setData(Place place) {
            listTitle.setText(place.getName());
        }

        //When the item in recycler view is clicked
        @Override
        public void onClick(View view) {
            userClickListener.placeClicked(getAdapterPosition());
        }
    }
}
