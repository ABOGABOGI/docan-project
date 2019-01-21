package fw.docan.docan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fw.docan.docan.R;
import fw.docan.docan.model.ListAgen;

public class AgenAdapter extends RecyclerView.Adapter<AgenAdapter.AgenViewHolder>{
    private ArrayList<ListAgen> dataList;

    public AgenAdapter(ArrayList<ListAgen> dataList) {
        this.dataList = dataList;
    }

    @Override
    public AgenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_list_item_agen, parent, false);
        return new AgenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AgenViewHolder holder, int position) {
        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtJumlahsaldo.setText(dataList.get(position).getJumlahsaldo());
        holder.txtJumlahcash.setText(dataList.get(position).getJumlahcash());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AgenViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtJumlahsaldo, txtJumlahcash;

        public AgenViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtJumlahsaldo = (TextView) itemView.findViewById(R.id.txt_jumlahsaldo);
            txtJumlahcash = (TextView) itemView.findViewById(R.id.txt_jumlahcash);
        }
    }

}
