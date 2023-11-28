package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RVAdapterDesigner;
import com.example.myapplication.User;
import com.example.myapplication.adapter.RVAdapterManagement;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class FragmentDesigner extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;

    private List<User> userList;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    private ShimmerFrameLayout mFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private RVAdapterDesigner userAdapter;

    public static FragmentDesigner newInstance(int index) {
        FragmentDesigner fragment = new FragmentDesigner();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.RV_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        userList = new ArrayList<>();

        mFrameLayout = view.findViewById(R.id.shimmerLayout);
        swipeRefreshLayout = view.findViewById(R.id.refreshLayout);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("items");
        Query query = rootRef.orderByChild("department").equalTo("design");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    userList.add(user);
                }

                recyclerView.setAdapter(adapter);
                mFrameLayout.startShimmer();
                mFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                userAdapter=new RVAdapterDesigner(getContext(), userList, getActivity());
                recyclerView.setAdapter(userAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        dataUpdate();
                    }
                }
        );
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        searchView=(SearchView)menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query!=null){
                    filter(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null){
                    filter(newText);
                }
                return true;
            }
        });
    }

    private void filter(String s){
        List<User> filteredlist=new ArrayList<>();
        for(User user: userList){
            if(user.getFirstName().toLowerCase().contains(s.toLowerCase())||
                    user.getLastName().toLowerCase().contains(s.toLowerCase()))
            {
                filteredlist.add(user);
            }
        }
        userAdapter=new RVAdapterDesigner(getContext(),filteredlist,getActivity());
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void onRefresh() {

        dataUpdate();
    }

    private void dataUpdate()
    {
        swipeRefreshLayout.setRefreshing(true);
        Collections.shuffle(userList, new Random(System.currentTimeMillis()));
        adapter = new RVAdapterManagement(context, userList, getActivity());
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onPause() {
        mFrameLayout.stopShimmer();
        super.onPause();
    }
}