package com.prezi.backend.controller;

import com.prezi.backend.response.PresentationResponseDTO;
import com.prezi.backend.response.SimpleResponseDTO;
import com.prezi.backend.service.PresentationService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v0")
public class PresentationController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PresentationController.class);
    private PresentationService presentationService;

    public PresentationController(PresentationService presentationService){
        this.presentationService = presentationService;
    }


    /*
     API to List Presentations sorted by createdAt (Paginated)
     Params:
        1. page (page number)
        2. perPage (number of data to show per page)
        3. dir (direction of sorting [sort by createdAt], 1 = ascending, -1 = descending)
        4. title (search by title, leave empty to list all)
      Returns:
        An object of format: {presentationList: [], totalCount: 0}
     */

    @GetMapping("/presentations")
    public ResponseEntity index(
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "perPage", defaultValue = "10", required = false) Integer perPage,
            @RequestParam(name = "dir", defaultValue = "1", required = false) Integer direction,
            @RequestParam(name = "title", defaultValue = "", required = false) String title
            ){
        try {
            PresentationResponseDTO presentationResponseDTO = this.presentationService.getPresentations(page, perPage, direction, title);
            return new ResponseEntity(presentationResponseDTO, HttpStatus.OK);
        }
        catch (IllegalArgumentException ex){
            SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO("Page size or page number is invalid.", 1000);
            return new ResponseEntity(simpleResponseDTO, HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error("Some error occurred while listing presentations: ", ex);
            SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO("Some error occurred, please try again later.", 1001);
            return new ResponseEntity(simpleResponseDTO, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
