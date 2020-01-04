package com.example.scio.ui.notifications;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.scio.Challenge;
import com.example.scio.MainActivity;
import com.example.scio.R;
import com.example.scio.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    TextView name, id, completions, donation;
    ListView challenges;
    RadioGroup tabs;
    final String uid = MainActivity.CurrentID;
    Users userData;
    boolean loading = true;
    DatabaseReference users, challengelist;
    ArrayList<String> comp = new ArrayList<>();
    ArrayList<String> pend = new ArrayList<>();

    ArrayList<Challenge> compleated;
    ArrayList<Challenge> pending;

    CustomAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        users = FirebaseDatabase.getInstance().getReference("Users");
        challengelist = FirebaseDatabase.getInstance().getReference("Challenges");

        tabs = (RadioGroup)view.findViewById(R.id.radioGroup);
        name = (TextView) view.findViewById(R.id.userName);
        id = (TextView) view.findViewById(R.id.userId);
        completions= (TextView) view.findViewById(R.id.compleationNumber);
        donation= (TextView) view.findViewById(R.id.DonationAmount);
        challenges = (ListView) view.findViewById(R.id.profileList);
        id.setText(MainActivity.CurrentID);
        Log.d("Profile",id.getText().toString());
        Log.d("Profile",uid);
        findProfile(uid);

        tabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(!loading){
                    if(tabs.getCheckedRadioButtonId()==R.id.CompleatedTab){
                        adapter = new CustomAdapter(getContext(), R.layout.list_view_challenge_layout, compleated);
                        challenges.setAdapter(adapter);
                    }
                    if(tabs.getCheckedRadioButtonId()==R.id.pending){
                        adapter = new CustomAdapter(getContext(), R.layout.list_view_challenge_layout, pending);
                        challenges.setAdapter(adapter);
                    }

                }
            }
        });

        return view;
    }

    void findProfile(String user){
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData= dataSnapshot.child(uid).getValue(Users.class);
                name.setText(userData.getDisplay());
                completions.setText(userData.getChalenges());
                donation.setText(userData.getAmount());
                if(userData.getComplted()!=null)
                    comp = userData.getComplted();
                Log.d("arraysize", Integer.toString(comp.size()));
                if(userData.getPending()!=null)
                    pend = userData.getPending();
                //loadChallenges();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    void loadChallenges(){
        challengelist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (comp.size() > 0){
                    for (String id : comp) {
                        compleated.add(dataSnapshot.child(id).getValue(Challenge.class));
                    }
                }

                if(pend.size()>0) {
                    for (String id : pend) {
                        pending.add(dataSnapshot.child(id).getValue(Challenge.class));
                    }
                }
                loading = false;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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


        ChallengeName.setText(list.get(position).getName());
        author.setText(list.get(position).getAuthor());


        return adapterView;
    }
}