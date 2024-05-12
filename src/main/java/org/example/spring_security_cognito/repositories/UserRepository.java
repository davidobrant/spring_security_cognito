package org.example.spring_security_cognito.repositories;

import org.example.spring_security_cognito.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {


}
