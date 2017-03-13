package com.example.huyu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huyu.bean.XiaBean;
import com.example.huyu.bean.XiaBean;
import com.example.huyu.flipped.R;
import com.example.huyu.flipped.WebActivity;

import java.util.List;

/**
 * Created by huyu on 2017/3/13.
 */

public class XiaRvAdapter extends RecyclerView.Adapter<XiaRvAdapter.XiaViewHolder>{

    private XiaBean mXiaBean;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<XiaBean.Results> mList;


    public XiaRvAdapter(XiaBean XiaBean, Context context){
        this.mXiaBean=XiaBean;
        this.mContext=context;
        this.mInflater=LayoutInflater.from(context);
        this.mList=XiaBean.getResults();
    }


    @Override
    public XiaRvAdapter.XiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=mInflater.inflate(R.layout.xia_rv_item,parent,false);
        final XiaRvAdapter.XiaViewHolder holder=new XiaRvAdapter.XiaViewHolder(view);
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
    public void onBindViewHolder(XiaRvAdapter.XiaViewHolder holder, int position) {
        XiaBean.Results XiaResult=mList.get(position);
        holder.title.setText(XiaResult.getDesc());
        holder.author.setText(XiaResult.getWho());
        holder.time.setText(XiaResult.getPublishedat());
        //图片加载框架glide
        //.override(300, 200);
        ////固定imageView的大小
        if (XiaResult.getImages()!=null){
            Glide.with(mContext)
                    .load(XiaResult.getImages().get(0))
                    .into(holder.img);
        }else {
            holder.img.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class XiaViewHolder extends RecyclerView.ViewHolder {

        TextView title,author,time;
        ImageView img;
        CardView cardView;

        public XiaViewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView;
            title= (TextView) itemView.findViewById(R.id.xia_itemtv_title);
            author= (TextView) itemView.findViewById(R.id.xia_itemtv_author);
            time= (TextView) itemView.findViewById(R.id.xia_itemtv_time);
            img= (ImageView) itemView.findViewById(R.id.xia_itemiv_imge);
        }
    }
}
