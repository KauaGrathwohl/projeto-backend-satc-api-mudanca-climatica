package com.satc.projeto.mudancaclimatica.repository;

import com.satc.projeto.mudancaclimatica.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}