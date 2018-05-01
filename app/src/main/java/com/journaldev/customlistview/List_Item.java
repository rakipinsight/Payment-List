package com.journaldev.customlistview;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class List_Item extends AppCompatActivity{

    private TextView mTextMessage;

    private ImageButton increment_col_amt;
    private ImageButton decrement_col_amt;
    private EditText col_amt;

    private ImageButton increment_agent_percent;
    private ImageButton decrement_agent_percent;
    private EditText agent_percent;

    private ImageButton increment_agent_amt;
    private ImageButton decrement_agent_amt;
    private TextView agent_amt;
    private EditText calendar_Edit_Text;
    Calendar myCalendar = Calendar.getInstance();
    EditText time;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mPaymentsDatabaseReference;
    private ChildEventListener mChildEventListener;

    private Button updateButton;

    private ListView mMessageListView;
//    private MessageAdapter mMessageAdapter;

    public Double calculateAgentAmount(Double principal, Double percent){

        BigDecimal base = new BigDecimal(principal);
        BigDecimal percentage = new BigDecimal(percent);

        return base.multiply(percentage).divide(new BigDecimal(100)).doubleValue();

    }

    public void updateAgentAmountTextView(){
        if(!col_amt.getText().toString().isEmpty() && (!agent_percent.getText().toString().isEmpty())){
            Double agent_amount = calculateAgentAmount(Double.parseDouble(col_amt.getText().toString()),Double.parseDouble(agent_percent.getText().toString()));
            agent_amt.setText(" "+agent_amount);
        }
    }

    public void incrementWidgetLogic(EditText editText){
        Double result = Double.parseDouble(editText.getText().toString());
        result++;
        editText.setText(" "+result);
    }

    public void decrementWidgetLogic(EditText editText){
        Double result = Double.parseDouble(editText.getText().toString());
        if(result > 0.0d){
            result--;
        }
        editText.setText(" "+result);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        calendar_Edit_Text.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

//        mTextMessage = (TextView) findViewById(R.id.message);

//        mMessageListView = (ListView) findViewById(R.id.messageListView);
//        // Initialize message ListView and its adapter
//        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
//        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
//        mMessageListView.setAdapter(mMessageAdapter);

        /* Firebase Code */

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mPaymentsDatabaseReference = mFirebaseDatabase.getReference().child("flamelink/environments/production/content/schemaDemo/en-US");

        // Update Button Logic
        updateButton = (Button) findViewById(R.id.update_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
//                BigInteger bigInteger = new BigInteger("1524240866140");
                Payment payment = new Payment("Payment 2 Kansas", 40000, 10, 4000,
                        "Cash", "Rupee", "27th April 2018", "12:30", "1524240866180", "Kansas", "Collected");
//                payment.setTitle("Payment 2 Kansas");
//                payment.setCollection_amount(40000);
//                payment.setAgent_percentage(10);
//                payment.setAgent_amount(4000);
//                payment.setPaymentType("Cash");
//                payment.setCurrency("Rupee");
//                payment.setDate("26th April 2018");
//                payment.setTime("12:30");
//
//                payment.setId(bigInteger);
//                payment.setLocation("Kansas");
//                payment.setStatus("Collected");

                mPaymentsDatabaseReference.push().setValue(payment);

//                HashMap<String, Object> result = new HashMap<>();
//                result.put("title", "Payment 2 Kansas");
//                result.put("collectionAmount", 40000);
//
//
//                mPaymentsDatabaseReference.child("1524240866140").updateChildren(result);
            }
        });

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Payment payment = dataSnapshot.getValue(Payment.class);
//                mMessageAdapter.add(payment);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mPaymentsDatabaseReference.addChildEventListener(mChildEventListener);

//        updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO: Update payments on click
//                Payment payment = new Payment();
//                payment.setTitle("Collect From Kansas");
//                payment.setCollection_amount(40000);
//                payment.setAgent_percentage(10);
//                payment.setAgent_amount(4000);
//                payment.setPaymentType("Cash");
//                payment.setCurrency("Rupee");
//                payment.setDate("April 26, 2018");
//                payment.setTime("11:50");
////                payment.setId(1524240866140);
//
//
//        })


        Spinner dynamicSpinner = (Spinner) findViewById(R.id.payment_dynamic_spinner);

        String[] items = new String[] { "Cash", "Credit Card", "Online Banking" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Spinner currencyDynamicSpinner = (Spinner) findViewById(R.id.currency_dynamic_spinner);

        String[] currency_items = new String[] { "Rupee" };

        ArrayAdapter<String> currency_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currency_items);

        currencyDynamicSpinner.setAdapter(currency_adapter);

        currencyDynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        // Status Spinner Logic

        Spinner statusDynamicSpinner = (Spinner) findViewById(R.id.status_dynamic_spinner);

        String[] status_items = new String[] { "Open", "Assigned", "Collected" };

        ArrayAdapter<String> status_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, status_items);

        statusDynamicSpinner.setAdapter(status_adapter);

        statusDynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        // Date Picker Logic

        calendar_Edit_Text= (EditText) findViewById(R.id.calendar_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        calendar_Edit_Text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(List_Item.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Time Picker Logic

        //  initiate the edit text
        time = (EditText) findViewById(R.id.time);
        // perform click event listener on edit text
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(List_Item.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

    }

}
