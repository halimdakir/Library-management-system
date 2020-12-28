package se.iths.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.dto.UserInfoDTO;
import se.iths.library.models.Roles;
import se.iths.library.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

        @Query("SELECT new se.iths.library.dto.UserInfoDTO(l.active, u.fullName, l.email, l.password, l.roles, u.birthDate, u.address, u.id, l.id) FROM User u INNER JOIN u.login l WHERE l.roles=:roles")
        List<UserInfoDTO> findByLogin_Roles(Roles roles);

        @Query("SELECT new se.iths.library.dto.UserInfoDTO(l.active, u.fullName, l.email, l.password, l.roles, u.birthDate, u.address, u.id, l.id) FROM User u INNER JOIN u.login l")
        List<UserInfoDTO> getAllUserInfos();

        @Query("SELECT u FROM User u INNER JOIN u.login l WHERE l.email=:email")
        User findByLogin_Email(String email);


}
