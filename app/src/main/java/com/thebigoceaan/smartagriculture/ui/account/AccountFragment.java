package com.thebigoceaan.smartagriculture.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

return root;
    }
}