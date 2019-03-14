package co.realinventor.schoolbus.Common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import co.realinventor.schoolbus.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<Notification> notificationsList;

    public NotificationAdapter(List<Notification> notificationsList){
        this.notificationsList = notificationsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView msg,from,date;

        public MyViewHolder(View view) {
            super(view);
            msg = (TextView) view.findViewById(R.id.msgTextView);
            from = (TextView) view.findViewById(R.id.nameTextView);
            date = (TextView) view.findViewById(R.id.dateTextView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notification notifications = notificationsList.get(position);
        holder.msg.setText(notifications.getMsg());
        holder.date.setText("Date : " + notifications.getDate() + " | " + notifications.getTime());
        holder.from.setText(notifications.getSenderName());
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
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