package com.comarch.tomasz.kosacki.db;

import com.comarch.tomasz.kosacki.dao.UserDao;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.mongodb.morphia.Datastore;

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
        datastore.save(new UserEntity("2", "FN1", "LN2", "email2@email.com", new Date()));
    }

    @Override
    public List<UserEntity> getAllUsers() {

        return datastore.createQuery(UserEntity.class)
                .asList();
    }

    @Override
    public UserEntity getUserById(String userId) {

        return datastore.createQuery(UserEntity.class)
                .field("id").equal(userId)
                .get();
    }

    @Override
    public List<UserEntity> getUserByFirstName(String userFirstName) {

        return datastore.createQuery(UserEntity.class)
                .field("firstName").equal(userFirstName)
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
    public void updateUser(UserEntity user) {

        UserEntity updatedUser = getUserById(user.getId());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
    }

}
