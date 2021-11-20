package com.thebigoceaan.smartagriculture.ui.connect;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.dashboard.connect.CrudConnect;
import com.thebigoceaan.smartagriculture.dashboard.info.AddInfoActivity;
import com.thebigoceaan.smartagriculture.databinding.FragmentConnectBinding;
import com.thebigoceaan.smartagriculture.models.Connect;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;


public class ConnectFragment extends Fragment {
    private FragmentConnectBinding binding;
    private FirebaseAuth auth;
    private Dialog dialog;
    ProgressDialog progressDialog;



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

            binding.msgSentBtnConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(auth.getCurrentUser()!=null) {
                        ConnectFragment.this.sendMessage();
                    }
                     else{
                        Toast.makeText(getContext(), "Kindly login first to use this feature !", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        return root;
    }

    private void sendMessage() {
        //get reference
        dialog = new Dialog(getContext());
        CrudConnect crud = new CrudConnect();
        Connect connect = new Connect(binding.writeMsgDetailsEditText.getText().toString().trim(),
                auth.getCurrentUser().getDisplayName().trim());
        dialog.setContentView(R.layout.dialog_confirmation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button yesBtn = dialog.findViewById(R.id.btn_yes_logout);
        Button noBtn = dialog.findViewById(R.id.btn_no_logout);
        dialog.show();
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(validateMessages()){
                    return;
                }
                progressDialog.show();
                crud.add(connect).addOnSuccessListener(unused -> {
                    binding.writeMsgDetailsEditText.setText("");
                    progressDialog.dismiss();
                    Toast.makeText(ConnectFragment.this.getContext(), "Successfully sent your message to the admin.", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(ConnectFragment.this.getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
        noBtn.setOnClickListener(view -> dialog.dismiss());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean validateMessages(){
        if(binding.writeMsgDetailsEditText.getText().toString().trim().equals("")){
            binding.writeMsgDetailsEditText.setError("Please Write your messages !");
        }
        return true;
    }
}