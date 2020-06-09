package com.webappsecurity.controller;

import com.webappsecurity.model.Player;
import com.webappsecurity.model.Team;
import com.webappsecurity.service.SoccerService;
import org.owasp.esapi.errors.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Collection;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);


    @Autowired
    SoccerService service;

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        return goToDashboard(false);
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public ModelAndView add(Player p, @RequestParam("team") Long teamId, @RequestParam("file") MultipartFile file) throws IOException {
        Team team = service.getTeam(teamId);
        p.setTeam(team);
        p.setImage(file.getOriginalFilename());
        service.addPlayer(p);
        service.saveFile(file.getInputStream(), file.getOriginalFilename());
        logger.info("Added player " + p.getName());
        return goToDashboard(true);
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public StreamingResponseBody streamFile(@RequestParam("filename") String filename,
                                            HttpServletResponse response) throws IOException, ValidationException {
        logger.info("Downloading file: "+filename);

        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        String downloadFolder = "uploads";

        java.io.File f = new java.io.File(downloadFolder, filename);
         InputStream inputStream =  new FileInputStream(f);
//        InputStream inputStream = new URL("file://" + downloadFolder + "/"+ filename).openStream();

        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
            inputStream.close();
        };
    }


    @RequestMapping(value = "/image/{imageName}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
        File uploadedFile = new File("uploads", imageName  + ".jpg");
        if (!uploadedFile.exists()) {
            String realPath = context.getRealPath("ui/static");
            uploadedFile = new File(realPath, imageName  + ".jpg");
        }

        return Files.readAllBytes(uploadedFile.toPath());
    }


    @RequestMapping(value = "/player/{id}/red", method = RequestMethod.GET)
    public ModelAndView giveRed(@PathVariable("id") Long playerId) {
        service.giveRed(playerId);
        return goToDashboard(true);
    }

    @RequestMapping(value = "/player/{id}/yellow", method = RequestMethod.GET)
    public ModelAndView giveYellow(@PathVariable("id") Long playerId) {
        service.giveYellow(playerId);
        return goToDashboard(true);
    }

    @RequestMapping(value = "/player/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") Long playerId) {
        service.deletePlayer(playerId);
        return goToDashboard(true);
    }


    private ModelAndView goToDashboard(boolean isRedirect) {
        ModelAndView model = new ModelAndView();
        model.addObject("isMatchReferee", hasRole("match_referee"));
        model.addObject("user", getUserName());
        model.addObject("players", service.getAllPlayers());
        if (isRedirect)
            model.setViewName("redirect:/dashboard");
        else
            model.setViewName("/dashboard");


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
