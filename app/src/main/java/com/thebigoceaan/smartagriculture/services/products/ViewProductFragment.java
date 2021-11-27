package com.thebigoceaan.smartagriculture.services.products;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thebigoceaan.smartagriculture.databinding.FragmentAddProductBinding;

public class ViewProductFragment extends Fragment {
    FragmentAddProductBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddProductBinding.inflate(inflater,container,false);
        View root = binding.getRoot();



        return root;
    }

}