package com.zybooks.cs360_warehouse_inventory_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InventoryActivity extends AppCompatActivity {

    RecyclerView recyclerViewInventory;
    FloatingActionButton fabAddItem;
    InventoryAdapter adapter;
    InventoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        recyclerViewInventory   = findViewById(R.id.recyclerViewInventory);
        fabAddItem              = findViewById(R.id.fabAddItem);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        // Set RecyclerView
        adapter = new InventoryAdapter(new java.util.ArrayList<>(), viewModel);
        recyclerViewInventory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewInventory.setAdapter(adapter);

        // Track LiveData - list updates automatically whith db changes
        viewModel.getAllItems().observe(this, items -> {
            adapter.updateList(items);
            checkForZeroQuantity(items);
        });

        // Floating Action Button
        fabAddItem.setOnClickListener(v -> showAddItemDialog());

    }

    // ADD ITEM DIALOG - Logic for adding new item/using fabAddItem
    private void showAddItemDialog() {
        // Simple LayoutInflater for Add Item Dialog
        View addItemDialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_add_item, null);

        EditText editNewItemName        = addItemDialogView.findViewById(R.id.editNewItemName);
        EditText editNewItemQuantity    = addItemDialogView.findViewById(R.id.editNewItemQuantity);

        new AlertDialog.Builder(this)
                .setTitle("Add Inventory Item")
                .setView(addItemDialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name         = editNewItemName.getText().toString().trim();
                    String quantityStr  = editNewItemQuantity.getText().toString().trim();

                    if (name.isEmpty() || quantityStr.isEmpty()) {
                        Toast.makeText(this, "Please fill in all fields",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int quantity            = Integer.parseInt(quantityStr);
                    InventoryItem newItem   = new InventoryItem(name, quantity);
                    viewModel.insert(newItem);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // SMS ZERO QUANTITY CHECK
    private void checkForZeroQuantity(java.util.List<InventoryItem> items) {
        // ONLY SEND WITH PERMISSION - Check SMS permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        for (InventoryItem item : items) {
            if (item.getQuantity() == 0) {
                SmsUtils.sendLowStockAlert(this, item.getName());
            }
        }
    }
}