package com.example.huyu.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huyu.bean.IOSBean;
import com.example.huyu.bean.MeiZhiBean;
import com.example.huyu.download.DownloadService;
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

    //长摁接口的回调
    public interface OnItemLongClickDownload{
        void onItemLongClickDownload(View view, int position,String url);
    }

    private OnItemLongClickDownload downloadListener;
    public void setOnItemLongClick(OnItemLongClickDownload downloadListener){
        this.downloadListener=downloadListener;
    }

    @Override
    public MeiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.meizhi_rv_item,parent,false);
         final MeiViewHolder holder=new MeiViewHolder(view);

        //设置ItemVIew的点击事件,长摁下载
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
        //长摁图片出来下载图片的提示
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //开启一个对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setTitle("下载")
                        .setMessage("看到妹子你心动了么？")
                        .setCancelable(false)
                        .setPositiveButton("害羞~", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position=holder.getAdapterPosition();
                        String url=mList.get(position).getUrl();
                        //开启下载服务
                        downloadListener.onItemLongClickDownload(holder.itemView,position,url);
                        Log.i("MeirvAdapter",url);
                        Toast.makeText(mContext, "You clicked OK", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "You clicked No",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;
            }
        });

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
