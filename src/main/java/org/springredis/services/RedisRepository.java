package org.springredis.services;

import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<String, String> {
}
