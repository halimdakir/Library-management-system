package se.iths.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT DISTINCT u FROM User u INNER JOIN FETCH u.login l WHERE l.isAdmin = false ")
    Iterable<User> findUsersNotAdmin();

    Iterable<User> findUsersByLogin_IsAdmin(boolean isAdmin);
}
