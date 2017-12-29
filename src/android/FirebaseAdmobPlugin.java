package com.jernung.plugins.firebase.admob;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

public class FirebaseAdmobPlugin extends CordovaPlugin {

    private static final String PLUGIN_NAME = "FirebaseAdmobPlugin";

    private Context applicationContext;
    private String applicationId;
    private String interstitialId;
    private InterstitialAd mInterstitialAd;
    private JSONArray mTestDeviceIds;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        applicationContext = cordova.getActivity().getApplicationContext();

        mInterstitialAd = new InterstitialAd(applicationContext);
        mTestDeviceIds = new JSONArray();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("addTestDevice".equals(action)) {
            addTestDevice(args.getString(0));

            callbackContext.success(getTestDevices());

            return true;
        }

        if ("getTestDevices".equals(action)) {
            callbackContext.success(getTestDevices());

            return true;
        }

        if ("requestInterstitial".equals(action)) {
            requestNewInterstitial();

            return true;
        }

        if ("setApplicationId".equals(action)) {
            setApplicationId(args.getString(0));

            return true;
        }

        if ("setInterstitialId".equals(action)) {
            setInterstitialId(args.getString(0));

            return true;
        }

        if ("showInterstitial".equals(action)) {
            showInterstitial();

            return true;
        }

        return false;
    }

    private void addTestDevice(final String deviceId) {
        mTestDeviceIds.put(deviceId);
    }

    private Boolean canRequestNewAd() {
        return !mInterstitialAd.isLoaded() && !mInterstitialAd.isLoading();
    }

    private JSONArray getTestDevices() {
        return mTestDeviceIds;
    }

    private void requestNewInterstitial() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                AdRequest.Builder adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

                try {
                    for (int i = 0; i < mTestDeviceIds.length(); i++) {
                        adRequest.addTestDevice(mTestDeviceIds.getString(i));
                    }
                } catch (JSONException error) {
                    Log.e(PLUGIN_NAME, error.getMessage());
                }

                if (canRequestNewAd()) {
                    mInterstitialAd.loadAd(adRequest.build());
                }
            }
        });
    }

    private void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;

        MobileAds.initialize(applicationContext, applicationId);
    }

    private void setInterstitialId(final String interstitialId) {
        this.interstitialId = interstitialId;

        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                mInterstitialAd.setAdUnitId(interstitialId);
                mInterstitialAd.setAdListener(
                        new AdListener() {
                            @Override
                            public void onAdClosed() {
                                requestNewInterstitial();
                            }
                        }
                );

                requestNewInterstitial();
            }
        });
    }

    private void showInterstitial() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    requestNewInterstitial();
                }
            }
        });
    }

}
