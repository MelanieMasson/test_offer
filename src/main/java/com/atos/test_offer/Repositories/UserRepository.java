package com.atos.test_offer.Repositories;

import com.atos.test_offer.Entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
}
