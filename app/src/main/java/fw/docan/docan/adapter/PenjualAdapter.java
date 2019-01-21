package fw.docan.docan.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import fw.docan.docan.R;

public class PenjualAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] name,address,type;
    private final Integer[] img;

    public PenjualAdapter(Activity context, String[] name,String[] address,String[] type, Integer[] img) {
        super(context, R.layout.custom_penjual, name);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.name = name;
        this.address = address;
        this.type = type;
        this.img=img;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_penjual, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.name_seller);
        TextView txtAddress = rowView.findViewById(R.id.address_seller);
        TextView txtType = rowView.findViewById(R.id.tipe_seller);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img_seller);

        txtTitle.setText(name[position]);
        txtAddress.setText(address[position]);
        txtType.setText(type[position]);
        imageView.setImageResource(img[position]);
        return rowView;

    };

}
