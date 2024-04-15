package com.lemon.backend.domain.users.repository;

import com.lemon.backend.domain.users.entity.Users;
import com.lemon.backend.domain.users.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer>, UserRepositoryCustom {


}
