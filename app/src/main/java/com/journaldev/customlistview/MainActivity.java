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

        dataModels= new ArrayList<>();

        dataModels.add(new DataModel("Apple Pie", "Android 1.0", "1","September 23, 2008"));
        dataModels.add(new DataModel("Banana Bread", "Android 1.1", "2","February 9, 2009"));
        dataModels.add(new DataModel("Cupcake", "Android 1.5", "3","April 27, 2009"));
        dataModels.add(new DataModel("Donut","Android 1.6","4","September 15, 2009"));
        dataModels.add(new DataModel("Eclair", "Android 2.0", "5","October 26, 2009"));
        dataModels.add(new DataModel("Froyo", "Android 2.2", "8","May 20, 2010"));
        dataModels.add(new DataModel("Gingerbread", "Android 2.3", "9","December 6, 2010"));
        dataModels.add(new DataModel("Honeycomb","Android 3.0","11","February 22, 2011"));
        dataModels.add(new DataModel("Ice Cream Sandwich", "Android 4.0", "14","October 18, 2011"));
        dataModels.add(new DataModel("Jelly Bean", "Android 4.2", "16","July 9, 2012"));
        dataModels.add(new DataModel("Kitkat", "Android 4.4", "19","October 31, 2013"));
        dataModels.add(new DataModel("Lollipop","Android 5.0","21","November 12, 2014"));
        dataModels.add(new DataModel("Marshmallow", "Android 6.0", "23","October 5, 2015"));

        paymentModel = new ArrayList<>();
        paymentModel.add(new Payment("New York Payment", "100", "10", 10, "Credit CARD", "USD", "1st May 2018","00:00", "Some ID", "New York", "Collected"));
        paymentModel.add(new Payment("New York Payment 2", "200", "10", 20, "Cash", "USD", "1st May 2018","00:00", "Some ID", "New York", "Collected"));


        adapter = new CustomAdapter(dataModels,getApplicationContext());
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

//                adapter.add(payment);

//                    Log.i("THE_CURRENT_USER:::", payment.toString());
//                    Log.i("Agent %:::", payment.getAgentPercentage());
//                    Log.i("Collection_amount :::", payment.getCollectionAmount());
//                    Log.i("Currency:::", payment.getCurrency());
//                    Log.i("Date %:::", payment.getDate());
//                    Log.i("ID %:::", payment.getId());
//                    Log.i("Location %:::", payment.getLocation());
//                    Log.i("Payment Type %:::", payment.getPaymentType());
//                    Log.i("Status %:::", payment.getStatus());
//                    Log.i("Time %:::", payment.getTime());
//                    Log.i("Title %:::", payment.getTitle());
//                    Log.i("Agent Amount:::", String.valueOf(payment.getAgentAmount()));

                    Payment payment1 = new Payment();
                    payment1.setAgentAmount(payment.getAgentAmount());
                    payment1.setAgentPercentage(payment.getAgentPercentage());
                    payment1.setCollectionAmount(payment.getCollectionAmount());
                    payment1.setCurrency(payment.getCurrency());
                    payment1.setDate(payment.getDate());
                    payment1.setId(payment.getId());
                    payment1.setLocation(payment.getLocation());
                    payment1.setPaymentType(payment.getPaymentType());
                    payment1.setStatus(payment.getStatus());
                    payment1.setTime(payment.getTime());
                    payment1.setDate(payment.getDate());
                    payment1.setTitle(payment.getTitle());

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

                DataModel dataModel= dataModels.get(position);
                Payment payment = paymentModel.get(position);

//                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();

                Intent intent = new Intent(MainActivity.this, List_Item.class);
                //Get the value of the item you clicked
//                String itemClicked = countries[position];
                intent.putExtra("paymentTitle", payment.getTitle());
                startActivity(intent);
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
