package com.example.newsapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.adapters.HomeFragAdapter;
import com.example.newsapp.models.Article;
import com.example.newsapp.models.NewsHeadlines;
import com.example.newsapp.rest.ApiClient;
import com.example.newsapp.rest.ApiInterface;
import com.example.newsapp.util.OnCardClickListener;
import com.example.newsapp.util.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    View homeView;
    private Context mcontext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       homeView = inflater.inflate(R.layout.home_fragment,container,false);
        initViews();
        return homeView;
    }

    private void initViews(){
        mcontext = getContext();
        recyclerView = homeView.findViewById(R.id.recyclerView);
        if(Util.checkConnection(mcontext)){
            api_call();
        }
        else {
            Toast.makeText(mcontext, "No internet, Please turn on internet and try again...", Toast.LENGTH_SHORT).show();
        }
    }

    public void api_call() {
        Util.showProgDialog(mcontext, "Please wait...");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<NewsHeadlines> call = apiService.getNewsHeadlines("in",Util.API_KEY);
        call.enqueue(new Callback<NewsHeadlines>() {
            @Override
            public void onResponse(Call<NewsHeadlines> call, Response<NewsHeadlines> response) {
             if(response.code()==200){
              setAdapter((ArrayList<Article>) response.body().getArticles());
                 Log.e("response", String.valueOf(response.body()));
              Util.hideProgDialog();
             }
            }

            @Override
            public void onFailure(Call<NewsHeadlines> call, Throwable t) {

            }
        });
    }


    private void setAdapter(ArrayList<Article> articleArrayList) {
        recyclerView.setLayoutManager(new GridLayoutManager(mcontext, 1));
        recyclerView.setAdapter(new HomeFragAdapter(mcontext, articleArrayList,onCardClickListener));
        //animation
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    OnCardClickListener onCardClickListener = new OnCardClickListener() {
        @Override
        public void onItemClicked(Article object) {
         NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
         Bundle bundle = new Bundle();
         bundle.putParcelable("articles", (Parcelable) object);
         Log.e("article Obj", String.valueOf(object.getAuthor()));
         newsDetailFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,newsDetailFragment).addToBackStack(null).commit();
        }
    };
}
