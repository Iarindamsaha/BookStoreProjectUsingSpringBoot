package com.bookstore.repository;

import com.bookstore.models.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDetailsRepo extends JpaRepository<UserDetailsModel,Integer> {

    UserDetailsModel findByEmail(String email);
    UserDetailsModel findByUsername(String username);
    UserDetailsModel findByUsernameAndPassword(String username, String password);

}
