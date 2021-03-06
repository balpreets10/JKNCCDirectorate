package com.jknccdirectorate.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jknccdirectorate.Activity.MainActivity;
import com.jknccdirectorate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnrollmentFragment extends Fragment {


    public EnrollmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enrollment, container, false);
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.enrollment));
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.app_name));
    }

}
