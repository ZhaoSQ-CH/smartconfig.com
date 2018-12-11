package example.demosmartconfig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class resetting_device_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetting_device_);

        Intent intent = getIntent();

        Button button = (Button)findViewById(R.id.id_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(resetting_device_Activity.this,connect_Wifi_Activity.class);
                startActivity(intent2);
            }
        });
    }
}
