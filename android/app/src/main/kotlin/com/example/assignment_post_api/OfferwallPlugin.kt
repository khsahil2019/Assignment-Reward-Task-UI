package com.example.assignment_post_api

import android.content.Context
import androidx.annotation.NonNull
import com.pubscale.offerwall.sdk.OfferWall
import com.pubscale.offerwall.sdk.OfferWallListener
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class OfferwallPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
    private lateinit var channel: MethodChannel
    private lateinit var context: Context

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "offerwall_plugin")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        if (call.method == "showOfferwall") {
            OfferWall.getInstance().showOfferWall(context, object : OfferWallListener {
                override fun onOfferWallClosed() {
                    result.success("Offerwall Closed")
                }

                override fun onOfferWallOpened() {
                    result.success("Offerwall Opened")
                }

                override fun onOfferWallShowFailed(errorCode: Int, errorMessage: String) {
                    result.error(errorCode.toString(), errorMessage, null)
                }

                override fun onOfferWallCredited(amount: Float, totalCredits: Float) {
                    // Handle credit logic if needed
                }
            })
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    // Implement other methods from ActivityAware if needed
}
