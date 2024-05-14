package de.hse.gruppe8.orm.dao;

import de.hse.gruppe8.exception.NoUserFoundException;
import de.hse.gruppe8.orm.model.UserEntity;
import de.hse.gruppe8.util.mapper.UserMapper;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserDao {
    @Inject
    EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(UserDao.class);


    public UserEntity getUser(Long id) {
        return entityManager.find(UserEntity.class, id);
    }

    public UserEntity login(String username, String password) {
        try {
            LOGGER.debug("Checking for user name and password");
            return (UserEntity) entityManager.createQuery("SELECT user FROM UserEntity user " +
                            "WHERE user.username=:username " +
                            "AND user.password=:password " +
                            "AND user.active=true")
                    .setParameter("username", username)
                    .setParameter("password", password).getSingleResult();

        } catch (NoResultException e) {
            throw new NoUserFoundException(username);
        }
    }

    public List<UserEntity> getUsers() {
        Query q = entityManager.createQuery("select users from UserEntity users where users.active = TRUE");
        List<UserEntity> users = (List<UserEntity>) q.getResultList();
        return users;
    }

    @Transactional
    public UserEntity save(UserEntity user) {
        if (user.getId() != null) {
            user = entityManager.merge(user);
        } else {
            entityManager.persist(user);
        }
        return user;
    }

    @Transactional
    public void delete(UserEntity user) {
        if (user != null) {
            user.setActive(false);
            save(user);
        }
    }

    @Transactional
    public void removeAll() {
        Query del = entityManager.createQuery("DELETE FROM UserEntity WHERE id >= 0");
        del.executeUpdate();
    }

    public List<UserEntity> getUsersFromCompany(Long id) {
        Query q = entityManager.createQuery("select users from UserEntity users where users.active = TRUE AND users.company.id =:id");
        q.setParameter("id", id);
        List<UserEntity> users = (List<UserEntity>) q.getResultList();
        return users;
    }
}
