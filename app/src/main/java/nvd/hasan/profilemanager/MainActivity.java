package nvd.hasan.profilemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onActiveClick(View v){
        startService(new Intent(this, MyService.class));
        Toast.makeText(this, "Activated", Toast.LENGTH_LONG).show();
    }

    public void onDeactiveClick(View v){
        stopService(new Intent(this, MyService.class));
        Toast.makeText(this, "Deactivated", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        stopService(new Intent(this, MyService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
