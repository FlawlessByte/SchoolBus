package co.realinventor.schoolbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {
    TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_about_us);

        textView = findViewById(R.id.textViewNames);

        textView.setText("Nikhil Joy\nAleesh P Antony\nEvin Tony\nKiran KV\nSreekanth Satheesan\nAjith TJ");
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
