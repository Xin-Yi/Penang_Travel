package com.example.penangtravel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>{

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Place> place;
    private FavoriteListAdapter.FavoriteClickListener favoriteClickListener;

    FavoriteListAdapter(Context context, ArrayList<Place> place, FavoriteListAdapter.FavoriteClickListener favoriteClickListener){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.place = place;
        this.favoriteClickListener = favoriteClickListener;
    }

    @NonNull
    @Override
    public FavoriteListAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteListAdapter.FavoriteViewHolder(mInflater.inflate(R.layout.favorite_list_item,parent,false), favoriteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListAdapter.FavoriteViewHolder holder, int position) {
        holder.setData(place.get(position)); //Set data for each holder
    }

    @Override
    public int getItemCount() {
        return place.size(); //Must know how many place in the array list
    }

    public interface FavoriteClickListener{
        void placeClicked(int position, int choice);
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView listTitle;
        private final View itemView;
        private final ImageButton mFavourite;
        private FavoriteListAdapter.FavoriteClickListener favoriteClickListener;

        public FavoriteViewHolder(View itemView, FavoriteListAdapter.FavoriteClickListener favoriteClickListener){
            super(itemView);
            this.itemView = itemView;
            this.favoriteClickListener = favoriteClickListener;

            itemView.setOnClickListener(this);
            listTitle = itemView.findViewById(R.id.list_title);
            mFavourite = itemView.findViewById(R.id.favorite);
            mFavourite.setOnClickListener(this);

        }

        //Set data for corresponding holder
        public void setData(Place place) {
            listTitle.setText(place.getName());
        }

        //When the item in recycler view is clicked
        @Override
        public void onClick(View view) {
            int choice;

            if(view.getId() == R.id.favorite){
                choice = 1;
            }else {
                choice = 2;
            }
            favoriteClickListener.placeClicked(getAdapterPosition(), choice);

        }
    }
}
