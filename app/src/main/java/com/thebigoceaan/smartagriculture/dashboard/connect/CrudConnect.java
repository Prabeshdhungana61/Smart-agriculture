package com.thebigoceaan.smartagriculture.dashboard.connect;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.thebigoceaan.smartagriculture.models.Connect;
import com.thebigoceaan.smartagriculture.models.Info;

import java.util.HashMap;

public class CrudConnect {
    private DatabaseReference databaseReference;
    public CrudConnect() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Connect.class.getSimpleName());
    }
    public Task<Void> add(Connect connect){
        return databaseReference.push().setValue(connect);
    }
    public Task<Void> update(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }
    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }
    public Query get(String key){
        if(key==null){
            return databaseReference.orderByKey().limitToFirst(8);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
    }
}
