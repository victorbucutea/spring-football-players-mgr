package com.developerstack.rest;


import com.developerstack.service.SoccerService;
import org.apache.jasper.tagplugins.jstl.core.Url;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static java.util.Arrays.asList;

@RestController
public class PlayerRestService {

    Logger logger = LoggerFactory.getLogger(PlayerRestService.class);


    @Autowired
    private SoccerService service;

    @RequestMapping(value = "/player/{id}")
    public ResponseEntity getPlayer(@PathVariable("id") Long playerId) {
        return ResponseEntity.ok(service.getPlayer(playerId));
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public StreamingResponseBody streamFile(@RequestParam("filename") String filename,
                                            HttpServletResponse response) throws IOException {
        logger.info("Downloading file: "+filename);

        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");


        String downloadFolder = "/Users/victor/projects/craiova/spring-security-example/src/main/webapp/ui/static/";


        InputStream inputStream = new URL("file://" + downloadFolder + filename).openStream();
        // new FileInputStream(downloadFolder + filename);

        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
            inputStream.close();
        };
    }

}
