package com.goulartgrossi.lucas.appaem.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goulartgrossi.lucas.appaem.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IMAddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IMAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IMAddFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_imadd, container, false);
    }
}
