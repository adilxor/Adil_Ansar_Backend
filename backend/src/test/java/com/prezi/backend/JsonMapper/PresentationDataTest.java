package com.prezi.backend.jsonmapper;

import com.prezi.backend.fixtures.PresentationListDTOBuilder;
import com.prezi.backend.model.Presentation;
import com.prezi.backend.response.PresentationResponseDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PresentationDataTest {
    @InjectMocks
    private PresentationData presentationData;
    private List<Presentation> presentationList;

    @Before
    public void setup() {
        presentationList = new PresentationListDTOBuilder(12).build();
    }

    @Test
    // Test Filter
    public void testFilterByTitle() throws  Exception{
        presentationData.setPresentations(presentationList);
        List<Presentation> filteredList = presentationData.filterByTitle("Title3");
        assertEquals(1, filteredList.size());
        assertEquals("Title3", filteredList.get(0).getTitle());
        filteredList = presentationData.filterByTitle("Title8");
        assertEquals(1, filteredList.size());
        assertEquals("Title8", filteredList.get(0).getTitle());
    }

    @Test
    // Test Sorted Paginated Data Function for pagination page number
    public void testGetSortedPaginatedDataByTitle1() throws  Exception{
        presentationData.setPresentations(presentationList);
        PresentationResponseDTO presentationResponseDTO = presentationData.getSortedPaginatedDataByTitle(1, 5, 1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals(5, presentationResponseDTO.getPresentationList().size());

        presentationResponseDTO = presentationData.getSortedPaginatedDataByTitle(2, 5, 1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals(5, presentationResponseDTO.getPresentationList().size());

        presentationResponseDTO = presentationData.getSortedPaginatedDataByTitle(3, 5, 1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals(2, presentationResponseDTO.getPresentationList().size());
    }

    @Test
    // Test Sorted Paginated Data Function for pagination perPage
    public void testGetSortedPaginatedDataByTitle2() throws  Exception{
        presentationData.setPresentations(presentationList);
        PresentationResponseDTO presentationResponseDTO = presentationData.getSortedPaginatedDataByTitle(1, 5, 1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals(5, presentationResponseDTO.getPresentationList().size());

        presentationResponseDTO = presentationData.getSortedPaginatedDataByTitle(1, 10, 1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals(10, presentationResponseDTO.getPresentationList().size());

        presentationResponseDTO = presentationData.getSortedPaginatedDataByTitle(1, 15, 1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals(12, presentationResponseDTO.getPresentationList().size());
    }

    @Test
    // Test Sorted Paginated Data Function for sort order
    public void testGetSortedPaginatedDataByTitle3() throws  Exception{
        presentationData.setPresentations(presentationList);
        PresentationResponseDTO presentationResponseDTO = presentationData.getSortedPaginatedDataByTitle(1, 10, 1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals("Title1", presentationResponseDTO.getPresentationList().get(0).getTitle());

        presentationResponseDTO = presentationData.getSortedPaginatedDataByTitle(1, 10, -1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals("Title12", presentationResponseDTO.getPresentationList().get(0).getTitle());

        presentationResponseDTO = presentationData.getSortedPaginatedDataByTitle(2, 10, -1, "");
        assertEquals(Integer.valueOf(12), presentationResponseDTO.getTotalCount());
        assertEquals("Title2", presentationResponseDTO.getPresentationList().get(0).getTitle());
    }
}
