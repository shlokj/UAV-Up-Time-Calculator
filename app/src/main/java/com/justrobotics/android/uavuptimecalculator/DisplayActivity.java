package com.justrobotics.android.uavuptimecalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    int timeInSeconds, minutesPart, secondsPart;
    TextView minutes_part, seconds_part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        timeInSeconds = getIntent().getIntExtra("FT_SECONDS",0);
        secondsPart = timeInSeconds%60;
        minutesPart = (timeInSeconds-secondsPart)/60;
        minutes_part = (TextView) findViewById(R.id.minutes);
        seconds_part = (TextView) findViewById(R.id.seconds);
        minutes_part.setText(Integer.toString(minutesPart));
        seconds_part.setText(Integer.toString(secondsPart));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Flight Time");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.contact_us_sendemail:
                sendEmail();
                break;
            case R.id.about_app:
                displayAboutApp();
                break;
            case R.id.open_on_gp:
                openOnGooglePlay();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    public void composeEmail(String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setType("*/*");
        intent.setType("text/HTML");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "shlokj@gmail.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Drone Flight Time Calculator");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    public void sendEmail () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send a message: ");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        FrameLayout container = new FrameLayout(getApplicationContext());
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        input.setLayoutParams(params);
        container.addView(input);
        //builder.setMessage("This will direct you to send a mail to the developer");
        builder.setView(container);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String Message = input.getText().toString();
                composeEmail(Message);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void displayAboutApp () {
        new AlertDialog.Builder(DisplayActivity.this)
                .setTitle("About Drone Flight Time Calculator")
                .setMessage("This will redirect you to send an email")
                .setMessage(R.string.about_app)
                .setIcon(R.drawable.dftc_drawable_icon)
                .setPositiveButton("OK",null)
                .show();
    }

    public void openOnGooglePlay(){
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
