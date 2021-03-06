package com.example.ergasia2_android_20202021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity implements LocationListener ,Dialog.DialogListener{

    TextView textView6;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView textView4;
    private static final int REC_RESULT = 653;
    LocationManager locationManager;
    TextView textView7;
    TextView textView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView6 = findViewById(R.id.textView6);
        textView6.setText(getIntent().getStringExtra("key1"));
        radioGroup = findViewById(R.id.radioGroup);
        textView4 = findViewById(R.id.textView4);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)//???????????? ???????????? ???????????? ???????????????????? ????????????????
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.
                    requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},234);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                this);
        //locationManager.removeUpdates(this);
    }

    public void sendSMS(View view){//???????????????? ??????????????????

        String message = textView6.getText().toString();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!=//???????????? ???????????? ?????? ???????????????? SMS
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},5434);
        }else{
            if(radioGroup.getCheckedRadioButtonId() == -1){//???? ?????? ?????????????? checked radiobutton
                int foo = 0;
                try {
                    foo = Integer.parseInt(textView4.getText().toString());//???????????? ???????????????????? ?????? ?????????????????? ?????? ???????????? ???? ????????????
                    String a = textView4.getText().toString();
                    if(textView7.getText().toString().equals("") && textView8.getText().toString().equals("")){//???? ?????? ?????????? ?????????????????? ?????????? ?????? textViews
                        HashMap<String, Object> map = new HashMap<>();//?????????????????????? map ?????? ???????????????? ?????????????????? ?????????? ???????? ????????
                        map.put("Location","null");
                        map.put("Timestamp","null");
                        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();//???????????? id ?????? ???????????? ?????? ???????? ???????? ???????? ????????????????
                        FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).push().updateChildren(map);//?????????????? ???????? ????????
                    }
                    else{
                        HashMap<String, Object> map = new HashMap<>();//?????????????????????? map ?????? ???????????????? ?????????????????? ?????????? ???????? ????????
                        map.put("Location",textView7.getText().toString());
                        map.put("Timestamp",textView8.getText().toString());
                        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();//???????????? id ?????? ???????????? ?????? ???????? ???????? ???????? ????????????????
                        FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).push().updateChildren(map);//?????????????? ???????? ????????
                    }
                    SmsManager manager = SmsManager.getDefault();
                    manager.sendTextMessage("13033",null,a+" "+message,null,null);//???????????????? ??????????????????
                    Toast.makeText(this,"???????????? ????????????!",Toast.LENGTH_LONG).show();
                }
                catch (NumberFormatException e)
                {
                    Toast.makeText(this,"???????????????? ???????????????? ????????????!",Toast.LENGTH_LONG).show();
                }
            }
            else{
                int radioId = radioGroup.getCheckedRadioButtonId();//???????????? checked radiobutton
                radioButton = findViewById(radioId);//???????????? ?????? id ?????? ?????????????????????????? ????????????????
                String s = radioButton.getText().toString().replaceAll("\\D+","");//???????????????? ???????? ?????? ?????????????????? ?????? text ?????? ????????????????
                if(textView7.getText().toString().equals("") && textView8.getText().toString().equals("")){//???? ?????? ?????????? ?????????????????? ?????????? ?????? textViews
                    HashMap<String, Object> map = new HashMap<>();//?????????????????????? map ?????? ???????????????? ?????????????????? ?????????? ???????? ????????
                    map.put("Location","null");
                    map.put("Timestamp","null");
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();//???????????? id ?????? ???????????? ?????? ???????? ???????? ???????? ????????????????
                    FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).push().updateChildren(map);//?????????????? ???????? ????????
                }
                else{
                    HashMap<String, Object> map = new HashMap<>();//?????????????????????? map ?????? ???????????????? ?????????????????? ?????????? ???????? ????????
                    map.put("Location",textView7.getText().toString());
                    map.put("Timestamp",textView8.getText().toString());
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();//???????????? ?????? id ?????? ?????????????????????????? ????????????????
                    FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).push().updateChildren(map);//?????????????? ???????? ????????
                }
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage("13033",null,s+" "+message,null,null);//???????????????? ??????????????????
                Toast.makeText(this,"???????????? ????????????!",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//???????????????? ?????????????????? ?????????????????? ???????? ??????????
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REC_RESULT && resultCode==RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            textView4.setText(matches.get(0));
            if (matches.contains("red"))
                getWindow().getDecorView().setBackgroundColor(Color.RED);
            if (matches.contains("blue"))
                getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            if (matches.contains("yellow"))
                getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
        }

    }

    public void recognize(View view){//???????????????? ???????????????? ??????????????
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"???????????????? ?????????????????? ????????????!");
        startActivityForResult(intent,REC_RESULT);
    }

    @Override
    public void onLocationChanged(Location location) {
        Double  x = location.getLatitude();//???????????? ?????????????????????? ?????????????????????????? ??
        Double y = location.getLongitude();//???????????? ?????????????????????? ?????????????????????????? Y
        textView7.setText(String.valueOf(x)+","+String.valueOf(y));
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());//???????????? timestamp
        textView8.setText(String.valueOf(formatter.format(date)));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void open_dialog(View view){
        openDialog();
    }

    public void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void applyText(String code) {
        int radioId = radioGroup.getCheckedRadioButtonId();//???????????? checked radiobutton
        radioButton = findViewById(radioId);//???????????? ?????? id ?????? ?????????????????????????? ????????????????
        radioButton.setText(code);//???????????? ?????? text ?????? checked radiobutton
    }
}