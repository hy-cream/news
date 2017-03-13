package com.example.huyu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huyu.bean.IOSBean;
import com.example.huyu.bean.MeiZhiBean;
import com.example.huyu.flipped.R;
import com.example.huyu.flipped.WebActivity;

import java.util.List;

/**
 * Created by huyu on 2017/3/13.
 */

public class MeiRvAdapter extends  RecyclerView.Adapter<MeiRvAdapter.MeiViewHolder>{

    private List<MeiZhiBean.Results>  mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public MeiRvAdapter(Context context,MeiZhiBean bean){
        this.mList=bean.getResults();
        mContext=context;
        this.mInflater=LayoutInflater.from(context);
    }

    @Override
    public MeiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.meizhi_rv_item,parent,false);
         final MeiViewHolder holder=new MeiViewHolder(view);
        //设置ItemVIew的点击事件
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position=holder.getAdapterPosition();
//                String url=mList.get(position).getUrl();
//                Intent intent=new Intent(mContext, WebActivity.class);
//                intent.putExtra("url",url);
//                mContext.startActivity(intent);
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MeiViewHolder holder, int position) {
       MeiZhiBean.Results meiResult=mList.get(position);
        //图片加载框架glide
        //.override(300, 200);
        ////固定imageView的大小

            Glide.with(mContext)
                    .load(meiResult.getUrl())
                    .into(holder.imageView);
        holder.desc.setText(meiResult.getDesc());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MeiViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView desc;
        CardView cd;
        public MeiViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.meizhi_item_img);
            cd= (CardView) itemView.findViewById(R.id.meizhi_item_cd);

            //设置瀑布流图的宽高
            WindowManager manager= (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm=new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            int width= dm.widthPixels;
            int height=dm.heightPixels;
            ViewGroup.LayoutParams layoutParams= imageView.getLayoutParams();
            layoutParams.width=width-5;
//            layoutParams.height=height*2/3;
            imageView.setLayoutParams(layoutParams);
            imageView.setMaxHeight(height*2/3);
            desc= (TextView) itemView.findViewById(R.id.meizhi_item_desc);
        }
    }
}
