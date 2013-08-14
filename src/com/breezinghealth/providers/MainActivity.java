package com.breezinghealth.providers;

import com.breezinghealth.R;
import com.breezinghealth.transation.DataReceiver;
import com.breezinghealth.transation.DataTaskService;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == button) {
            sendBroadcast(new Intent(DataTaskService.ACTION_IMPORT_DATA,
                    null,
                    this,
                    DataReceiver.class));
        }
        
    }

}
