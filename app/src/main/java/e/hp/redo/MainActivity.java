package e.hp.redo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView startDate,endDate,startTime,endTime,startLocation,endLocation;
    Calendar calendar;
    static EditText pickupCity,dropoffCity;
    int day,year,hour,min,month;
    String []s={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    String minn=null,format=null,hr=null;
    static String totalKms;
    int resultDate;
    android.support.v7.widget.Toolbar main_toolbar;
    static LatLng startingLocationLatLng,endingLocationLatLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
        startDate=findViewById(R.id.startDate);
        endDate=findViewById(R.id.endDate);
        startTime=findViewById(R.id.startTime);
        endTime=findViewById(R.id.endTime);
        pickupCity=findViewById(R.id.pickupCity);
        dropoffCity=findViewById(R.id.dropOffCity);
        startLocation=findViewById(R.id.startingLocation);
        endLocation=findViewById(R.id.endingLocation);

        //toolbar
        main_toolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        getSupportActionBar().setTitle("Easy Car App");

    }


    public void dateSet(final View view) {
        Log.i("value of tag","::"+view.getTag().toString());
        if (view.getTag().equals("2")) {
            if (startDate.getText().toString().equals("Start Date")) {
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                Log.i("khali","yes");
                Log.i("day,month,year",day+" "+month+" "+year);
            } else {
                String date = startDate.getText().toString();
                String[] days = date.split(" ");
                day = Integer.parseInt(days[0]);
                String[] months = days[1].split(",");
                month = Arrays.asList(s).indexOf(months[0]);
                year = Integer.parseInt(days[2]);
                Log.i("khali","no");
                Log.i("day,month,year",day+" "+month+" "+year);
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    if (endDate.getText().toString().equals("End Date"))
                    startDate.setText(i2 + " " + s[i1] + ", " + i);
                    else {
                        String Fdate = endDate.getText().toString();
                        String[] Fdays = Fdate.split(" ");
                        int Fday = Integer.parseInt(Fdays[0]);
                        String[] months = Fdays[1].split(",");
                        int Fmonth = Arrays.asList(s).indexOf(months[0]);
                        int Fyear = Integer.parseInt(Fdays[2]);
                       resultDate=checkDate(Fday,Fmonth,Fyear,i2,i1,i);
                       Log.e("result 9999999",String.valueOf(resultDate));
                       if(resultDate==1||resultDate==5)
                           startDate.setText(i2 + " " + s[i1] + ", " + i);
                       else
                           Toast.makeText(MainActivity.this,"Inappropriate date",Toast.LENGTH_SHORT).show();
                    }
                    Log.i("startdate",startDate.getText().toString());
                }
            }, year, month, day);
            DatePicker dp = datePickerDialog.getDatePicker();
            dp.setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        }else {
            if (endDate.getText().toString().equals("End Date")) {
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                Log.i("khali","yes");
                Log.i("day,month,year",day+" "+month+" "+year);
            } else {
                String date = endDate.getText().toString();
                String[] days = date.split(" ");
                day = Integer.parseInt(days[0]);
                String[] months = days[1].split(",");
                month = Arrays.asList(s).indexOf(months[0]);
                year = Integer.parseInt(days[2]);
                Log.i("khali","no");
                Log.i("day,month,year",day+" "+month+" "+year);
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    if (startDate.getText().toString().equals("Start Date"))
                        endDate.setText(i2 + " " + s[i1] + ", " + i);
                    else {
                        String Fdate = startDate.getText().toString();
                        String[] Fdays = Fdate.split(" ");
                        int Fday = Integer.parseInt(Fdays[0]);
                        String[] months = Fdays[1].split(",");
                        int Fmonth = Arrays.asList(s).indexOf(months[0]);
                        int Fyear = Integer.parseInt(Fdays[2]);
                        resultDate=checkDate(Fday,Fmonth,Fyear,i2,i1,i);
                        Log.e("result 9999999",String.valueOf(resultDate));
                        if(resultDate==2||resultDate==5)
                            endDate.setText(i2 + " " + s[i1] + ", " + i);
                        else
                            Toast.makeText(MainActivity.this,"Inappropriate date",Toast.LENGTH_SHORT).show();
                    }
                    Log.i("enddate",endDate.getText().toString());
                }
            }, year, month, day);
            DatePicker dp = datePickerDialog.getDatePicker();
            dp.setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        }

    }

    public void timeSet(final View view) {
        if (!view.getTag().equals("1")) {
            if (startTime.getText().toString().equals("Start Time")) {
                calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);
            } else {
                String time=startTime.getText().toString();
                String[] form=time.split(" ");
                if (form[1].equals("PM")){
                    String [] hours=form[0].split(":");
                    hour=Integer.parseInt(hours[0])+12;
                    min=Integer.parseInt(hours[1]);
                }else {
                    String [] hours=form[0].split(":");
                    hour=Integer.parseInt(hours[0]);
                    min=Integer.parseInt(hours[1]);
                }
            }
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    if (i1 < 10)
                        minn = "0" + i1;
                    else
                        minn = String.valueOf(i1);
                    if (i > 12) {
                        i -= 12;
                        format = "PM";
                    } else if (i == 12) {
                        i = 12;
                        format = "PM";
                    } else if (i == 0) {
                        i = 12;
                        format = "AM";
                    } else
                        format = "AM";
                    if (String.valueOf(i).length() == 1)
                        hr = "0" + i;
                    else
                        hr = String.valueOf(i);
                    if (resultDate==5) {
                        if (endTime.getText().toString().equals("End Time"))
                            startTime.setText(hr + ":" + minn + " " + format);
                        else {
                            String Fhours[] = endTime.getText().toString().split(":");
                            String Fhour = Fhours[0];
                            String Fmins[] = Fhours[1].split(" ");
                            String Fmin = Fmins[0];
                            String Fformat = Fmins[1];
                            int result = timeCheck(Fhour, Fmin, Fformat, hr, minn, format);
                            if (result==2)
                                startTime.setText(hr + ":" + minn + " " + format);
                            else
                                Toast.makeText(MainActivity.this,"Inappropriate time",Toast.LENGTH_SHORT).show();
                        }
                    }else
                        startTime.setText(hr + ":" + minn + " " + format);
                }
            }, hour, min, false);
            timePickerDialog.show();
        }else {
            if (endTime.getText().toString().equals("End Time")) {
                calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);
            } else {
                String time=endTime.getText().toString();
                String[] form=time.split(" ");
                if (form[1].equals("PM")){
                    String [] hours=form[0].split(":");
                    hour=Integer.parseInt(hours[0])+12;
                    min=Integer.parseInt(hours[1]);
                }else {
                    String [] hours=form[0].split(":");
                    hour=Integer.parseInt(hours[0]);
                    min=Integer.parseInt(hours[1]);
                }
            }
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    if (i1 < 10)
                        minn = "0" + i1;
                    else
                        minn = String.valueOf(i1);
                    if (i > 12) {
                        i -= 12;
                        format = "PM";
                    } else if (i == 12) {
                        i = 12;
                        format = "PM";
                    } else if (i == 0) {
                        i = 12;
                        format = "AM";
                    } else
                        format = "AM";
                    if (String.valueOf(i).length() == 1)
                        hr = "0" + i;
                    else
                        hr = String.valueOf(i);
                    if (resultDate==5) {
                        if (startTime.getText().toString().equals("Start Time"))
                            endTime.setText(hr + ":" + minn + " " + format);
                        else {
                            String Fhours[] = startTime.getText().toString().split(":");
                            String Fhour = Fhours[0];
                            String Fmins[] = Fhours[1].split(" ");
                            String Fmin = Fmins[0];
                            String Fformat = Fmins[1];
                            int result = timeCheck(Fhour, Fmin, Fformat, hr, minn, format);
                            if (result==1)
                                endTime.setText(hr + ":" + minn + " " + format);
                            else
                                Toast.makeText(MainActivity.this,"Inappropriate time",Toast.LENGTH_SHORT).show();
                        }
                    }else
                        endTime.setText(hr + ":" + minn + " " + format);
                }
            }, hour, min, false);
            timePickerDialog.show();
        }
    }

    public void startingLocationSet(View view) {
        if (!pickupCity.getText().toString().isEmpty()) {
            Intent i1 = new Intent(this, MyMap.class);
            i1.putExtra("cityName", "1");
            startActivityForResult(i1, 1);
        }
        else
            Toast.makeText(getApplicationContext(),"You must filled PickUp City",Toast.LENGTH_LONG).show();
    }

    public void endingLocationSet(View view) {

        if (!dropoffCity.getText().toString().isEmpty()) {
            Intent i1 = new Intent(this, MyMap.class);
            i1.putExtra("cityName", "2");
            startActivityForResult(i1, 1);
        }
        else
            Toast.makeText(getApplicationContext(),"You must filled DropOff City",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==RESULT_OK){
            String set=data.getStringExtra("result");
            if (set.equals("1"))//||set.equals("2"))
            startLocation.setText(MyMap.setTextView(MyMap.setAddress,50));
           else {
                endLocation.setText(MyMap.setTextView(MyMap.setAddress, 45));
                if (startingLocationLatLng != null && endingLocationLatLng != null) {
                    float distance[] = new float[10];
                    Location.distanceBetween(startingLocationLatLng.latitude, startingLocationLatLng.longitude, endingLocationLatLng.latitude, endingLocationLatLng.longitude, distance);
                    DecimalFormat numberFormat=new DecimalFormat("#.00");
                    totalKms=numberFormat.format(distance[0]/1000f);
                    Toast.makeText(getApplicationContext(),"Total distance: "+totalKms+"kms",Toast.LENGTH_LONG).show();
                    Log.e("total distance::::", String.valueOf(totalKms));
                }
            }
        }
    }

    public void findCars(View view) {
        if(!pickupCity.getText().toString().equals("")&&!dropoffCity.getText().toString().equals("")&&!startTime.getText().toString().equals("Start Time")&&!endTime.getText().toString().equals("End Time")&&!startDate.getText().toString().equals("Start Date")&&!endDate.getText().toString().equals("End Date"))
        startActivity(new Intent(this,FindCars.class));
        else
            Toast.makeText(getApplicationContext(),"Fill the details first.",Toast.LENGTH_SHORT).show();
    }
    int  checkDate(int Cday,int Cmonth,int Cyear,int day,int month,int year){
        //return 2 when set date > preset date
        //return 1 when set date < preset date
        if(Cyear==year){
            if(Cmonth<month&&month-Cmonth<=2)
                return 2;
            else if(Cmonth>month&&Cmonth-month<=2)
                return 1;
            else {
                if (Cday<day)
                    return 2;
                else if (Cday>day)
                    return 1;
                else if(Cday==day)
                    return 5;
                else
                    return 0;
            }
        }
        else if (Cyear<year){
            return 2;
        }
        else
            return 1;
    }
    int timeCheck(String mhr,String mmin,String mfor,String chr,String cmin,String cfor) {
        //1 for endtime
        //2 for startTime
        if (cfor.equals("AM") && mfor.equals("AM") || cfor.equals("PM") && mfor.equals("PM")) {
            Log.e("btaoooo", "sahi jara");
            int myhr = Integer.parseInt(mhr);
            int tarhr = Integer.parseInt(chr);
            Log.e("hours ki value", String.valueOf(myhr) + " " + String.valueOf(tarhr));
            int mymin=Integer.parseInt(mmin);
            int tarmin=Integer.parseInt(cmin);
            if (myhr < tarhr)
                return 1;
            else if (myhr==tarhr){
                return 5;
            }
            else
                return 2;
        } else {
            if (mfor.equals("AM") && cfor.equals("PM")) {
                Log.e("btaoooo2222", "galat jara");
                return 1;
            } else {
                Log.e("btaoooo2222", "galat jara");
                return 2;
            }

        }
    }
}
