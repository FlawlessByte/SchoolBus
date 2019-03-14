package co.realinventor.schoolbus.Parent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import co.realinventor.schoolbus.R;

public class ParentAttendenceAdapter extends RecyclerView.Adapter<ParentAttendenceAdapter.MyViewHolder> {

    private List<ParentAttendence> attendenceList;
    public Context context;

    public ParentAttendenceAdapter(List<ParentAttendence> attendenceList){
        this.attendenceList = attendenceList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public MaterialCheckBox checkBoxAm, checkBoxPm;
        public ImageView imgView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nameTextView);
            checkBoxAm = (MaterialCheckBox) view.findViewById(R.id.checkBoxAm);
            checkBoxPm = (MaterialCheckBox) view.findViewById(R.id.checkBoxPm);
            imgView = (ImageView) view.findViewById(R.id.imgView);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parent_attendence_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ParentAttendence studentAttendence = attendenceList.get(position);
        holder.checkBoxAm.setChecked(studentAttendence.isAm());
        holder.checkBoxPm.setChecked(studentAttendence.isPm());
        holder.name.setText(studentAttendence.getDate());
    }

    @Override
    public int getItemCount() {
        return attendenceList.size();
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
