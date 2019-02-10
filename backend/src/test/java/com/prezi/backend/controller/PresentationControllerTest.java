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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
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
    // When payments exists return records
    public void testListPresentationsSuccess() throws Exception{
        doReturn(new PresentationResponseDTO(presentationList.subList(0,10), 12)).when(presentationService).getPresentations(1, 10, 1, "");
        mockMvc.perform(get("/api/v0/presentations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("perPage", "10")
                .param("sortDirection", "1"))
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
}
