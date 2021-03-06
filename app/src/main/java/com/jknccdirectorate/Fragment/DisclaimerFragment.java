package com.jknccdirectorate.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jknccdirectorate.Activity.MainActivity;
import com.jknccdirectorate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisclaimerFragment extends Fragment {

    private TextView GamingDronzz, textView;

    public DisclaimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_disclaimer, container, false);
        GamingDronzz = (TextView) view.findViewById(R.id.disclaimerDesignedBy);
        textView = (TextView) view.findViewById(R.id.disclaimer);
        GamingDronzz.setVisibility(View.INVISIBLE);
        GamingDronzz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCompanyWebsite();
            }
        });
        StartAnimations();
        return view;
    }

    private void StartAnimations() {
        Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.fade);
        final Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.translate);

        animation1.reset();
        animation2.reset();
        textView.clearAnimation();
        GamingDronzz.clearAnimation();

        textView.startAnimation(animation1);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                GamingDronzz.setVisibility(View.VISIBLE);
                GamingDronzz.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void openCompanyWebsite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("http://www.gamingdronzz.com"));
        startActivity(browserIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.disclaimer));
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.app_name));
    }
}
