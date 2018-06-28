package com.developerstack.repository;

import com.developerstack.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

	List<Player> findByTeamId(long teamId);
}
