package api.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.rest.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
