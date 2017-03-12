package com.example.huyu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huyu.bean.AndroidBean;
import com.example.huyu.bean.IOSBean;
import com.example.huyu.flipped.R;
import com.example.huyu.flipped.WebActivity;

import java.util.List;

/**
 * Created by huyu on 2017/3/12.
 */

public class IOSRvAdapter extends RecyclerView.Adapter<IOSRvAdapter.IosViewHolder>{

    private IOSBean mIosBean;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<IOSBean.Results> mList;


    public IOSRvAdapter(IOSBean iosBean, Context context){
        this.mIosBean=iosBean;
        this.mContext=context;
        this.mInflater=LayoutInflater.from(context);
        this.mList=iosBean.getResults();
    }


    @Override
    public IosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=mInflater.inflate(R.layout.ios_rv_item,parent,false);
        final IosViewHolder holder=new IosViewHolder(view);
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
    public void onBindViewHolder( IosViewHolder holder, int position) {
        IOSBean.Results iosResult=mList.get(position);
        holder.title.setText(iosResult.getDesc());
        holder.author.setText(iosResult.getWho());
        holder.time.setText(iosResult.getPublishedAt());
        //图片加载框架glide
        //.override(300, 200);
        ////固定imageView的大小
        if (iosResult.getString()!=null){
            Glide.with(mContext)
                    .load(iosResult.getString().get(0))
                    .override(120,80)
                    .into(holder.img);
        }else {
            holder.img.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class IosViewHolder extends RecyclerView.ViewHolder {

        TextView title,author,time;
        ImageView img;

        public IosViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.ios_itemtv_title);
            author= (TextView) itemView.findViewById(R.id.ios_itemtv_author);
            time= (TextView) itemView.findViewById(R.id.ios_itemtv_time);
            img= (ImageView) itemView.findViewById(R.id.ios_itemiv_imge);
        }
    }
}
