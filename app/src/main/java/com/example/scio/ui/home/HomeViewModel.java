package com.example.scio.ui.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.scio.Challenge;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    ListView listView;

    public HomeViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}
