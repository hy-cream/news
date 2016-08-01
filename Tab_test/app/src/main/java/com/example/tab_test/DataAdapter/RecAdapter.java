package com.example.tab_test.DataAdapter;

/**
 * Created by 胡钰 on 2016/7/27.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tab_test.R;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 胡钰 on 2016/7/19.
 */

//类似listview布局的recyclerView适配器
public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater mlayoutInflater;
    private List<HashMap<String, String>> list;
    private String url;

    public RecAdapter(Context context,List<HashMap<String, String>> list){

        this.context=context;
        this.mlayoutInflater=LayoutInflater.from(context);
        this.list=list;
    }

    //添加点击事件，自定义接口，模拟ListView的onitemclickListener
    public interface OnItemClickListener{
        void onItemClick(View view,int position,String url);
        void onLongItemClick(View view,int position);
    }

    //创建一个回调
    private OnItemClickListener monItemClickListener;
    public void setOnItemClickListener(OnItemClickListener monItemClickListener){
        this.monItemClickListener=monItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=mlayoutInflater.inflate(R.layout.reclist,parent,false);

        MyViewHolder holder=new MyViewHolder(view);

        return holder;
    }

    //绑定viewHodler
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_title.setText(list.get(position).get("desc").toString());
        holder.tv_author.setText(list.get(position).get("who").toString());
        holder.tv_publish.setText(list.get(position).get("publishedAt").toString());

        //onclick方法的回调
        if(monItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();
                    String url=list.get(pos).get("url");
                    monItemClickListener.onItemClick(holder.itemView,pos,url);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title;
        TextView tv_author;
        TextView tv_publish;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_title= (TextView) itemView.findViewById(R.id.title);
            tv_author= (TextView) itemView.findViewById(R.id.author);
            tv_publish= (TextView) itemView.findViewById(R.id.publish_time);
        }
    }
}
