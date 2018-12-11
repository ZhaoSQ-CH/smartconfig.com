package example.demosmartconfig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class connect_Wifi_Progress_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect__wifi__progress_);

        Button button = (Button)findViewById(R.id.id_next3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(connect_Wifi_Progress_Activity.this,device_list_Actitvity.class);
                startActivity(intent);
            }
        });
    }
}
