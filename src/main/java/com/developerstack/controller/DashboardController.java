package com.developerstack.controller;

import java.util.Collection;

import com.developerstack.model.*;
import com.developerstack.repository.PlayerRepository;
import com.developerstack.service.SoccerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);


    @Autowired
    SoccerService service;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        return goToDashboard();
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public ModelAndView add(Player p, @RequestParam("team") Long teamId) {
        Team team = service.getTeam(teamId);
        p.setTeam(team);
        service.addPlayer(p);
        logger.info("Added player " + p.getName());
        return goToDashboard();
    }


    @RequestMapping(value = "/player/{id}/red", method = RequestMethod.GET)
    public ModelAndView giveRed(@PathVariable("id") Long playerId) {
        service.giveRed(playerId);
        return goToDashboard();
    }

    @RequestMapping(value = "/player/{id}/yellow", method = RequestMethod.GET)
    public ModelAndView giveYellow(@PathVariable("id") Long playerId) {
        service.giveYellow(playerId);
        return goToDashboard();
    }

    @RequestMapping(value = "/player/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") Long playerId) {
        service.deletePlayer(playerId);
        return goToDashboard();
    }


    private ModelAndView goToDashboard() {
        ModelAndView model = new ModelAndView();
        model.addObject("isMatchReferee", hasRole("match_referee"));
        model.addObject("user", getUserName());
        model.addObject("players", service.getAllPlayers());
        model.setViewName("dashboard");
        return model;
    }

    private boolean hasRole(String role) {
        Collection<? extends GrantedAuthority> authorities = getUser().getAuthorities();

        for (GrantedAuthority auth : authorities) {
            if (auth.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }

    private Authentication getUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((User) authentication.getPrincipal()).getUsername();
    }

}
