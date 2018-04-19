package com.example.android.uavuptimecalculator;

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
    float thrustChangedByAngle;
    //Views as member variables so that kept in memory until activity destroys.
    EditText Battery_Capacity,Amps_perMotor,Thrust_perMotor,Weight_ofDrone;
    Spinner typeOfDrone, sizeOfDrone;
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
        sizeOfDrone = (Spinner) findViewById(R.id.droneSize) ;

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
                        thrustChangedByAngle=0.7071f;
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
        //public void calculateFT()
        CalculateFTime = (Button) findViewById(R.id.calculate_flight_time);

        CalculateFTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Values from editTexts should be collected at the time of button click event.
                String stringA = Battery_Capacity.getText().toString();
                String stringB = Amps_perMotor.getText().toString();
                String stringC = Thrust_perMotor.getText().toString();
                String stringD = Weight_ofDrone.getText().toString();

// Removed null checks on strings as EditText never returns null as per android docs
//but empty checks are necessary to avoid app crash in case of parsing exception of empty string
                //float recperc = 80/100;
                float battery_capacity_mah =Float.parseFloat(!stringA.isEmpty()?stringA:"0") * 80/100;
                float total_thrust =  Integer.parseInt(!stringC.isEmpty()?stringC:"0") * number_of_rotors ;
                int total_max_ampdraw = Integer.parseInt(stringB.isEmpty()||stringB.equals("0")?"1":stringB) * number_of_rotors;//Simplified
                float kgthrust = (total_thrust/1000) * thrustChangedByAngle;
                float required_throttle = (Float.parseFloat(stringD.isEmpty()||stringD.equals("0")?"1":stringD)/1000) / kgthrust;//Simplified
                if (required_throttle>1){
                    required_throttle=1;
                }
                else{
                    required_throttle=required_throttle;
                }

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