package com.example.huyu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huyu.bean.AndroidBean;
import com.example.huyu.bean.BaseBean;
import com.example.huyu.flipped.R;
import com.example.huyu.flipped.WebActivity;

import java.util.List;

/**
 * Created by huyu on 2017/3/10.
 */

public class AndroidRvAdapter  extends RecyclerView.Adapter<AndroidRvAdapter.ARViewHolder>{

    private AndroidBean mAndroidBean;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<AndroidBean.Results> mList;

    public AndroidRvAdapter(AndroidBean androidBean, Context context){
        this.mAndroidBean=androidBean;
        this.mContext=context;
        this.mInflater=LayoutInflater.from(context);
        this.mList=androidBean.getResults();
    }


    @Override
    public ARViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.android_rv_item,parent,false);
        final ARViewHolder holder=new ARViewHolder(view);
        //设置ItemVIew的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                String url=mList.get(position).getUrl();
                Intent intent=new Intent(mContext, WebActivity.class);
                intent.putExtra("url",url);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ARViewHolder holder, int position) {
        AndroidBean.Results androidResult=mList.get(position);
        holder.title.setText(androidResult.getDesc());
        holder.author.setText(androidResult.getWho());
        holder.time.setText(androidResult.getPublishedAt());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ARViewHolder extends RecyclerView.ViewHolder {

        TextView title,author,time;

        public ARViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.android_itemtv_title);
            author= (TextView) itemView.findViewById(R.id.android_itemtv_author);
            time= (TextView) itemView.findViewById(R.id.android_itemtv_time);
        }
    }
}
