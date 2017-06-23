package com.goulartgrossi.lucas.appaem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goulartgrossi.lucas.appaem.R;


public class DefineEquivalentCircuitFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_define_equivalent_circuit, container, false);

        view.findViewById(R.id.insertCData).setVisibility(View.GONE);
        view.findViewById(R.id.insertTests).setVisibility(View.GONE);

        return view;
    }
}
