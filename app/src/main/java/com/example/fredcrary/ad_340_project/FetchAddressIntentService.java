package com.example.fredcrary.ad_340_project;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Fred Crary on 6/4/2017. Adapted from instructor's code.
 */

public class FetchAddressIntentService extends IntentService {

    private static final String TAG = "FetchAddressIS";

    protected ResultReceiver mReceiver;     // Receive where results are forwarded

    // Constructor to name the worker thread
    public FetchAddressIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Log.d(TAG, "onHandleIntent()");

        String errorMessage = "";

        mReceiver = intent.getParcelableExtra(Constants.RECEIVER);

        if (mReceiver == null) {
            Log.wtf(TAG, "No receiver registered. Nowhere to send the results!");
            return;
        }

        // Get the location passed in
        Location location = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);

        if (location == null) {
            errorMessage = "No location data supplied";
            Log.wtf(TAG, errorMessage);
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;

        try{
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);         // only getting one address
        } catch (IOException ioException) {
            errorMessage = "Geocoder service not available";
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = "Invalid Lat/Long supplied";
            Log.e(TAG,errorMessage + ". " +
                "Latitude = " + location.getLatitude() +
                ", Longitude = " + location.getLongitude(), illegalArgumentException);
        }

        // Case where no address was found
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No address found";
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
        } else {
            // Use only the first address returned
            Address address = addresses.get(0);
            // Put together the address
            ArrayList<String> addressFragments = new ArrayList<String>();
            for (int i = 0 ; i < address.getMaxAddressLineIndex() ; i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            //Log.i(TAG, "Address found");
            deliverResultToReceiver(Constants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"), addressFragments));
        }
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        mReceiver.send(resultCode, bundle);
    }

}
