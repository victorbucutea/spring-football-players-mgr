package com.webappsecurity.controller;

import com.webappsecurity.model.Player;
import com.webappsecurity.model.Vote;
import com.webappsecurity.repository.VoteRepository;
import com.webappsecurity.service.SoccerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PublicController {


    @Autowired
    SoccerService service;


    @Autowired
    VoteRepository voteRepository;


    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        return goToPublic();
    }

    @RequestMapping(value = "/public/vote", method = RequestMethod.GET)
    public ModelAndView vote(@RequestParam("id") Long playerId, @RequestParam("voter") String voter) {
        Player player = service.getPlayer(playerId);
        Vote vote = new Vote();
        vote.setUser(voter);
        player.addVote(vote);
        return goToPublic(voter);
    }

    @RequestMapping(value = "/public/voteDown", method = RequestMethod.GET)
    public ModelAndView voteDown(@RequestParam("id") Long playerId, @RequestParam("voter") String voter) {
        Player player = service.getPlayer(playerId);
        if (player.getVotes().size() > 0) {
            Vote next = player.getVotes().iterator().next();
            voteRepository.delete(next);
            player.removeVote(next);
        }
        return goToPublic(voter);
    }


    private ModelAndView goToPublic() {
        ModelAndView model = new ModelAndView();
        model.addObject("players", service.getAllPlayers());
        model.setViewName("public");
        return model;
    }


    private ModelAndView goToPublic(String voter) {
        ModelAndView model = new ModelAndView();
        model.addObject("players", service.getAllPlayers());
        model.addObject("voter", voter);
        model.setViewName("public");
        return model;
    }
}
