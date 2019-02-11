package com.prezi.backend.controller;

import com.prezi.backend.fixtures.PresentationListDTOBuilder;
import com.prezi.backend.model.Presentation;
import com.prezi.backend.response.PresentationResponseDTO;
import com.prezi.backend.service.PresentationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PresentationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PresentationController presentationController;

    @Mock
    private PresentationService presentationService;

    private List<Presentation> presentationList;

    @Before
    public void setup() {
        //runs before each test
        mockMvc = MockMvcBuilders.standaloneSetup(presentationController).build();
        presentationList = new PresentationListDTOBuilder(12).build();
    }


    @Test
    // When presentations exists return records with page 1 and perPage 10
    public void testListPresentationsSuccess() throws Exception{
        doReturn(new PresentationResponseDTO(presentationList.subList(0,10), 12)).when(presentationService).getPresentations(1, 10, 1, "");
        mockMvc.perform(get("/api/v0/presentations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("perPage", "10")
                .param("dir", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.presentationList", hasSize(10)))
                .andExpect(jsonPath("$.presentationList[0].id", is("1")))
                .andExpect(jsonPath("$.presentationList[0].title", is("Title1")))
                .andExpect(jsonPath("$.presentationList[0].thumbnail", is("thumbnail_url")))
                .andExpect(jsonPath("$.presentationList[0].creator.name", is("Adil1")))
                .andExpect(jsonPath("$.presentationList[1].id", is("2")))
                .andExpect(jsonPath("$.presentationList[1].title", is("Title2")))
                .andExpect(jsonPath("$.presentationList[1].thumbnail", is("thumbnail_url")))
                .andExpect(jsonPath("$.presentationList[1].creator.name", is("Adil2")))
                .andExpect(jsonPath("$.totalCount", is(12)));
    }

    @Test
    // When presentations exists return records with page 2 and perPage 10
    public void testListPresentationsSuccess5() throws Exception{
        doReturn(new PresentationResponseDTO(presentationList.subList(10,12), 2)).when(presentationService).getPresentations(2, 10, 1, "");
        mockMvc.perform(get("/api/v0/presentations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "2")
                .param("perPage", "10")
                .param("dir", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.presentationList", hasSize(2)))
                .andExpect(jsonPath("$.presentationList[0].id", is("11")))
                .andExpect(jsonPath("$.presentationList[0].title", is("Title11")))
                .andExpect(jsonPath("$.presentationList[0].thumbnail", is("thumbnail_url")))
                .andExpect(jsonPath("$.presentationList[0].creator.name", is("Adil11")))
                .andExpect(jsonPath("$.presentationList[1].id", is("12")))
                .andExpect(jsonPath("$.presentationList[1].title", is("Title12")))
                .andExpect(jsonPath("$.presentationList[1].thumbnail", is("thumbnail_url")))
                .andExpect(jsonPath("$.presentationList[1].creator.name", is("Adil12")))
                .andExpect(jsonPath("$.totalCount", is(2)));
    }

    @Test
    // When presentations exists return records sorted ascending
    public void testListPresentationsSuccess2() throws Exception{
        doReturn(new PresentationResponseDTO(presentationList.subList(0,10), 12)).when(presentationService).getPresentations(1, 10, 1, "");
        mockMvc.perform(get("/api/v0/presentations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("perPage", "10")
                .param("dir", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.presentationList", hasSize(10)))
                .andExpect(jsonPath("$.presentationList[0].id", is("1")))
                .andExpect(jsonPath("$.presentationList[0].title", is("Title1")))
                .andExpect(jsonPath("$.presentationList[0].thumbnail", is("thumbnail_url")))
                .andExpect(jsonPath("$.presentationList[0].creator.name", is("Adil1")))
                .andExpect(jsonPath("$.presentationList[9].id", is("10")))
                .andExpect(jsonPath("$.presentationList[9].title", is("Title10")))
                .andExpect(jsonPath("$.presentationList[9].thumbnail", is("thumbnail_url")))
                .andExpect(jsonPath("$.presentationList[9].creator.name", is("Adil10")))
                .andExpect(jsonPath("$.totalCount", is(12)));
    }

    @Test
    // When presentations exists return records sorted descending
    public void testListPresentationsSuccess3() throws Exception{
        List<Presentation> listToReturn = new ArrayList<>(presentationList);
        listToReturn.sort(Comparator.comparing(Presentation::getCreatedAt).reversed());
        doReturn(new PresentationResponseDTO(listToReturn.subList(0, 10), 12)).when(presentationService).getPresentations(1, 10, -1, "");
        mockMvc.perform(get("/api/v0/presentations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("perPage", "10")
                .param("dir", "-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.presentationList", hasSize(10)))
                .andExpect(jsonPath("$.presentationList[0].id", is("12")))
                .andExpect(jsonPath("$.presentationList[0].title", is("Title12")))
                .andExpect(jsonPath("$.presentationList[0].thumbnail", is("thumbnail_url")))
                .andExpect(jsonPath("$.presentationList[0].creator.name", is("Adil12")))
                .andExpect(jsonPath("$.presentationList[9].id", is("3")))
                .andExpect(jsonPath("$.presentationList[9].title", is("Title3")))
                .andExpect(jsonPath("$.presentationList[9].thumbnail", is("thumbnail_url")))
                .andExpect(jsonPath("$.presentationList[9].creator.name", is("Adil3")))
                .andExpect(jsonPath("$.totalCount", is(12)));
    }

    @Test
    // When presentations exists return records filtered By Name
    public void testListPresentationsSuccess4() throws Exception{
        List<Presentation> listToReturn = new ArrayList<>(presentationList);
        listToReturn = listToReturn
                .stream()
                .filter(p-> p.getTitle().startsWith("Title5"))
                .collect(Collectors.toList());
        doReturn(new PresentationResponseDTO(listToReturn, 1)).when(presentationService).getPresentations(1, 10, 1, "Title5");
        mockMvc.perform(get("/api/v0/presentations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("perPage", "10")
                .param("dir", "1")
                .param("title", "Title5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.presentationList", hasSize(1)))
                .andExpect(jsonPath("$.presentationList[0].id", is("5")))
                .andExpect(jsonPath("$.presentationList[0].title", is("Title5")))
                .andExpect(jsonPath("$.presentationList[0].thumbnail", is("thumbnail_url")))
                .andExpect(jsonPath("$.presentationList[0].creator.name", is("Adil5")))
                .andExpect(jsonPath("$.totalCount", is(1)));
    }

    @Test
    // When presentations do not exist because invalid page was requested
    public void testListPresentationsFailure1() throws Exception{
        doThrow(new IllegalArgumentException()).when(presentationService).getPresentations(10, 10, 1, "");
        mockMvc.perform(get("/api/v0/presentations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "10")
                .param("perPage", "10")
                .param("dir", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Page size or page number is invalid.")))
                .andExpect(jsonPath("$.status", is(1000)));

    }

    @Test
    // When any exception occured
    public void testListPresentationsFailure2() throws Exception{
        doThrow(new NullPointerException()).when(presentationService).getPresentations(10, 10, 1, "");
        mockMvc.perform(get("/api/v0/presentations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "10")
                .param("perPage", "10")
                .param("dir", "1"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", is("Some error occurred, please try again later.")))
                .andExpect(jsonPath("$.status", is(1001)));

    }
}
