package com.thebigoceaan.smartagriculture.ui.account;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.FragmentAccountBinding;

import java.util.Objects;

public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater,container,false);

        //for display drawer layout
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        Objects.requireNonNull(mActionBar).setDisplayHomeAsUpEnabled(true);


        View root = binding.getRoot();

return root;
    }
}