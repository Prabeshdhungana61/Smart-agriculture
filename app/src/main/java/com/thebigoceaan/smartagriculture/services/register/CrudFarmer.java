package com.thebigoceaan.smartagriculture.services.register;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.thebigoceaan.smartagriculture.models.Farmer;
import java.util.HashMap;

public class CrudFarmer {
    private DatabaseReference databaseReference;

    public CrudFarmer(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Farmer.class.getSimpleName());
    }
    public Task<Void> addFarmer(Farmer farmer){
        return databaseReference.setValue(farmer);
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
