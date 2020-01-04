package com.example.scio.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.scio.Challenge;
import com.example.scio.R;
import com.example.scio.challengeViewIntent;
import com.example.scio.uploadChallenge;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ListView challengeList;
    DatabaseReference databaseusers;
    FloatingActionButton uploadChallenge;
    public static Challenge challengeId;
    public static ArrayList<Challenge> challenges = new ArrayList<Challenge>();
    SwipeRefreshLayout swipeRefreshLayout;
    CustomAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        databaseusers = FirebaseDatabase.getInstance().getReference("Challenges");
        setChallengeList();

        challengeList = (ListView) view.findViewById(R.id.ListOfChallenges);
        adapter = new CustomAdapter(this.getContext(), R.layout.list_view_challenge_layout, challenges);
        challengeList.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setChallengeList();
                adapter = new CustomAdapter(getContext(), R.layout.list_view_challenge_layout, challenges);
                challengeList.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        uploadChallenge = view.findViewById(R.id.uploadChallenge);
        uploadChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), uploadChallenge.class);
                startActivityForResult(i, Activity.RESULT_OK);

            }
        });
        challengeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                challengeId = challenges.get(position);
                Intent i = new Intent(getActivity(), challengeViewIntent.class);
                startActivity(i);

            }
        });
        return view;

    }
    public void setChallengeList(){
        databaseusers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                challenges.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    keys.add(ds.getKey());
                    Challenge tempChallenge = ds.getValue(Challenge.class);
                    challenges.add(tempChallenge);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "reached");
        if(resultCode == Activity.RESULT_OK){
            Log.d("TAG", data.getStringExtra("name"));
        }
    }
}

class CustomAdapter extends ArrayAdapter<Challenge> {
    Context context;
    ArrayList<Challenge> list;
    int resource;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Challenge> objects){
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        list = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View adapterView = layoutInflater.inflate(resource,null);

        TextView ChallengeName = adapterView.findViewById(R.id.challengeTitle);
        TextView author = adapterView.findViewById(R.id.organizationChallenge);
        TextView completitonView = adapterView.findViewById(R.id.completionview);
        TextView donationView = adapterView.findViewById(R.id.donaitionView);
        ChallengeName.setText(list.get(position).getName());
        author.setText(list.get(position).getAuthor());
        completitonView.setText("Completions: " + list.get(position).getCompletitions());
        donationView.setText("Donations: " + list.get(position).getDonations());


        return adapterView;
    }
}