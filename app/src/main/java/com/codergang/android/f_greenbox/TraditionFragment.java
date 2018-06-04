package com.codergang.android.f_greenbox;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TraditionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TraditionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TraditionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference isCoolDown = database.getReference("Cool Down");
    private DatabaseReference isWater = database.getReference("Water");
    private DatabaseReference isWarm = database.getReference("Warm");
    private DatabaseReference caseSelected = database.getReference("Case Selected");
    private DatabaseReference led = database.getReference("Led");
    private DatabaseReference temperature = database.getReference("Temperature");
    private DatabaseReference humidity = database.getReference("Humidity");





    public TraditionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TraditionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TraditionFragment newInstance(String param1, String param2) {
        TraditionFragment fragment = new TraditionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RadioGroup caseGroup;
    public Button coolDownButton;
    public Button warmButton;
    public Button waterButton;
    public Switch ledLightSwitch;
    public TextView tempTrad;
    public TextView humidTrad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tradition, container, false);
        caseGroup = v.findViewById(R.id.case_group);
        caseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.case_1:
                        caseSelected.setValue("1");
                        break;
                    case  R.id.case_2:
                        caseSelected.setValue("2");
                        break;
                    case R.id.case_3:
                        caseSelected.setValue("3");
                        break;
                }
            }
        });
        coolDownButton = v.findViewById(R.id.cool_down_button);
        coolDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isCoolDown.setValue(true);
            }
        });
        warmButton = v.findViewById(R.id.warm_button);
        warmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWarm.setValue(true);
            }
        });
        waterButton = v.findViewById(R.id.water_button);
        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWater.setValue(true);
            }
        });
        ledLightSwitch = v.findViewById(R.id.led_light);
        led.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean state = (boolean) dataSnapshot.getValue();
                ledLightSwitch.setChecked(state);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ledLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    led.setValue(true);
                } else{
                    led.setValue(false);
                }
            }
        });
        tempTrad = v.findViewById(R.id.temperature_soil);
        humidTrad = v.findViewById(R.id.humidity_soil);

        temperature.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp =  dataSnapshot.getValue(String.class);
                tempTrad.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tempTrad.setText(getString(R.string.error));
            }
        });

        humidity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String humid = dataSnapshot.getValue(String.class);
                humidTrad.setText(humid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                humidTrad.setText(getString(R.string.error));
            }
        });



        return v;
    }







}
