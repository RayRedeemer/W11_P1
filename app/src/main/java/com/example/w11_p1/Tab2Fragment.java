package com.example.w11_p1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Tab2Fragment extends Fragment {

    FirebaseAuth mAuth;
    String username;
    String TAG = "data";

    private DatabaseReference mDatabase;

    private List<User> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        // grab the reference to the recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // init the adapter
        mAdapter = new UserAdapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter); // set the adapter to the recycler view

        // grab the reference to the firebase auth
        mAuth = FirebaseAuth.getInstance();
        username = mAuth.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("flash_card"); // get the ref of db

        // get data sorted by score limit to 5
        mDatabase.orderByChild("score").limitToLast(5).addValueEventListener( new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                Log.i("dataInside",dataSnapshot.getValue().toString());

                userList.clear();
                if(dataSnapshot.hasChildren()){
                    Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();
                    while (iter.hasNext()){
                        DataSnapshot snap = iter.next();
                        String nodId = snap.getKey();
                        String username = (String) snap.child("username").getValue();
                        Long score = (Long) snap.child("score").getValue();
                        User user = new User(username, score);
                        userList.add(user);
                        //received results
                        Log.i(TAG, username + " on nod " + nodId + " has score " + score);
                    }
                }
                // firebase return the sorted data in ascending order, we have to reverse it
                Collections.reverse(userList);
                // notify the adapter
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        });

        return view;
    }
}