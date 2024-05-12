package org.example.spring_security_cognito.repositories;

import org.example.spring_security_cognito.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByUserIdOrderByUpdatedAtDesc(String id);

    List<Post> findAllByOrderByUpdatedAtDesc();

}
