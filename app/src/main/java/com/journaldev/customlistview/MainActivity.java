package com.journaldev.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ArrayList<Payment> paymentModel;
    ListView listView;
    private static CustomAdapter adapter;
    private static CustomPaymentAdapter paymentAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mPaymentsDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.list);

        paymentModel = new ArrayList<>();

        paymentAdapter = new CustomPaymentAdapter(paymentModel, getApplicationContext());

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mPaymentsDatabaseReference = mFirebaseDatabase.getReference().child("flamelink/environments/production/content/schemaDemo/en-US");

        Log.i("mPaymentsDB:::", mPaymentsDatabaseReference.toString());


        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getValue() != null) {

                    Payment payment = dataSnapshot.getValue(Payment.class);

                    Log.i("THE_CURRENT_USER:::", payment.toString());

                    paymentAdapter.add(payment);

                }else {
                    Log.i("THE_CURRENT_USER:::", "dataSnapshot is null");
                }
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


        listView.setAdapter(paymentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Payment payment = paymentModel.get(position);

                Intent intent = new Intent(MainActivity.this, List_Item.class);

                intent.putExtra("title", payment.getTitle());
                intent.putExtra("agentAmount", payment.getAgentAmount());
                intent.putExtra("agentPercentage", payment.getAgentPercentage());
                intent.putExtra("collectionAmount", payment.getCollectionAmount());
                intent.putExtra("currency", payment.getCurrency());
                intent.putExtra("date", payment.getDate());
                intent.putExtra("time", payment.getTime());
                intent.putExtra("id", payment.getId());
                intent.putExtra("location", payment.getLocation());
                intent.putExtra("paymentType", payment.getPaymentType());
                intent.putExtra("status", payment.getStatus());
                intent.putExtra("__meta__", (Serializable) payment.get__meta__());

                startActivity(intent);

                finish();
            }
        });


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
}
