package com.jknccdirectorate.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.jknccdirectorate.Activity.MainActivity;
import com.jknccdirectorate.Adapter.RecyclerViewAdapterImages;
import com.jknccdirectorate.Model.ImageModel;
import com.jknccdirectorate.R;
import com.jknccdirectorate.Tools.Helper;
import com.jknccdirectorate.Tools.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment implements VolleyHelper.VolleyResponse {

    final String TAG = "Gallery";

    Helper helper;
    int total = 0,current = 0;
    private RecyclerViewAdapterImages adapter;
    private List<ImageModel> modelList;
    private RecyclerView recyclerView;

    VolleyHelper volleyHelper;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        volleyHelper = new VolleyHelper(this, view.getContext());
        helper = new Helper(view.getContext());
        modelList = new ArrayList<>();
        findViews(view);
        adapter = new RecyclerViewAdapterImages(modelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        return view;
    }



    private void findViews(View view)
    {
        recyclerView = (RecyclerView) view.findViewById(R.id.gallery_recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.gallery));
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.app_name));
    }


    //Call this function after on create to get ids from api
    //it is not implemented yet so i have not called it yet


    private void getImageIDs() {
        String url = this.helper.getBaseURL() + "getGalleryImageIds.php";
        //Map<String, String> params = new HashMap();
        //params.put("id", id + "");
        if (this.volleyHelper.countRequestsInFlight("getGalleryImageIds") == 0) {
            this.volleyHelper.makeStringRequest(url, "getGalleryImageIds");
        }
    }


    //this function will be called from the response as we get the ids of the images
    private void getImage(int id) {
        String url = this.helper.getBaseURL() + "getImageFromGallery.php";
        Map<String, String> params = new HashMap();
        params.put("id", id + "");
        if (this.volleyHelper.countRequestsInFlight("getImageFromGallery" + id) == 0) {
            this.volleyHelper.makeStringRequest(url, "getImageFromGallery" + id, params);
        }
    }

    @Override
    public void onError(VolleyError volleyError) {

    }

    @Override
    public void onResponse(String result) {
        Log.d(this.TAG, result);
        try {
            JSONObject json = this.helper.getJson(result);
            if (json.getString(Helper.ACTION).equals("fetching image id")) {
                if (json.getString("image id").equals(this.helper.SUCCESS)) {
                    total = json.getInt("row_count");
                    for (int i = 0; i < total; i++) {
                        getImage(json.getInt("image_id" + i));
                    }
                }
            } else if (json.getString(Helper.ACTION).equals("fetching image")) {
                if (json.getString("image_result").equals(this.helper.SUCCESS)) {
                    ImageModel model = new ImageModel(helper.getBitmapFromString(json.getString("image")));
                    this.modelList.add(current,model);
                    this.adapter.notifyItemInserted(current);
                    current++;
                    if (current == total) {
                        //Hide progress bar here
                    }
                }
            }
        } catch (JSONException e) {

        }
    }
}
