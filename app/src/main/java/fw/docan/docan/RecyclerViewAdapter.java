package fw.docan.docan;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView
        .Adapter<RecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Beanlist> bean;
    private Context context;
    private static MyClickListener myClickListener;
    Typeface fonts1,fonts2,fonts3,fonts4,fonts5;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {


        ImageView image;
        TextView amount;
        TextView billname;
        TextView date;


        public DataObjectHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            amount = (TextView) itemView.findViewById(R.id.amount);
            billname = (TextView) itemView.findViewById(R.id.billname);
            date = (TextView) itemView.findViewById(R.id.date);


            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
     //       myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RecyclerViewAdapter(Context context, ArrayList<Beanlist> bean) {


        this.context = context;
        this.bean = bean;
    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

      //  holder.icon.setImageResource(mDataset.get(position).get());
        holder.image.setImageDrawable(bean.get(position).getImage());
        holder.amount.setText(bean.get(position).getAmount());
        holder.billname.setText(bean.get(position).getBillname());
        holder.date.setText(bean.get(position).getDate());


        fonts1 =  Typeface.createFromAsset(context.getAssets(),
                "fonts/Liquide.ttf");
        fonts2 =  Typeface.createFromAsset(context.getAssets(),
                "fonts/Lato-Medium.ttf");
        fonts3 =  Typeface.createFromAsset(context.getAssets(),
                "fonts/MavenPro-Regular.ttf");

        holder.amount.setTypeface(fonts1);
        holder.billname.setTypeface(fonts2);
        holder.date.setTypeface(fonts2);


    }



    public void deleteItem(int index) {
        bean.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return bean.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);



    }
}