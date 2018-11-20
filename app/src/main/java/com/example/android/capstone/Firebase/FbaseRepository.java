package com.example.android.capstone.Firebase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FbaseRepository {

    public void writeUnit(String unit){
        FbaseConnection.getInstance().child("units").push().setValue(unit);
    }

    public LiveData<List<String>> getUnits(){
        final MutableLiveData<List<String>> unitList = new MutableLiveData<>();
        FbaseConnection.getInstance().child("units").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> partialUnitList = new ArrayList<>();

                for (DataSnapshot unitChildren : dataSnapshot.getChildren()){
                    if (unitChildren != null)
                        partialUnitList.add(unitChildren.getValue().toString());
                }

                unitList.setValue(partialUnitList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return unitList;
    }

}
