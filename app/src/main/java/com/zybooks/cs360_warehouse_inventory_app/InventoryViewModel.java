package com.zybooks.cs360_warehouse_inventory_app;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class InventoryViewModel extends AndroidViewModel {

    private final InventoryRepository repository;
    private final LiveData<List<InventoryItem>> allItems;

    public InventoryViewModel(@NonNull Application application) {
        super(application);
        repository  = new InventoryRepository(application);
        allItems    = repository.getAllItems();
    }

    //  INVENTORY
    public LiveData<List<InventoryItem>> getAllItems() {

        return allItems;
    }

    public void insert(InventoryItem item) {

        repository.insert(item);
    }

    public void update(InventoryItem item) {

        repository.update(item);
    }
    public void delete(InventoryItem item) {
        repository.delete(item);
    }

    // USER
    public void login(String username, String password,
                      InventoryRepository.UserCallback callback) {
        repository.findByUsername(username, user -> {
            if  (user != null &&
                    user.getPasswordHash().equals(PasswordUtils.hash(password))) {
                callback.onResult(user);
            } else {
                callback.onResult(null);
            }
        });
    }

    public void createAccount(String username, String password,
                              InventoryRepository.ExistsCallback callback) {
        repository.usernameExists(username, exists -> {
            if (exists) {
                // Username already taken
                callback.onResult(false);
            } else {
                // Create new username with hashed password
                User newUser = new User(username, PasswordUtils.hash(password));
                repository.insertUser(newUser);
                callback.onResult(true);
            }
        });
    }
}
