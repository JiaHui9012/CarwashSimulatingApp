package com.example.carwashsimulatingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    //views
    TextView carPlateTV, timeRemainingTV, totalQueueTV;
    Button dropOffButton, pickUpButton;
    GifImageView gifImageView;
    //progress dialog
    ProgressDialog progressDialog;
    //class
    QUEUE queue = new QUEUE();
    STACK history = new STACK();
    CarPlate carPlate;
    //create a list to store details
    List<CarPlate> list = new ArrayList<CarPlate>();
    //count down timer
    CountDownTimer mCountDownTimer;
    //check if timer finished, default true
    boolean isFinished = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        carPlateTV = findViewById(R.id.carPlateTextView);
        timeRemainingTV = findViewById(R.id.timeRemainingTextView);
        totalQueueTV = findViewById(R.id.totalQueueTextView);
        dropOffButton = findViewById(R.id.dropOff_button);
        pickUpButton = findViewById(R.id.pickUp_button);
        gifImageView = findViewById(R.id.gifImageView);

        //progress dialog
        progressDialog = new ProgressDialog(this);

        //create timer
        mCountDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isFinished = false;
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                timeRemainingTV.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
            }
            @Override
            public void onFinish() {
                isFinished = true;
                //dequeue
                String dequeuedValue = queue.dequeue();
                //add the dequeued value into the stack
                history.enqueue(dequeuedValue);
                // if queue is empty
                if (queue.size() == 0) {
                    carPlateTV.setText("No car is being washed :(");
                    totalQueueTV.setText("0");
                    gifImageView.setImageResource(R.drawable.carwash2);
                }
                // else start timer
                else {
                    String[] tempQueue = queue.getQueue();
                    carPlateTV.setText(tempQueue[0]);
                    totalQueueTV.setText(Integer.toString(queue.size()-1));
                    mCountDownTimer.start();
                }
            }
        };

        //handle drop off button
        dropOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Adding your car plate to the queue...");
                showEditTextDialog("Drop Off");
            }
        });

        //handle pick up button
        pickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Checking the current status of your car...");
                showEditTextDialog("Pick Up");
            }
        });
    }

    //menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();
        switch(id) {
            case R.id.queue_list:
                //get the queue, stack, list
                String[] queueList = null;
                String[] historyList = null;
                ArrayList<CarPlate> detailsList = new ArrayList<CarPlate>();
                progressDialog.setMessage("Add Post");
                if (queue.size() != 0) {
                    queueList = queue.getQueue();
                }
                if (history.size() != 0) {
                    historyList = history.getQueue();
                }
                if (list.size() != 0) {
                    detailsList.addAll(list);
                }
                // pass the queue, stack, list to new activity
                Bundle bundle = new Bundle();
                bundle.putStringArray("queue_list", queueList);
                bundle.putStringArray("history_list", historyList);
                bundle.putParcelableArrayList("details_list", detailsList);
                Intent intent = new Intent(MainActivity.this, ViewQueueActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // show dialog for user to key in car plate and ticket number
    private void showEditTextDialog(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(key);
        //set layout of dialog
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);
        //add edit text
        EditText carPlateEditText = new EditText(this);
        carPlateEditText.setHint("Enter Your Car Plate");
        linearLayout.addView(carPlateEditText);
        EditText ticketNumberEditText = new EditText(this);
        if (key == "Pick Up") {
            ticketNumberEditText.setHint("Enter Your Ticket Number");
            linearLayout.addView(ticketNumberEditText);
        }

        builder.setView(linearLayout);

        //add confirm button in dialog
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input text from edit text
                String carPlateValue = carPlateEditText.getText().toString().trim();

                if (key == "Drop Off") {
                    //validate if user has entered something or not
                    if(!TextUtils.isEmpty(carPlateValue)) {
                        int index = -1;
                        boolean isInQueue = false;
                        //check if the entered car plate is in the details list, if yes, get the index
                        for (int i = 0; i < list.size(); i++) {
                            if(list.get(i).getCarPlate().equals(carPlateValue)){
                                index = i;
                                break;
                            }
                        }
                        //check if the entered car plate is in the queue, if yes, return true
                        if (queue.size() > 0) {
                            String[] tempQueue = queue.getQueue();
                            for (int i = 0; i < queue.size(); i++) {
                                if (carPlateValue.equals(tempQueue[i])) {
                                    isInQueue = true;
                                    break;
                                }
                            }
                        }
                        // if the entered car plate value is not in queue or queue is empty
                        if (!isInQueue) {
                            String ticketNumber;
                            // if the entered car plate value is not in list or list is empty
                            if (index < 0) {
                                // enqueue and generate ticket number
                                ticketNumber = enqueueCarPlate(carPlateValue);
                                //store details (car plate, ticket number, has picked up or not) into a list
                                carPlate = new CarPlate(carPlateValue, ticketNumber, false);
                                list.add(carPlate);
                            }
                            // if the entered car plate has already been in list
                            else {
                                // if the car has picked up before
                                if (list.get(index).getHasPickedUp()) {
                                    // enqueue and generate ticket number
                                    ticketNumber = enqueueCarPlate(carPlateValue);
                                    // modify the details in the list
                                    list.get(index).setHasPickedUp(false);
                                    list.get(index).setTicketNumber(ticketNumber);
                                }
                                // if the car has not picked up since in the list
                                else {
                                    showMessageDialogBox("Failed to Drop Off...\nThe car has not been picked up yet since washed.");
                                    //Toast.makeText(MainActivity.this, "The car has not picked up yet", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        // entered car plate value is currently already in the queue
                        else {
                            showMessageDialogBox("Failed to Drop Off...\nThe car is already in the queue.");
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please Enter Your Car Plate", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (key == "Pick Up") {
                    //input text from edit text
                    String ticketNumberValue = ticketNumberEditText.getText().toString().trim();
                    //validate if user has entered something or not
                    if(!TextUtils.isEmpty(carPlateValue) && !TextUtils.isEmpty(ticketNumberValue)) {
                        progressDialog.show();
                        int index = -1;
                        //check if the entered car plate is in the details list, if yes, get the index
                        for (int i = 0; i < list.size(); i++) {
                            if(list.get(i).getCarPlate().equals(carPlateValue)){
                                index = i;
                                break;
                            }
                        }
                        // the entered car plate is not in the list
                        if (index < 0) {
                            //carplate not valid
                            showMessageDialogBox("Failed to Pick Up...\nThis car has not been dropped off yet.");
                        }
                        else {
                            // if the ticket number entered is correct
                            if(list.get(index).getTicketNumber().equals(ticketNumberValue)){
                                // if the car has not picked up
                                if (!list.get(index).getHasPickedUp()) {
                                    boolean isInStack = false;
                                    // if the stack is not empty
                                    if (history.size() != 0) {
                                        String[] tempStack = history.getQueue();
                                        // check if the car is in the stack (washed)
                                        for (int i = 0; i < tempStack.length; i++) {
                                            if(carPlateValue.equals(tempStack[i])){
                                                isInStack = true;
                                                //car is washed, can pick up
                                                showMessageDialogBox("Picked Up Successful!\nThank you :)\nCome back again.");
                                                //Toast.makeText(MainActivity.this, "Picked Up Successful", Toast.LENGTH_SHORT).show();
                                                //update status picked up in the list
                                                list.get(index).setHasPickedUp(true);
                                                break;
                                            }
                                        }
                                    }
                                    // if the car not in stack
                                    if (!isInStack) {
                                        // check if the car in the queue
                                        if (queue.size() != 0) {
                                            String[] tempQueue = queue.getQueue();
                                            // if the car is the first element in the queue (car is being washed)
                                            if (carPlateValue.equals(tempQueue[0])) {
                                                //car is being washed
                                                showMessageDialogBox("Failed to Pick Up...\nYour car is being washed now.");
//                                                Toast.makeText(MainActivity.this, "Your car is being washed", Toast.LENGTH_SHORT).show();
                                            }
                                            // car still in queue
                                            else {
                                                for (int i = 1; i < tempQueue.length; i++) {
                                                    if(carPlateValue.equals(tempQueue[i])){
                                                        //car is still in queue
                                                        showMessageDialogBox("Failed to Pick Up...\nYour car is still in queue.");
//                                                        Toast.makeText(MainActivity.this, "Your car is still in queue", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    //the car has already picked up
                                    showMessageDialogBox("Failed to Pick Up...Again\nYour car has already picked up.");
//                                    Toast.makeText(MainActivity.this, "Your car has already picked up.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                //ticket number is wrong
                                showMessageDialogBox("Failed to Pick Up...\nThe ticket number you entered is wrong.");
//                                Toast.makeText(MainActivity.this, "Ticket number is wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please enter both car plate and ticket number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //add cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //create and show dialog
        builder.create().show();

    }

    // display message dialog
    private void showMessageDialogBox(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(str);

        //set layout of dialog
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //create and show dialog
        builder.create().show();
    }

    // enqueue the car into queue
    private String enqueueCarPlate(String carPlateValue) {
        int oriQueueSize = queue.size();
        progressDialog.show();
        //enqueue
        queue.enqueue(carPlateValue);
        totalQueueTV.setText(Integer.toString(queue.size()-1));
        // check if the queue is not empty and the timer is finished, then start timer
        if (queue.size() != 0 && isFinished) {
            String[] tempQueue = queue.getQueue();
            carPlateTV.setText(tempQueue[0]);
            gifImageView.setImageResource(R.drawable.carwash1);
            mCountDownTimer.start();
        }
        // enqueue success, generate ticket number and display out
        if (queue.size() > oriQueueSize){
            //success, dismiss progress
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Added Successful", Toast.LENGTH_SHORT).show();
            //create unique ticket number
            String ticketNumber = generateUniqueString(7);
            //create intent object
            Intent intent = new Intent(MainActivity.this, DropOffSuccessfulActivity.class);
            //put value (ticket number) into intent
            intent.putExtra("ticket_number", ticketNumber);
            //start DropOffSuccessfulActivity
            startActivity(intent);
            return ticketNumber;
        }
        else {
            //failed, dismiss progress
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Failed to Add", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    // generate ticket number
    private String generateUniqueString(int i) {
        final String str = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        while (i > 0) {
            Random rand = new Random();
            result.append(str.charAt(rand.nextInt(str.length())));
            i--;
        }
        return result.toString();
    }
}