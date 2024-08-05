package com.adjump.adjump;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import android.app.Activity;
import android.util.Log;
import android.content.Intent;

import java.util.HashMap;
import java.util.Objects;

import io.adjump.adjump;

import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

/** AdjumpPlugin */
public class AdjumpPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    private static final String TAG = "AdjumpPlugin";
    private static final String CHANNEL_NAME = "adjump";

    private static MethodChannel channel;
    private static AdjumpPlugin instance;
    private static Activity activityInstance;

    private adjump adjumpInstance;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        if (instance == null) instance = new AdjumpPlugin();
        if (channel != null) return;
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_NAME);
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        try {
            switch (call.method) {
                case "initSDK":
                    initSdk((HashMap) call.arguments);
                    result.success(true);
                    break;
                case "showOfferwall":
                    showOfferwall(result);
                    break;
                default:
                    result.notImplemented();
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error: ", e);
            result.error(String.valueOf(e.hashCode()), e.getMessage(), e.getLocalizedMessage());
        }
    }

    private void initSdk(final HashMap args) {
        try {
            if (activityInstance != null) {
             
                String appId = Objects.requireNonNull(args.get("appId")).toString();
                String userId = Objects.requireNonNull(args.get("userId")).toString();

                // Initialize the SDK with the provided parameters
                adjumpInstance = new adjump(activityInstance.getApplicationContext(), accountId, appId, userId);
                
                OfferWallConfig offerWallConfig =
                    new OfferWallConfig.Builder(context, "YOUR_PUBSCALE_APP_ID")
                .setUniqueId("unique_id") //optional, used to represent the user of your application
                .setLoaderBackgroundBitmap(backgroundBitmap)//optional
                .setLoaderForegroundBitmap(foregroundBitmap)//optional
                .setFullscreenEnabled(false)//optional
                .build();

                adjumpInstance.initialize(new adjump.InitialisationListener() {
                    @Override
                    public void onInitialisationSuccess() {
                        Log.i(TAG, "SDK Initialized Successfully");
                    }

                    @Override
                    public void onInitialisationError(Exception exception) {
                        Log.e(TAG, "SDK Initialization Error: ", exception);
                    }
                });

            } else {
                throw new Exception("Activity is null during SDK initialization");
            }
        } catch (Exception e) {
            Log.e(TAG, "SDK Initialization Error: ", e);
        }
    }

    private void showOfferwall(final Result result) {
        try {
            if (activityInstance != null && adjumpInstance != null) {
                if (adjumpInstance.isAvailable()) {
                    adjumpInstance.launchOfferWall();
                    Log.i(TAG, "Offerwall shown successfully");
                    result.success(true);
                } else {
                    Log.i(TAG, "Offerwall is not available");
                    result.success(false);
                }
            } else {
                throw new Exception("Activity is null or SDK is not initialized during Offerwall display");
            }
        } catch (Exception e) {
            Log.e(TAG, "Show Offerwall Error: ", e);
            result.error(String.valueOf(e.hashCode()), e.getMessage(), e.getLocalizedMessage());
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        if (channel != null) {
            channel.setMethodCallHandler(null);
            channel = null;
        }
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activityInstance = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        activityInstance = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        activityInstance = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {
        activityInstance = null;
    }
}
