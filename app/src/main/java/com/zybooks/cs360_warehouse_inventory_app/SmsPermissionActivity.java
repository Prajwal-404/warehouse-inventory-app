package com.zybooks.cs360_warehouse_inventory_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SmsPermissionActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 101;

    Button buttonGrantSms;
    Button buttonDenySms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_permission);

        buttonGrantSms  = findViewById(R.id.buttonGrantSMS);
        buttonDenySms   = findViewById(R.id.buttonDenySms);

        buttonGrantSms.setOnClickListener(v -> {
            // Check for Prior Permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission already granted",
                        Toast.LENGTH_SHORT).show();
                goToInventory();
            } else {
                // Ask User For SMS Permission
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.SEND_SMS},
                        SMS_PERMISSION_CODE
                );
            }
        });

        buttonDenySms.setOnClickListener(v -> {
            // User Deny App Notifications
            Toast.makeText(this, "SMS notifications disabled",
                    Toast.LENGTH_SHORT).show();
            goToInventory();
        });
    }

    //  TODO ? Possible IDEA < SMS NOTIFICATION TO SPECIFIED USER >
    // TODO: For Managers off Devices to also receive SMS, Not Only Current Device.
    // Method Called Automatically by Android after the user responds to permission dialog
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                          @NonNull String[] permissions,
                                          @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // User Accepted Permission
                Toast.makeText(this, "SMS permission granted",
                        Toast.LENGTH_SHORT).show();
            } else {
                // User Denied Permission
                Toast.makeText(this, "SMS permission denied - Notifications Disabled",
                        Toast.LENGTH_SHORT).show();
            }
            // Continue to Inventory After Permissions
            goToInventory();
        }
    }

    private void goToInventory() {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
        finish();
    }
}