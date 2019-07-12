package com.comarch.tomasz.kosacki.db;

import com.comarch.tomasz.kosacki.dao.UserDao;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDB implements UserDao {

    private List<UserEntity> users = new ArrayList<>();
    private Datastore datastore;

    public UserDB() {
        this.users.add(new UserEntity("1", "FN1", "LN1", "email1@email.com", new Date()));
        this.users.add(new UserEntity("2", "FN1", "LN2", "email2@email.com", new Date()));
        this.users.add(new UserEntity("3", "FN3", "LN3", "email3@email.com", new Date()));
        this.users.add(new UserEntity("4", "FN4", "LN4", "email4@email.com", new Date()));
    }

    public UserDB(Datastore datastore) {
        this.datastore = datastore;
        initializeDB(); // do usuniecia
    }

    //do usuniecia
    public void initializeDB() {
        datastore.save(new UserEntity("1", "FN1", "LN1", "email1@email.com", new Date()));
        datastore.save(new UserEntity("2", "FN1", "LN2", "email2@email.com", new Date()));
    }

    @Override
    public List<UserEntity> getAllUsers() {

        List<UserEntity> userEntityList = datastore.createQuery(UserEntity.class)
                .asList();
        return userEntityList;
    }

    @Override
    public UserEntity getUserById(String userId) {

        UserEntity userEntity = datastore.createQuery(UserEntity.class)
                .field("id").equal(userId)
                .get();

        return userEntity;
    }

    @Override
    public List<UserEntity> getUserByFirstName(String userFirstName) {

        List<UserEntity> userEntityList = datastore.createQuery(UserEntity.class)
                .field("firstName").equal(userFirstName)
                .asList();
        return userEntityList;
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
