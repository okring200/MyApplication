package com.example.minjena.myapplication.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.minjena.myapplication.R;
import com.example.minjena.myapplication.fragment.AccountFragment;
import com.example.minjena.myapplication.fragment.PeopleFragment;
import com.example.minjena.myapplication.fragment.RoomFragment;
import com.example.minjena.myapplication.presenter.PeoplePresenter;

public class MainActivity extends Activity{

    PeopleFragment peopleFragment;
    RoomFragment roomFragment;

    protected void onCreate(Bundle savedInstanceState) {    //commit test12
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle arg = getIntent().getExtras();
        peopleFragment = new PeopleFragment(arg);
        roomFragment = new RoomFragment(arg);
        peopleFragment.setArguments(arg);
        roomFragment.setArguments(arg);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.mainactivity_bottom_navigation_view);
        //mPeoplePresenter.getPeopleList();
        getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, peopleFragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_people:
                        getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, peopleFragment).commit();
                        return true;
                    case R.id.action_room:
                        getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, roomFragment).commit();
                        return true;
                    case R.id.action_account:
                        getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new AccountFragment()).commit();
                        return true;
                }

                return false;
            }
        });

    }
}
