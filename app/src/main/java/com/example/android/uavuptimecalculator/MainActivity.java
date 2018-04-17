package com.example.android.uavuptimecalculator;
/*
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
//import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int number_of_rotors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner typeOfDrone = (Spinner) findViewById(R.id.droneType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        typeOfDrone.setAdapter(adapter);

      //  ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
        //        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types));
        //myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //typeOfDrone.setAdapter(myAdapter);
        //   typeOfDrone.setSelection(1);


        final String drone_type = typeOfDrone.getSelectedItem().toString();

        if (drone_type=="Tricopter") {
            number_of_rotors=3;
        }
        else if (drone_type=="Quadcopter") {
            number_of_rotors=4;
        }
        else if (drone_type=="Hexacopter") {
            number_of_rotors=6;
        }
        else if (drone_type=="Octacopter") {
            number_of_rotors=8;
        }
        /*if (required_throttle>=1){
            Toast.makeText(MainActivity.this,"UAV will not take off",Toast.LENGTH_SHORT);
        }*/

        //String abc = Float.toString(avg_ampdraw);
       // Toast.makeText(MainActivity.this,abc,Toast.LENGTH_SHORT);
        //Toast.makeText(MainActivity.this,avg_ampdraw,Toast.LENGTH_SHORT);

        //public void calculateFT()
   /*     Button CalculateFTime = (Button) findViewById(R.id.calculate_flight_time);
        CalculateFTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Battery_Capacity = (EditText) findViewById(R.id.batteryCapacity);
                String stringA  = Battery_Capacity.getText().toString();
                if (stringA==null){
                    stringA="0";
                }
                EditText Amps_perMotor = (EditText) findViewById(R.id.MAPM);
                String stringB = Amps_perMotor.getText().toString();
                if (stringB==null){
                    stringB="0";
                }
                EditText Thrust_perMotor = (EditText) findViewById(R.id.MTPM);
                String stringC = Thrust_perMotor.getText().toString();
                if (stringC==null){
                    stringC="0";
                }
                EditText Weight_ofDrone = (EditText) findViewById(R.id.droneWeight);
                String stringD =  Weight_ofDrone.getText().toString();
                if (stringD==null){
                    stringD="0";
                }
                //int battery_capacity_mah =Integer.parseInt(stringA!=null&&!stringA.isEmpty()?stringA:"0");
                int battery_capacity_mah =Integer.parseInt(stringA);
                //int battery_capacity_mah = Integer.parseInt(Battery_Capacity.toString());
                //int total_max_ampdraw = Integer.parseInt(Amps_perMotor.toString()) * number_of_rotors;
                int total_max_ampdraw = Integer.parseInt(stringB) * number_of_rotors;
                //int total_thrust = Integer.parseInt(Thrust_perMotor.toString()) * number_of_rotors;
                int total_thrust =  Integer.parseInt(stringC) * number_of_rotors;
                //float required_throttle = Integer.parseInt(Weight_ofDrone.toString()) / total_thrust;
                float required_throttle = Float.parseFloat(stringD) / total_thrust;

                float avg_ampdraw=required_throttle*total_max_ampdraw;
                float battery_capacity_ah=battery_capacity_mah/1000;
                float time_in_hours=battery_capacity_ah/avg_ampdraw;
                float time_in_minutes=time_in_hours*60;
                float time_in_seconds=time_in_minutes*60;

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("Calculated Flight Time");
                //mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMessage("Your UAV will fly for "+time_in_seconds+" seconds, which is equivalent to "+time_in_minutes+" minutes. Avg amp draw is "+avg_ampdraw+" battery capacity is "+battery_capacity_ah+" max ampdraw is "+total_max_ampdraw+" number of rotors is "+drone_type+number_of_rotors);
                mBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = mBuilder.create();
                alertDialog.show();
            }
        });


    }


}
*/

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int number_of_rotors;

    //Views as member variables so that kept in memory until activity destroys.
    EditText Battery_Capacity,Amps_perMotor,Thrust_perMotor,Weight_ofDrone;
    Spinner typeOfDrone;
    Button CalculateFTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Battery_Capacity = (EditText) findViewById(R.id.batteryCapacity);
        Amps_perMotor = (EditText) findViewById(R.id.MAPM);
        Thrust_perMotor = (EditText) findViewById(R.id.MTPM);
        Weight_ofDrone = (EditText) findViewById(R.id.droneWeight);
        typeOfDrone = (Spinner) findViewById(R.id.droneType);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeOfDrone.setAdapter(myAdapter);
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

//        typeOfDrone.setSelection(1);

        //public void calculateFT()
        CalculateFTime = (Button) findViewById(R.id.calculate_flight_time);

        CalculateFTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Values from editTexts should be collected at the time of button click event.
                String stringA  = Battery_Capacity.getText().toString();
                String stringB = Amps_perMotor.getText().toString();
                String stringC = Thrust_perMotor.getText().toString();
                String stringD = Weight_ofDrone.getText().toString();

// Removed null checks on strings as EditText never returns null as per android docs
//but empty checks are necessary to avoid app crash in case of parsing exception of empty string
                int battery_capacity_mah =Integer.parseInt(!stringA.isEmpty()?stringA:"0");
                int total_thrust =  Integer.parseInt(!stringC.isEmpty()?stringC:"0") * number_of_rotors;//no usage found, so did not confirm it
                int total_max_ampdraw = Integer.parseInt(stringB.isEmpty()||stringB.equals("0")?"1":stringB) * number_of_rotors;//Simplified
                float kgthrust = total_thrust/1000;
                float required_throttle = (Float.parseFloat(stringD.isEmpty()||stringD.equals("0")?"1":stringD)/1000) / kgthrust;//Simplified
                //these two variables on RHS must not be zero, so added checks while parsing
                //In case, Amps_perMotor or Weight_ofDrone were empty, you should validate them to not to be.
                float avg_ampdraw=required_throttle*total_max_ampdraw;

                String abc = Float.toString(avg_ampdraw);
                Toast.makeText(MainActivity.this,abc,Toast.LENGTH_SHORT);

                float battery_capacity_ah=battery_capacity_mah/1000;
                float time_in_hours=battery_capacity_ah/avg_ampdraw;
                float time_in_minutes=time_in_hours*60;
                float time_in_seconds=time_in_minutes*60;
                int tis = (int)Math.round(time_in_seconds);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Calculated Flight Time")
                        .setMessage("Your UAV will fly for "+tis+" seconds, which is equivalent to "+time_in_minutes+" minutes." /*+ "No. of rotors is "+number_of_rotors+" Reqd throt. is "+required_throttle+" total max amps is "+total_max_ampdraw+"weight is "+stringD+" avg amp draw is "+avg_ampdraw+" total thrust is "+total_thrust*/)
                        .setNeutralButton("OK",null)
                        .show();
            }
        });
    }
}