package com.example.carwashsimulatingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carwashsimulatingapp.adapters.AdapterHistoryList;
import com.example.carwashsimulatingapp.adapters.AdapterQueueList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // recycler view
    RecyclerView historyListRecyclerView;
    AdapterHistoryList adapterHistoryList;

    public HistoryListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryListFragment newInstance(String param1, String param2) {
        HistoryListFragment fragment = new HistoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);
        // recycler view
        historyListRecyclerView = view.findViewById(R.id.historyValueRecycleView);
        // get the history array list and details list
        String[] historyList = getArguments().getStringArray("history_list");
        ArrayList<CarPlate> detailsList = getArguments().getParcelableArrayList("details_list");
        // adapter
        adapterHistoryList = new AdapterHistoryList(getActivity(), historyList, detailsList);
        historyListRecyclerView.setAdapter(adapterHistoryList);
        historyListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}