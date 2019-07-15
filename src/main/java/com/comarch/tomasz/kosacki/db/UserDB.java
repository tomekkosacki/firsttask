package com.comarch.tomasz.kosacki.db;

import com.comarch.tomasz.kosacki.dao.UserDao;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDB implements UserDao {

    private Datastore datastore;

    public UserDB(Datastore datastore) {
        this.datastore = datastore;
        initializeDB(); // do usuniecia
    }

    //do usuniecia
    private void initializeDB() {
        datastore.save(new UserEntity("1", "FN1", "LN1", "email1@email.com", new Date()));
        datastore.save(new UserEntity("2", "FN1", "LN1", "email2@email.com", new Date()));
    }

    @Override
    public UserEntity getUserById(String userId) {

        return datastore.createQuery(UserEntity.class)
                .field("id").equal(userId)
                .get();
    }

    @Override
    public List<UserEntity> getUserBy(String userId, String userFirstName, String userLastName, String userEmail, int offset, int limit, String sortBy) {

        List<Criteria> criteriaList = new ArrayList<>();
        Query<UserEntity> query = datastore.createQuery(UserEntity.class);

        if (userId != null) {
            criteriaList.add(query.criteria("id").equal(userId));
        }
        if (userFirstName != null) {
            criteriaList.add(query.criteria("firstName").equal(userFirstName));
        }
        if (userLastName != null) {
            criteriaList.add(query.criteria("lastName").equal(userLastName));
        }
        if (userEmail != null) {
            criteriaList.add(query.criteria("email").equal(userEmail));
        }
        query.and(criteriaList.toArray(new Criteria[criteriaList.size()]));
        if (sortBy != null) {
            return query.order(sortBy)
                    .offset(offset)
                    .limit(limit)
                    .asList();

        }
        return query.order()
                .offset(offset)
                .limit(limit)
                .asList();
    }

    @Override
    public void createUser(UserEntity newUser) {

        this.datastore.save(newUser);
    }

    @Override
    public void deleteUser(UserEntity userToDelete) {

        UserEntity user = datastore.createQuery(UserEntity.class)
                .field("id").equal(userToDelete.getId())
                .get();
        this.datastore.delete(user);
    }

    @Override
    public void updateUser(UserEntity userToUpdate) {

//         ogarnac jak to zrobic, bo nie dziala

//        Query<UserEntity> userEntityQuery = datastore.createQuery(UserEntity.class)
//                .field("id").equal(userToUpdate.getId());
//        UpdateOperations<UserEntity> userEntityUpdateOperations = datastore.createUpdateOperations(UserEntity.class);
//                userEntityUpdateOperations.set("");
    }

}
