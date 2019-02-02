package com.prezi.backend.controller;

import com.prezi.backend.jsonMapper.JsonMapperFactory;
import com.prezi.backend.model.Presentation;
import com.prezi.backend.response.SimpleResponseDTO;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v0")
public class PresentationController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PresentationController.class);
    private JsonMapperFactory jsonMapperFactory;

    public PresentationController(JsonMapperFactory jsonMapperFactory){
        this.jsonMapperFactory = jsonMapperFactory;
    }


    @GetMapping("/presentations")
    public ResponseEntity index(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "perPage", defaultValue = "10") Integer perPage
            ){
        try {
            List<Presentation> presentations = this.jsonMapperFactory.getPresentationsPaginated(page, perPage);
            return new ResponseEntity(presentations, HttpStatus.OK);
        }
        catch (IllegalArgumentException ex){
            SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO("Page size or page number is invalid.", 1000);
            return new ResponseEntity(simpleResponseDTO, HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error("Some error occurred while listing presentations: {} ", ex.getStackTrace());
            SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO("Some error occurred, please try again later.", 1001);
            return new ResponseEntity(simpleResponseDTO, HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
//
//    @GetMapping("/presentations/{id}")
//    public ResponseEntity get(){
//        List<Presentation> presentations = this.jsonMapperFactory.getPresentations();
//        return new ResponseEntity(
//                new SimpleResponseDTO("Your response has been logged successfully"),
//                HttpStatus.OK
//        );
//    }

}
