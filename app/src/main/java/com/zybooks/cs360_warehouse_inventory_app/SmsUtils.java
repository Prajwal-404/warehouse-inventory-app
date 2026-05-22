package com.zybooks.cs360_warehouse_inventory_app;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
public class SmsUtils {


    private static final String TAG = "SmsUtils";
    // Phone number to send alerts to
    private static final String ALERT_NUMBER ="5551234567";

    public static void sendLowStockAlert(Context context, String itemName) {
        try {
            SmsManager smsManager   = SmsManager.getDefault();
            String message          = "CS360 Inventory Alert:  " + itemName +
                                    " has reached zero. Time to restock.";
            smsManager.sendTextMessage(
                    ALERT_NUMBER,       // destination
                    null,               // service center (null = default)
                    message,            // message body
                    null,               // sent intent
                    null                // delivery intent
            );
            Log.d(TAG, "SMS sent for: " + itemName);
        } catch (Exception e) {
            // Log the error
            Log.e(TAG, "Failed to send SMS: " + e.getMessage());
        }
    }
}
