package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RVAdapterFA;
import com.example.myapplication.User;
import com.example.myapplication.adapter.RVAdapterManagement;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FragmentAll extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DatabaseReference databaseReference;
    private ShimmerFrameLayout mFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;

    private List<User> userList;
    private SearchView searchView;
    private RVAdapterFA userAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        this.context = getContext();


        recyclerView = view.findViewById(R.id.RV_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        userList = new ArrayList<>();

        mFrameLayout = view.findViewById(R.id.shimmerLayout);
        swipeRefreshLayout = view.findViewById(R.id.refreshLayout);

        FirebaseDatabase.getInstance().getReference().child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //userList.clear();
                for(DataSnapshot childsnapshot:snapshot.getChildren()){
                    userList.add(childsnapshot.getValue(User.class));
                }

                recyclerView.setAdapter(adapter);
                mFrameLayout.startShimmer();
                mFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                userAdapter=new RVAdapterFA(getContext(),userList,getActivity());
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //progressDialog.dismiss();
               // ReusableFunctionsAndObjects.showMessageAlert(getContext(),"Network Error",error.getMessage(),"Ok",(byte)0);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        dataUpdate();
                    }
                }
        );
        return view;
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
        userAdapter=new RVAdapterFA(getContext(),filteredlist,getActivity());
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
        adapter = new RVAdapterFA(context, userList, getActivity());
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onPause() {
        mFrameLayout.stopShimmer();
        super.onPause();
    }
}