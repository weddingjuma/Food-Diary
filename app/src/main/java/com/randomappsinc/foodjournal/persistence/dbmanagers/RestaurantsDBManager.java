package com.randomappsinc.foodjournal.persistence.dbmanagers;

import com.randomappsinc.foodjournal.models.Dish;
import com.randomappsinc.foodjournal.models.Restaurant;
import com.randomappsinc.foodjournal.persistence.DBConverter;
import com.randomappsinc.foodjournal.persistence.models.RestaurantDO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class RestaurantsDBManager {

    private static RestaurantsDBManager instance;

    public static RestaurantsDBManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized RestaurantsDBManager getSync() {
        if (instance == null) {
            instance = new RestaurantsDBManager();
        }
        return instance;
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    public List<Restaurant> getUserRestaurants(String searchTerm) {
        List<Restaurant> locations = new ArrayList<>();
        RealmQuery<RestaurantDO> restaurantQuery = getRealm()
                .where(RestaurantDO.class);
        if (!searchTerm.isEmpty()) {
            restaurantQuery = restaurantQuery.contains("name", searchTerm.toLowerCase(), Case.INSENSITIVE);
        }
        RealmResults<RestaurantDO> restaurantDOs = restaurantQuery.findAllSorted("timeAdded", Sort.DESCENDING);
        for (RestaurantDO restaurantDO : restaurantDOs) {
            locations.add(DBConverter.getRestaurantFromDO(restaurantDO));
        }
        return locations;
    }

    public int getNumUserRestaurants() {
        return getRealm().where(RestaurantDO.class).findAll().size();
    }

    public boolean userAlreadyHasRestaurant(Restaurant restaurant) {
        return getRealm().where(RestaurantDO.class).equalTo("id", restaurant.getId()).findFirst() != null;
    }

    public void addRestaurant(final Restaurant restaurant) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RestaurantDO restaurantDO = restaurant.toRestaurantDO();
                restaurantDO.setTimeAdded(System.currentTimeMillis());
                realm.insert(restaurantDO);
            }
        });
    }

    public void deleteRestaurant(final Restaurant restaurant) {
        final RestaurantDO restaurantDO = getRealm()
                .where(RestaurantDO.class)
                .equalTo("id", restaurant.getId())
                .findFirst();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                restaurantDO.getDishes().deleteAllFromRealm();
                restaurantDO.getCheckIns().deleteAllFromRealm();
                restaurantDO.deleteFromRealm();
            }
        });
    }

    public Restaurant getRestaurant(String restaurantId) {
        RestaurantDO restaurantDO = getRealm()
                .where(RestaurantDO.class)
                .equalTo("id", restaurantId)
                .findFirst();
        return DBConverter.getRestaurantFromDO(restaurantDO);
    }

    public void tagDishToRestaurant(final Dish dish) {
        final RestaurantDO restaurantDO = getRealm()
                .where(RestaurantDO.class)
                .equalTo("id", dish.getRestaurantId())
                .findFirst();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                restaurantDO.getDishes().add(dish.toDishDO());
            }
        });
    }
}
