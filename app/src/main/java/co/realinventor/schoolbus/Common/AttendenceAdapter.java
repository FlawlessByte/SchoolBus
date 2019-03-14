package co.realinventor.schoolbus.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import co.realinventor.schoolbus.R;

public class AttendenceAdapter extends RecyclerView.Adapter<AttendenceAdapter.MyViewHolder> {

    private List<StudentAttendence> attendenceList;
    public Context context;

    public AttendenceAdapter(List<StudentAttendence> attendenceList){
        this.attendenceList = attendenceList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public MaterialCheckBox checkBoxAm, checkBoxPm;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nameTextView);
            checkBoxAm = (MaterialCheckBox) view.findViewById(R.id.checkBoxAm);
            checkBoxPm = (MaterialCheckBox) view.findViewById(R.id.checkBoxPm);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendence_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final StudentAttendence studentAttendence = attendenceList.get(position);
        holder.checkBoxAm.setChecked(studentAttendence.isAm());
        holder.checkBoxPm.setChecked(studentAttendence.isPm());
        holder.name.setText(studentAttendence.getName());
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
