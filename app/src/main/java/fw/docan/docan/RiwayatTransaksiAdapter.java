package fw.docan.docan;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import fw.docan.docan.model.RiwayatTransaksi;

public class RiwayatTransaksiAdapter extends ArrayAdapter<RiwayatTransaksi> implements View.OnClickListener{

    protected Context mContext;
    protected List<RiwayatTransaksi> mTransaksi;


    public RiwayatTransaksiAdapter(Context context, List<RiwayatTransaksi> objects) {
        super(context, R.layout.list_item_riwayat_transaksi, objects);
        mContext = context;
        mTransaksi = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.list_item_riwayat_transaksi, null);
            holder = new ViewHolder();
            holder.iconTransaksi = (ImageView) convertView
                    .findViewById(R.id.iconTransaksi);
            holder.judulTransaksi = (TextView) convertView
                    .findViewById(R.id.txtJudul);
            holder.waktuTransaksi = (TextView) convertView
                    .findViewById(R.id.txtDateTime);
            holder.jumlahTransaksi = (TextView) convertView
                    .findViewById(R.id.txtAmount);


            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();



        }

        RiwayatTransaksi RiwayatTransaksi = mTransaksi.get(position);
        holder.iconTransaksi.setImageResource(RiwayatTransaksi.getIconTransaksi());
        holder.judulTransaksi.setText(RiwayatTransaksi.getJudulTransaksi());
        holder.waktuTransaksi.setText(RiwayatTransaksi.getWaktuTransaksi());
        holder.jumlahTransaksi.setText(RiwayatTransaksi.getJumlahTransaksi());



        return convertView;
    }

    public static class ViewHolder {
        ImageView iconTransaksi;
        TextView judulTransaksi;
        TextView waktuTransaksi;
        TextView jumlahTransaksi;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        RiwayatTransaksi dataModel=(RiwayatTransaksi)object;


        Log.d("**********", dataModel.getJudulTransaksi().toString());
//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }
}
