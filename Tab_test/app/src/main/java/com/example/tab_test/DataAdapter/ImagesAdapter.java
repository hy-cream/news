package com.example.tab_test.DataAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.tab_test.R;
import java.util.HashMap;
import java.util.List;


/**
 * Created by 胡钰 on 2016/7/29.
 */

//瀑布流的recyclerview 适配器
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.imageViewHolder> {

    private Context context;
    private LayoutInflater mlayoutInflater;
    private List<HashMap<String, String>> list;
    private String url;
    private RequestQueue mqueue;

    public ImagesAdapter(Context context, List<HashMap<String, String>> list,RequestQueue mqueue){

        this.context=context;
        this.mlayoutInflater=LayoutInflater.from(context);
        this.list=list;
        this.mqueue=mqueue;

    }

    @Override
    public ImagesAdapter.imageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=mlayoutInflater.inflate(R.layout.imagrecslist,parent,false);

        imageViewHolder holder=new imageViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ImagesAdapter.imageViewHolder holder, int position) {

        url= list.get(position).get("url");

        ImageRequest imageRequest=new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {

                holder.mimages.setImageBitmap(bitmap);

            }
        }, 0,0, Bitmap.Config.RGB_565,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("error",volleyError.toString());
            }
        });

        mqueue.add(imageRequest);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class imageViewHolder extends RecyclerView.ViewHolder{
        ImageView mimages;

        public imageViewHolder(View itemView) {
            super(itemView);
            mimages= (ImageView) itemView.findViewById(R.id.images);

        }
    }
}

