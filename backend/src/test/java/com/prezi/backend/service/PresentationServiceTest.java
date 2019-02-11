package com.prezi.backend.service;

import com.prezi.backend.fixtures.PresentationListDTOBuilder;
import com.prezi.backend.jsonmapper.PresentationData;
import com.prezi.backend.model.Presentation;
import com.prezi.backend.response.PresentationResponseDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class PresentationServiceTest {

    @InjectMocks
    private PresentationService presentationService;
    @Mock
    private PresentationData presentationData;
    private List<Presentation> presentationList;

    @Before
    public void setup() {
        //runs before each test
        presentationService = new PresentationService(presentationData);
        presentationList = new PresentationListDTOBuilder(12).build();
    }

    @After
    public void clear(){
        //runs before each test
        presentationService = null;

    }

    @Test
    // Test getPresentations ascending
    public void testPresentations() throws Exception{
        doReturn(new PresentationResponseDTO(presentationList.subList(0,10), 12)).when(presentationData).getSortedPaginatedDataByTitle(1, 10, 1, "");
        PresentationResponseDTO presentationResponseDTO = presentationService.getPresentations(1, 10, 1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals("Title1", presentationResponseDTO.getPresentationList().get(0).getTitle());
        assertEquals("Title2", presentationResponseDTO.getPresentationList().get(1).getTitle());
        assertEquals("Title3", presentationResponseDTO.getPresentationList().get(2).getTitle());
    }

    @Test
    // Test getPresentations descending
    public void testPresentations1() throws Exception{
        List<Presentation> listToReturn = new ArrayList<>(presentationList);
        listToReturn.sort(Comparator.comparing(Presentation::getCreatedAt).reversed());
        doReturn(new PresentationResponseDTO(listToReturn.subList(0,10), 12)).when(presentationData).getSortedPaginatedDataByTitle(1, 10, -1, "");
        PresentationResponseDTO presentationResponseDTO = presentationService.getPresentations(1, 10, -1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals("Title12", presentationResponseDTO.getPresentationList().get(0).getTitle());
        assertEquals("Title11", presentationResponseDTO.getPresentationList().get(1).getTitle());
        assertEquals("Title10", presentationResponseDTO.getPresentationList().get(2).getTitle());
    }

    @Test
    // Test getPresentations title
    public void testPresentations3() throws Exception{
        List<Presentation> listToReturn = new ArrayList<>(presentationList);
        listToReturn = listToReturn
                .stream()
                .filter(p-> p.getTitle().startsWith("Title3"))
                .collect(Collectors.toList());
        doReturn(new PresentationResponseDTO(listToReturn, 1)).when(presentationData).getSortedPaginatedDataByTitle(1, 10, 1, "Title3");
        PresentationResponseDTO presentationResponseDTO = presentationService.getPresentations(1, 10, 1, "Title3");
        assertEquals(Integer.valueOf(1), presentationResponseDTO.getTotalCount());
        assertEquals("Title3", presentationResponseDTO.getPresentationList().get(0).getTitle());
    }


}
