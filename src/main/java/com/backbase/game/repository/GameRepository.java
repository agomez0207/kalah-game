package com.backbase.game.repository;

import com.backbase.game.repository.dao.GameDAO;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface for game CRUD operations.
 *
 * @author andres.gomez
 */
public interface GameRepository extends CrudRepository<GameDAO, Integer> {
}
