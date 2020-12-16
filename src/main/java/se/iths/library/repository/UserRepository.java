package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

        Iterable<User> findUsersByLogin_IsAdmin(boolean isAdmin);

}
