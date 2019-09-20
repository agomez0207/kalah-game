package com.backbase.game.repository;

import com.backbase.game.repository.dao.GameDAO;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<GameDAO, Integer> {
}
