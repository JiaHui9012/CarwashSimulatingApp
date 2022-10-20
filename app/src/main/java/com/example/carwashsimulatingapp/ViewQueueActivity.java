package com.example.carwashsimulatingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewQueueActivity extends AppCompatActivity {

    TabLayout queueHistoryTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_queue);

        queueHistoryTab = findViewById(R.id.queueHistoryTab);

        Bundle bundle = getIntent().getExtras();
        String[] queueList = bundle.getStringArray("queue_list");
        String[] historyList = bundle.getStringArray("history_list");

        //create the get Intent object
        Intent intent = getIntent();
        //receive the value using key
        ArrayList<CarPlate> detailsList = (ArrayList<CarPlate>) intent.getSerializableExtra("details_list");

        if (queueList == null) {
            goToBlankPage();
        }
        else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle1 = new Bundle();
            bundle1.putStringArray("queue_list", queueList);
            QueueListFragment fragmentQueueList =  new QueueListFragment();
            fragmentQueueList.setArguments(bundle1);
            fragmentTransaction.replace(R.id.content, fragmentQueueList, "");
            fragmentTransaction.commit();
        }

        queueHistoryTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        // queue tab
                        if (queueList == null) {
                            goToBlankPage();
                        }
                        else {
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putStringArray("queue_list", queueList);
                            QueueListFragment fragmentQueueList =  new QueueListFragment();
                            fragmentQueueList.setArguments(bundle);
                            fragmentTransaction.replace(R.id.content, fragmentQueueList, "");
                            fragmentTransaction.commit();
                        }
                        break;
                    case 1:
                        // history tab
                        if (historyList == null) {
                            goToBlankPage();
                        }
                        else {
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putStringArray("history_list", historyList);
                            bundle.putParcelableArrayList("details_list", detailsList);
                            HistoryListFragment fragmentHistoryList =  new HistoryListFragment();
                            fragmentHistoryList.setArguments(bundle);
                            fragmentTransaction.replace(R.id.content, fragmentHistoryList, "");
                            fragmentTransaction.commit();
                        }
                        break;
                    default:

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    private void goToBlankPage() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BlankListFragment fragmentBlankList =  new BlankListFragment();
        fragmentTransaction.replace(R.id.content, fragmentBlankList, "");
        fragmentTransaction.commit();
    }
}