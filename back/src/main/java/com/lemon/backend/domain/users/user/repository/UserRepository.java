package com.lemon.backend.domain.users.user.repository;

import com.lemon.backend.domain.users.user.entity.Social;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.custom.UserRepositoryCustom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer>, UserRepositoryCustom {


    long countByNickname(String nickname);

    Optional<Users> findByProviderAndProviderId(Social social, String providerId);

    @Query("select u from Users u left join fetch u.characterList where u.id = :userId")
    Optional<Users> findByIdFetch(@Param("userId")int userId);

    @Query(value = "SELECT * FROM users WHERE provider = :provider AND provider_id = :providerId", nativeQuery=true)
    Optional<Users> findByProviderAndProviderIdIgnoreDeleted(@Param("provider") String provider, @Param("providerId") String providerId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Users SET is_deleted = false WHERE users_id = :id AND is_deleted = true", nativeQuery = true)
    void updateIsDeleted(Integer id);
}
