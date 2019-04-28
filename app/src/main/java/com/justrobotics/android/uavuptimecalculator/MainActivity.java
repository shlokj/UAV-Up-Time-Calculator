package com.justrobotics.android.uavuptimecalculator;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.content.DialogInterface;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    String canFly;

    public void composeEmail(String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","shlokj@gmail.com", null));
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
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
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

    int number_of_rotors;
    float thrustChangedByAngle;
    EditText Battery_Capacity,Amps_perMotor,Thrust_perMotor,Weight_ofDrone;
    Spinner typeOfDrone, sizeOfDrone;
    Button CalculateFTime;

    public void displayFlightTime (int flightTimeInSeconds){
        float flightTimeInMinutes = (float)flightTimeInSeconds/(float)60;
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Calculated Flight Time")
                .setMessage("Your UAV will fly for "+flightTimeInSeconds+" seconds, which is equivalent to "+flightTimeInMinutes+" minutes.")
                .setPositiveButton("OK",null)
                .show();
    }

    public void displayAboutApp () {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("About Drone Flight Time Calculator");
        builder.setMessage("This will redirect you to send an email");
        builder.setMessage(R.string.about_app);
        builder.setIcon(R.drawable.dftc_drawable_icon);
        builder.setPositiveButton("OK",null);
        builder.setNeutralButton("Contact", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendEmail();
            }
        });
        builder.show();
    }

    public void openOnGooglePlay(){
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Battery_Capacity = (EditText) findViewById(R.id.batteryCapacity);
        Amps_perMotor = (EditText) findViewById(R.id.MAPM);
        Thrust_perMotor = (EditText) findViewById(R.id.MTPM);
        Weight_ofDrone = (EditText) findViewById(R.id.droneWeight);
        typeOfDrone = (Spinner) findViewById(R.id.droneType);
        sizeOfDrone = (Spinner) findViewById(R.id.droneSize) ;
        final Vibrator vibrator = (Vibrator) getSystemService(MainActivity.this.VIBRATOR_SERVICE);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sizes));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfDrone.setAdapter(myAdapter);
        sizeOfDrone.setAdapter(myAdapter1);
        typeOfDrone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String drone_type = typeOfDrone.getSelectedItem().toString();

                switch (drone_type){
                    case "Tricopter":
                        number_of_rotors = 3;
                        break;
                    case "Quadcopter":
                        number_of_rotors = 4;
                        break;
                    case "Hexacopter":
                        number_of_rotors = 6;
                        break;
                    case "Octacopter":
                        number_of_rotors = 8;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        typeOfDrone.setSelection(1);
        sizeOfDrone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String size_of_drone=sizeOfDrone.getSelectedItem().toString();
                switch (size_of_drone){
                    case "Racing Drone (180-270, high speed)":
                        thrustChangedByAngle=0.7f;
                    break;
                    case  "Medium Size (330-600, normal pace)":
                        thrustChangedByAngle=0.85f;
                        break;
                    case "Large UAV (680+, stability type)":
                        thrustChangedByAngle=0.95f;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sizeOfDrone.setSelection(1);

        CalculateFTime = (Button) findViewById(R.id.calculate_flight_time);
        CalculateFTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Values from editTexts should be collected at the time of button click event.
                String stringA = Battery_Capacity.getText().toString();
                String stringB = Amps_perMotor.getText().toString();
                String stringC = Thrust_perMotor.getText().toString();
                String stringD = Weight_ofDrone.getText().toString();

                if (!stringA.matches("[0-9]+")){
                    stringA="0";
                }
                if (!stringB.matches("[0-9]+")){
                    stringB="0";
                }
                if (!stringC.matches("[0-9]+")){
                    stringC="0";
                }
                if (!stringD.matches("[0-9]+")){
                    stringD="0";
                }
                if (vibrator!=null){
                    vibrator.vibrate(40);
                }

                float battery_capacity_mah =Float.parseFloat(!stringA.isEmpty()?stringA:"0") * 80/100;
                float total_thrust =  Integer.parseInt(!stringC.isEmpty()?stringC:"0") * number_of_rotors ;
                int total_max_ampdraw = Integer.parseInt(stringB.isEmpty()||stringB.equals("0")?"1":stringB) * number_of_rotors;//Simplified
                float kgthrust = (total_thrust/1000) * thrustChangedByAngle;
                float required_throttle = (Float.parseFloat(stringD.isEmpty()||stringD.equals("0")?"1":stringD)/1000) / kgthrust;//Simplified
                if (required_throttle>1){
                    required_throttle=1;
                    canFly="no";
                }
                else {
                    canFly="yes";
                }
                float avg_ampdraw=required_throttle*total_max_ampdraw;
                float battery_capacity_ah=battery_capacity_mah/1000;
                float time_in_hours=battery_capacity_ah/avg_ampdraw;
                float time_in_minutes=time_in_hours*60;
                float time_in_seconds=time_in_minutes*60;
                int tis = (int)Math.round(time_in_seconds);
//                displayFlightTime(tis);
                Intent DFT = new Intent(MainActivity.this, DisplayActivity.class);
                DFT.putExtra("FT_SECONDS",tis);
                DFT.putExtra("CAN_FLY",canFly);
                startActivity(DFT);
            }
        });
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
            case R.id.about_app:
                displayAboutApp();
                break;
            case R.id.open_on_gp:
                openOnGooglePlay();
                break;
        }
        return true;
    }
}