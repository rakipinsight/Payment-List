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
import java.util.HashMap;
import java.util.Locale;

public class List_Item extends AppCompatActivity{

    private TextView mTextMessage;

    private ImageButton increment_col_amt;
    private ImageButton decrement_col_amt;
    private TextView col_amt;

    private ImageButton increment_agent_percent;
    private ImageButton decrement_agent_percent;
    private TextView agent_percent;

    private ImageButton increment_agent_amt;
    private ImageButton decrement_agent_amt;
    private TextView agent_amt;

    private EditText date_Calendar_EditText;
    private Calendar myCalendar = Calendar.getInstance();
    private EditText time_EditText;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mPaymentsDatabaseReference;
    private ChildEventListener mChildEventListener;

    private Button updateButton;

    private ListView mMessageListView;

    private TextView title_TextView;

    private Spinner dynamicPaymentSpinner;

    private String[] items;

    private ArrayAdapter<String> paymentAdapter;

    private EditText payment_id_editText;
    private EditText location_editText;

    private Spinner currencyDynamicSpinner;

    private String[] currency_items;

    private ArrayAdapter<String> currency_adapter;

    private Spinner statusDynamicSpinner;

    private String[] status_items;

    private ArrayAdapter<String> status_adapter;

    private Intent intent;

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
        date_Calendar_EditText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        intent = getIntent();

        // Payment Title

        title_TextView = (TextView) findViewById(R.id.title_TextView);
        title_TextView.setText(intent.getStringExtra("title"), TextView.BufferType.EDITABLE);

        // Collection Amount

        col_amt = (TextView) findViewById(R.id.collection_amt_editText);
        col_amt.setText(intent.getStringExtra("collectionAmount"), TextView.BufferType.EDITABLE);

        // Agent Percentage

        agent_percent = (TextView) findViewById(R.id.agent_percent_editText);
        agent_percent.setText(intent.getStringExtra("agentPercentage"), TextView.BufferType.EDITABLE);

        // Agent Amount

        agent_amt = (TextView) findViewById(R.id.agent_amt_TextView);
        agent_amt.setText(String.valueOf(intent.getIntExtra("agentAmount", 0)));

        // ID

        payment_id_editText = (EditText) findViewById(R.id.payment_id_edit_text);
        payment_id_editText.setText(Long.toString((intent.getLongExtra("id", 0l))));

        // Location

        location_editText = (EditText) findViewById(R.id.location_edit_text);
        location_editText.setText(intent.getStringExtra("location"), TextView.BufferType.EDITABLE);

        /* Firebase Code */

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mPaymentsDatabaseReference = mFirebaseDatabase.getReference().child("flamelink/environments/production/content/schemaDemo/en-US");

        // Update Button Logic
        updateButton = (Button) findViewById(R.id.update_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click

//                Payment payment = new Payment();
//
//                payment.setTitle(title_TextView.getText().toString());
//                payment.setCollectionAmount(col_amt.getText().toString());
//                payment.setAgentPercentage(agent_percent.getText().toString());
//                payment.setAgentAmount(Integer.parseInt(agent_amt.getText().toString()));
//                payment.setPaymentType(dynamicPaymentSpinner.getSelectedItem().toString());
//                payment.setCurrency(currencyDynamicSpinner.getSelectedItem().toString());
//                payment.setDate(date_Calendar_EditText.getText().toString());
//                payment.setTime(time_EditText.getText().toString());
//
//                payment.setId(Long.parseLong(payment_id_editText.getText().toString()));
//                payment.setLocation(location_editText.getText().toString());
//                payment.setStatus(statusDynamicSpinner.getSelectedItem().toString());
//
//                mPaymentsDatabaseReference.push().setValue(payment);

                HashMap<String, Object> result = new HashMap<>();

                result.put("title", title_TextView.getText().toString());
                result.put("collectionAmount", col_amt.getText().toString());
                result.put("agentPercentage", agent_percent.getText().toString());
                result.put("agentAmount", Integer.parseInt(agent_amt.getText().toString()));
                result.put("paymentType", dynamicPaymentSpinner.getSelectedItem().toString());
                result.put("currency", currencyDynamicSpinner.getSelectedItem().toString());
                result.put("date", date_Calendar_EditText.getText().toString());
                result.put("time", time_EditText.getText().toString());
                result.put("id", Long.parseLong(payment_id_editText.getText().toString()));
                result.put("location", location_editText.getText().toString());
                result.put("status", statusDynamicSpinner.getSelectedItem().toString());

                HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("__meta__");
                result.put("__meta__", hashMap);

                mPaymentsDatabaseReference.child(payment_id_editText.getText().toString()).updateChildren(result);

                Intent intent = new Intent(List_Item.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Payment payment = dataSnapshot.getValue(Payment.class);

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

        // Payment Type Drop-Down Menu

        dynamicPaymentSpinner = (Spinner) findViewById(R.id.payment_dynamic_spinner);

        items = new String[] { "Cash", "Credit Card", "Online Banking" };

        paymentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        dynamicPaymentSpinner.setAdapter(paymentAdapter);

        dynamicPaymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        int paymentTypeSelectionPosition = paymentAdapter.getPosition(intent.getStringExtra("paymentType"));
        dynamicPaymentSpinner.setSelection(paymentTypeSelectionPosition);

        // Currency Spinner

        currencyDynamicSpinner = (Spinner) findViewById(R.id.currency_dynamic_spinner);

        currency_items = new String[] { "Rupee(s)", "US Dollar(s)" };

        currency_adapter = new ArrayAdapter<String>(this,
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

        int currencySelectionPosition = currency_adapter.getPosition(intent.getStringExtra("currency"));
        currencyDynamicSpinner.setSelection(currencySelectionPosition);

        // Status Spinner Logic

        statusDynamicSpinner = (Spinner) findViewById(R.id.status_dynamic_spinner);

        status_items = new String[] { "Open", "Assigned", "Collected" };

        status_adapter = new ArrayAdapter<String>(this,
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

        int statusSelectionPosition = status_adapter.getPosition(intent.getStringExtra("status"));
        statusDynamicSpinner.setSelection(statusSelectionPosition);

        // Date Picker Logic

        date_Calendar_EditText = (EditText) findViewById(R.id.calendar_date);
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

        date_Calendar_EditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(List_Item.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date_Calendar_EditText.setText(intent.getStringExtra("date"), TextView.BufferType.EDITABLE);

        // Time Picker Logic

        //  initiate the edit text
        time_EditText = (EditText) findViewById(R.id.time);
        // perform click event listener on edit text
        time_EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(List_Item.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_EditText.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time_EditText
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        time_EditText.setText(intent.getStringExtra("time"), TextView.BufferType.EDITABLE);

    }

}
