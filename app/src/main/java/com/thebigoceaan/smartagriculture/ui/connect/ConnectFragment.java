package com.thebigoceaan.smartagriculture.ui.connect;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thebigoceaan.smartagriculture.dashboard.connect.CrudConnect;
import com.thebigoceaan.smartagriculture.databinding.FragmentConnectBinding;
import com.thebigoceaan.smartagriculture.models.Connect;

import java.util.Objects;

import es.dmoral.toasty.Toasty;


public class ConnectFragment extends Fragment {
    private FragmentConnectBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private CrudConnect crud;
    private Connect connect;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConnectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //for display drawer layout
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        Objects.requireNonNull(mActionBar).setDisplayHomeAsUpEnabled(true);

        //progress dialog codes
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Sending your messages ... ");

        //get instance
        auth = FirebaseAuth.getInstance();
        crud= new CrudConnect();

        if(validateMessages()){
            sendMessageButton();
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean validateMessages(){
        if(binding.writeMsgDetailsEditText.getText().toString().trim().isEmpty()){
            binding.writeMsgDetailsEditText.setError("Please write something to proceed !");
            return false;
        }
        else{
            return true;
        }
    }

    private void sendMessageButton(){
        binding.msgSentBtnConnect.setOnClickListener(view -> {
            user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                progressDialog.show();
                connect = new Connect(binding.writeMsgDetailsEditText.getText().toString(), user.getDisplayName());
                crud.add(connect).addOnSuccessListener(unused -> {
                    progressDialog.dismiss();
                    Toasty.success(getContext(), "Successfully sent the message to owner !", Toasty.LENGTH_SHORT, true).show();
                    binding.writeMsgDetailsEditText.setText("");
                });
            } else {
                Toasty.warning(getContext(), "Kindly login first to use this feature", Toasty.LENGTH_SHORT, true).show();
            }
        });
    }

}