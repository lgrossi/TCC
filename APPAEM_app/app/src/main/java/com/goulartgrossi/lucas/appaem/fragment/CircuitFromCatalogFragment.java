package com.goulartgrossi.lucas.appaem.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goulartgrossi.lucas.appaem.R;

public class CircuitFromCatalogFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        View view = inflater.inflate(R.layout.fragment_circuit_from_catalog, container, false);

        return view;
    }
}
