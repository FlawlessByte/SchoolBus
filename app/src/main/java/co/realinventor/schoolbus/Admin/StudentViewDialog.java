package co.realinventor.schoolbus.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import co.realinventor.schoolbus.Common.Student;
import co.realinventor.schoolbus.R;

public class StudentViewDialog extends DialogFragment {
    public static String TAG = "StudentViewDialog";
    public static Student currentStudent;
    private TextView textViewStudentName, textViewStudentRegNo, textViewStudentAddress, textViewStudentStop,
            textViewStudentBusNo, textViewStudentPhone, textViewStudentRFID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.student_view_dialog, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbarDialogStudent);
        toolbar.setNavigationIcon(R.drawable.close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        toolbar.setTitle("Student Details");

        textViewStudentName = view.findViewById(R.id.textViewStudentName);
        textViewStudentRegNo = view.findViewById(R.id.textViewStudentRegNo);
        textViewStudentAddress = view.findViewById(R.id.textViewStudentAddress);
        textViewStudentStop = view.findViewById(R.id.textViewStudentStop);
        textViewStudentBusNo = view.findViewById(R.id.textViewStudentBusNo);
        textViewStudentPhone = view.findViewById(R.id.textViewStudentPhone);
        textViewStudentRFID = view.findViewById(R.id.textViewStudentRFID);


        textViewStudentName.setText(currentStudent.getName());
        textViewStudentRegNo.setText("Reg No. : "+currentStudent.getRegNo());
        textViewStudentAddress.setText("Address : "+currentStudent.getAddress());
        textViewStudentStop.setText("Stop : "+currentStudent.getStop());
        textViewStudentBusNo.setText("Bus No. : "+currentStudent.getBusNo());
        textViewStudentPhone.setText("Phone : "+currentStudent.getContact());
        textViewStudentRFID.setText("RFID : "+currentStudent.getRfid());

        return view;
    }

    private void closeDialog(){
        this.dismiss();
    }

}
