package com.zybooks.cs360_warehouse_inventory_app;

import android.content.Context;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InventoryRepository {

    private final InventoryDao inventoryDao;
    private final UserDao   userDao;
    private final LiveData<List<InventoryItem>> allItems;

    //Single background thread for all db operations
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public InventoryRepository(Context context) {
        AppDatabase db  = AppDatabase.getInstance(context);
        inventoryDao    = db.inventoryDao();
        userDao         = db.userDao();
        allItems        = inventoryDao.getAllItems();
    }

    // INVENTORY OPERATIONS
    public LiveData<List<InventoryItem>> getAllItems() {
        return allItems;
    }

    public void insert(InventoryItem item) {
        executor.execute(() -> inventoryDao.insert(item));
    }

    public void update(InventoryItem item) {
        executor.execute(() -> inventoryDao.update(item));
    }

    public void delete(InventoryItem item) {
        executor.execute(() -> inventoryDao.delete(item));
    }



    // USER OPERATIONS
    // INSERT USER to DB
    public void insertUser(User user) {
        executor.execute(() -> userDao.insert(user));
    }

    // Finds a user by username -> Returns User Object via callback
    public void findByUsername(String username, UserCallback callback) {
        executor.execute(() -> {
            User user = userDao.findByUsername(username);
            callback.onResult(user);
        });
    }

    //CHECK if username already exists - return boolean via callback
    public void usernameExists(String username, ExistsCallback callback) {
        executor.execute(() -> {
            int count = userDao.usernameExists(username);
            callback.onResult(count > 0);
        });
    }

    // CALLBACKS
    // RETURN results from background threads back to the UI
    public interface UserCallback {
        void onResult(User user);
    }

    public interface ExistsCallback {
        void onResult(boolean exists);
    }

}
