package com.lemon.backend.domain.users.user.repository;

import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer>, UserRepositoryCustom {


}
