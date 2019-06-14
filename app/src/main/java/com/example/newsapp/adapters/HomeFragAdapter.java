package com.example.newsapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsapp.Activities.NewsDetailFragment;
import com.example.newsapp.R;
import com.example.newsapp.models.Article;
import com.example.newsapp.util.OnCardClickListener;

import java.util.ArrayList;

public class HomeFragAdapter extends RecyclerView.Adapter<HomeFragAdapter.MyViewHolder>{
    Context context;
    ArrayList<Article> articleArrayList;
   // onItemClickListener onItemClickListener;
    private OnCardClickListener cardClickListener;

    public HomeFragAdapter(Context context, ArrayList<Article> articleArrayList,OnCardClickListener onCardClickListener){
       this.context = context;
       this.articleArrayList = articleArrayList;
       this.cardClickListener = onCardClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_items,viewGroup,false);
        return new MyViewHolder(itemView,cardClickListener) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
          myViewHolder.headlinesTxtView.setText(articleArrayList.get(i).getDescription());
          //myViewHolder.headlinesImageView.setImageURI(Uri.parse(articleArrayList.get(i).getUrlToImage()));
        if(!articleArrayList.get(i).getUrlToImage().isEmpty()) {
            Glide.with(context).load(articleArrayList.get(i).getUrlToImage()).into(myViewHolder.headlinesImageView);
        }
        else
        {
           // Glide.with(context).load(R.drawable.news_default_image).into(myViewHolder.headlinesImageView);
            myViewHolder.headlinesImageView.setImageResource(R.drawable.news_default_image);
        }
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("articles",articleArrayList.get(i));
        newsDetailFragment.setArguments(bundle);
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardClickListener.onItemClicked(articleArrayList.get(i));
                Log.e("adapter Data", String.valueOf(articleArrayList.get(i).getAuthor()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        ImageView headlinesImageView;
        TextView headlinesTxtView;
        OnCardClickListener onCardClickListener;
        public MyViewHolder(@NonNull View itemView,OnCardClickListener onCardClickListener) {
            super(itemView);
            headlinesImageView = itemView.findViewById(R.id.homeImageView);
            headlinesTxtView = itemView.findViewById(R.id.headlines);
            cardView = itemView.findViewById(R.id.cardView);
            this.onCardClickListener = onCardClickListener;
          //  cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardClickListener.onItemClicked(articleArrayList.get(getAdapterPosition()));
        }
    }

//    public interface onItemClickListener{
//        void onItemClicked(Object object);
//    }
}
