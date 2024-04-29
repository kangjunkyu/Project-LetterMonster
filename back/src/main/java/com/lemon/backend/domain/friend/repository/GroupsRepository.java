package com.lemon.backend.domain.friend.repository;

import com.lemon.backend.domain.friend.entity.GroupsInfo;
import com.lemon.backend.domain.friend.repository.custom.GroupsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<GroupsInfo, Long>, GroupsRepositoryCustom {
}
