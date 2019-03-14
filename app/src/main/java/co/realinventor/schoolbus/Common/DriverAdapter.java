package co.realinventor.schoolbus.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import co.realinventor.schoolbus.R;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyViewHolder> {

    private List<Driver> driverList;
    public Context context;

    public DriverAdapter(List<Driver> driverList){
        this.driverList = driverList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView busNo,name,contact;

        public MyViewHolder(View view) {
            super(view);
            busNo = (TextView) view.findViewById(R.id.busNoTextView);
            name = (TextView) view.findViewById(R.id.nameTextView);
            contact = (TextView) view.findViewById(R.id.contactTextView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.driver_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Driver driver = driverList.get(position);
        holder.busNo.setText("Bus No. : "+driver.getBusNo());
        holder.contact.setText("Contact No. : "+driver.getContact());
        holder.name.setText(driver.getName());
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
