package com.thebigoceaan.smartagriculture.descriptions.help;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.FragmentOrderHelpBinding;

public class OrderHelpFragment extends Fragment {
    FragmentOrderHelpBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentOrderHelpBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.moreOption.setOnClickListener(view1 -> {
            FarmerRegisterHelpFragment fragment = new FarmerRegisterHelpFragment();
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction().replace(R.id.frame_container,fragment ).addToBackStack(null).commit();
        });
    }
}