package com.developerstack.service;

import com.developerstack.model.*;
import com.developerstack.repository.PlayerRepository;
import com.developerstack.repository.TeamRepository;
import com.developerstack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SoccerService {

	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private UserRepository userRepository;

	public List<String> getAllTeamPlayers(long teamId) {
		List<String> result = new ArrayList<String>();
		List<Player> players = playerRepository.findByTeamId(teamId);
		for (Player player : players) {
			result.add(player.getName());
		}

		return result;
	}

	public void addPlayer(Player p){
		playerRepository.save(p);
	}


	public void addBarcelonaPlayer(String name, String position, int number) {
		
		Team barcelona = teamRepository.findOne(1l);
		
		Player newPlayer = new Player();
		newPlayer.setName(name);
		newPlayer.setPosition(position);
		newPlayer.setNum(number);
		newPlayer.setTeam(barcelona);
		playerRepository.save(newPlayer);
	}

	public Player getPlayer(Long playerId) {
		return playerRepository.findOne(playerId);
	}

	public Iterable<Player> getAllPlayers(){
		return playerRepository.findAll();
	}
	public Iterable<User> getUsers(){
		return userRepository.findAll();
	}


	public Team getTeam(Long teamId) {
		return teamRepository.findOne(teamId);
	}

	public void deletePlayer(Long playerId) {
		playerRepository.delete(playerId);
	}

	public void giveRed(Long playerId) {
		Player player = getPlayer(playerId);
		player.addCard(new Card(CardType.RED));
		playerRepository.save(player);
	}

	public void giveYellow(Long playerId) {
		Player player = getPlayer(playerId);
		player.addCard(new Card(CardType.YELLOW));
		playerRepository.save(player);
	}
}
