package com.thebigoceaan.smartagriculture.dashboard.info;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.thebigoceaan.smartagriculture.LoginActivity;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.Utilities;
import com.thebigoceaan.smartagriculture.databinding.ActivityAddInfoBinding;
import com.thebigoceaan.smartagriculture.models.Info;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;

public class AddInfoActivity extends AppCompatActivity {
    ActivityAddInfoBinding binding;
    FirebaseAuth auth;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    public static final int PICK_IMAGE_REQUEST =1;
    private Uri mImageUri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //action bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        Utilities.appBarColor(actionBar, this); //action bar ends

        //progress dialog codes
        progressDialog = new ProgressDialog(AddInfoActivity.this);
        progressDialog.setTitle("Please Wait");

        //get instance
        auth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Info.class.getSimpleName());
        mStorageReference = FirebaseStorage.getInstance().getReference(Info.class.getSimpleName());

        binding.btnChooseImgInfo.setOnClickListener(view -> {
            browseImage();
        });
        Info info_edit= (Info) getIntent().getSerializableExtra("EDIT");

        if(info_edit!=null){
            binding.btnAddInfo.setText(R.string.update_info);
            binding.infoTitleEditText.setText(info_edit.getInfoTitle());
            binding.infoDetailsEditText.setText(info_edit.getInfoDetails());
            Glide.with(this).load(info_edit.getInfoImage()).into(binding.imgInfoView);
        }
        else{
            binding.btnAddInfo.setText(R.string.add_info);
        }

        //for news add button pressed
        binding.btnAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddInfoActivity.this.uploadFile();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            mImageUri = data.getData();
            Glide.with(this).load(mImageUri).into(binding.imgInfoView);

        }
    }

    public String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadFile(){
        Info info_edit= (Info) getIntent().getSerializableExtra("EDIT");
        CrudInfo crud = new CrudInfo();

        if(mImageUri !=null){
            progressDialog.show();
            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Info info = new Info(uri.toString(),binding.infoTitleEditText.getText().toString(),
                                            binding.infoDetailsEditText.getText().toString());
                                    if(info_edit==null) {
                                        crud.add(info).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void suc) {
                                                binding.infoTitleEditText.setText("");
                                                binding.infoDetailsEditText.setText("");
                                                Intent intent = new Intent(AddInfoActivity.this.getApplicationContext(), ViewInfoActivity.class);
                                                AddInfoActivity.this.startActivity(intent);
                                                Toast.makeText(AddInfoActivity.this, "Agro Info inserted successfully",
                                                        Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        }).addOnFailureListener(e -> Toast.makeText
                                                (AddInfoActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
                                    }

                                    else {
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("infoTitle", binding.infoTitleEditText.getText().toString());
                                        hashMap.put("infoDetails", binding.infoDetailsEditText.getText().toString());
                                        hashMap.put("infoImage",uri.toString());
                                        crud.update(info_edit.getKey(), hashMap).addOnSuccessListener(suc -> {
                                            Intent intent = new Intent (getApplicationContext(), ViewInfoActivity.class);
                                            startActivity(intent);
                                            progressDialog.dismiss();
                                            Toast.makeText(AddInfoActivity.this, "Info updated successfully",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        }).addOnFailureListener(e -> Toast.makeText
                                                (AddInfoActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                            long percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                            progressDialog.setMessage("Saving information to your account :"+ percent + "% Completed");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(AddInfoActivity.this, "Failure due to :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else{
            Toast.makeText(this, "No File Selected !", Toast.LENGTH_SHORT).show();
        }

    }

    private void browseImage(){
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent,PICK_IMAGE_REQUEST);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

}