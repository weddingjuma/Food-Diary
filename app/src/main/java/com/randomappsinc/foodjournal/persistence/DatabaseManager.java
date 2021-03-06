package com.randomappsinc.foodjournal.persistence;

import com.randomappsinc.foodjournal.persistence.dbmanagers.CheckInsDBManager;
import com.randomappsinc.foodjournal.persistence.dbmanagers.DishesDBManager;
import com.randomappsinc.foodjournal.persistence.dbmanagers.RestaurantsDBManager;
import com.randomappsinc.foodjournal.utils.MyApplication;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class DatabaseManager {

    private static final int CURRENT_REALM_VERSION = 3;

    private static DatabaseManager instance;

    public static DatabaseManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized DatabaseManager getSync() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private CheckInsDBManager mCheckInsDBManager;
    private RestaurantsDBManager mRestaurantsDBManager;
    private DishesDBManager mDishesDBManager;

    private DatabaseManager() {
        Realm.init(MyApplication.getAppContext());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .schemaVersion(CURRENT_REALM_VERSION)
                .migration(migration)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        mCheckInsDBManager = CheckInsDBManager.get();
        mRestaurantsDBManager = RestaurantsDBManager.get();
        mDishesDBManager = DishesDBManager.get();
    }

    private RealmMigration migration = new RealmMigration() {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema schema = realm.getSchema();

            // Support for dish tagging
            if (oldVersion == 0) {
                RealmObjectSchema checkInSchema = schema.get("CheckInDO");
                RealmObjectSchema dishSchema = schema.get("DishDO");
                if (checkInSchema != null && dishSchema != null) {
                    checkInSchema.addRealmListField("taggedDishes", dishSchema);
                    dishSchema.addField("checkInId", int.class);
                }
                oldVersion++;
            }

            // Support for dish favoriting
            if (oldVersion == 1) {
                RealmObjectSchema dishSchema = schema.get("DishDO");
                if (dishSchema != null) {
                    dishSchema.addField("isFavorited", boolean.class);
                }
                oldVersion++;
            }

            // Drop saved locations feature since it's clunky
            if (oldVersion == 2) {
                schema.remove("SavedLocationDO");
            }
        }
    };

    public CheckInsDBManager getCheckInsDBManager() {
        return mCheckInsDBManager;
    }

    public RestaurantsDBManager getRestaurantsDBManager() {
        return mRestaurantsDBManager;
    }

    public DishesDBManager getDishesDBManager() {
        return mDishesDBManager;
    }
}
