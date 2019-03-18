package co.realinventor.schoolbus;

import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.Admin.AdminHomeActivity;
import co.realinventor.schoolbus.Admin.AdminLoginActivity;
import co.realinventor.schoolbus.Parent.ParentHomeActivity;
import co.realinventor.schoolbus.Parent.ParentLoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
    }

    public void driverModeSelected(View view){
        startActivity(new Intent(this, ParentLoginActivity.class).putExtra("usertype","driver"));
    }
    public void parentModeSelected(View view){
        startActivity(new Intent(this, ParentLoginActivity.class).putExtra("usertype","parent"));
    }
    public void adminModeSelected(View view){
        startActivity(new Intent(this, AdminLoginActivity.class));
    }

    public void aboutUsModeSelected(View view){
        startActivity(new Intent(this, AboutUsActivity.class));
    }
}
