package com.example.newsapp.Activities;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.models.Article;
import com.example.newsapp.util.Util;

public class NewsDetailFragment extends Fragment {
 View newsDetailFragView;
 ImageView imageView;
 TextView titleTxtView,authorTxtView,dateTxtView,contentTxtView,appBarTitle;
    Article article;
    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       newsDetailFragView = inflater.inflate(R.layout.details_news,container,false);
       initViews();
        return newsDetailFragView;
    }
    private void initViews(){
        titleTxtView = newsDetailFragView.findViewById(R.id.titleTxtView);
        authorTxtView = newsDetailFragView.findViewById(R.id.authorTxtView);
        dateTxtView = newsDetailFragView.findViewById(R.id.dateTxtView);
        contentTxtView = newsDetailFragView.findViewById(R.id.contentTxtView);
        imageView = newsDetailFragView.findViewById(R.id.imageView);
        appBarTitle = newsDetailFragView.findViewById(R.id.appBarTitle);

        article = new Article();
        if(bundle!=null){
           Log.e("bundle not empty", String.valueOf(bundle));
            article = bundle.getParcelable("articles");
       }
       titleTxtView.setText(article.getTitle());
       authorTxtView.setText(article.getAuthor());
       dateTxtView.setText(Util.formattedDate(article.getPublishedAt()));
       contentTxtView.setText(article.getContent());
       if(!article.getUrlToImage().isEmpty()) {
           Glide.with(getContext()).load(article.getUrlToImage()).into(imageView);
       }
       else {
          imageView.setImageResource(R.drawable.news_default_image);

       }

        Log.e("Image Url",article.getUrlToImage());
        appBarTitle.setText(article.getSource().getName());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }
}
