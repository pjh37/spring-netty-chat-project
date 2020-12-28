package com.netty.practice.domain.User;

import com.netty.practice.domain.User.SessionUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRedisRepository extends CrudRepository<SessionUser,Long> {
}
