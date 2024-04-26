package com.lemon.backend.domain.friend.repository;

import com.lemon.backend.domain.friend.entity.Groups;
import com.lemon.backend.domain.friend.repository.custom.GroupsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Groups, Long>, GroupsRepositoryCustom {
}
