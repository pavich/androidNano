package com.example.usuario.myappportfolio;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openToast(View v)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Button bTemp= (Button)v;
        CharSequence text;

        if (bTemp.getText().toString().equals("SPOTIFY APP" ))
        {
            text = "This button will launch my Spotify Streamer!";
        }
        else if (bTemp.getText().toString().equals("SCORES APP"))
        {
            text = "This button will launch Scores App!";
        }
        else if (bTemp.getText().toString().equals("LIBRARY APP"))
        {
            text = "This button will launch Library App!";
        }
        else if (bTemp.getText().toString().equals("BUILD IT BIGGER"))
        {
            text = "This button will launch Build It Bigger!";
        }
        else if (bTemp.getText().toString().equals("XYZ READER" ))
        {
            text = "This button will launch XYZ Reader!";
        }
        else if (bTemp.getText().toString().equals("CAPSTONE: MY OWN APP" ))
        {
            text = "This button will launch my capstone app!";
        }
        else
        {
            text = "This button will launch nothing!";
        }
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
