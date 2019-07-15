package de.tobiasbecode.spring5shop.rating.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tobiasbecode.sfshop.rating.data.domain.Rating;
import de.tobiasbecode.sfshop.rating.data.repository.RatingRepository;
import de.tobiasbecode.sfshop.rating.web.controller.RatingController;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RatingControllerTest {


    @InjectMocks
    RatingController ratingController;

    @Mock
    RatingRepository ratingRepository;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ratingController = new RatingController(ratingRepository);

        mockMvc = MockMvcBuilders
                .standaloneSetup(ratingController)
                .build();
    }

    @Test
    public void getAllRatings() throws Exception {

        Rating ratingTest = new Rating();
        ratingTest.set_id(ObjectId.createFromLegacyFormat(1,1,1));

        List ratingList = new ArrayList<>();
        ratingList.add(ratingTest);

        when(ratingRepository.findAll()).thenReturn(ratingList);

        List<Rating> testRatingList = ratingController.getAllRatings();

        mockMvc.perform(get("/ratings/"))
                .andExpect(status().is(200));

        assertEquals(testRatingList.size(), 1);
        verify(ratingRepository, times(2)).findAll();
    }


    @Test
    public void getRatingByproductId() throws Exception {

        Rating rating = new Rating();
        rating.setIdproduct("34");
        rating.set_id(ObjectId.createFromLegacyFormat(1,1,1));

        when(ratingRepository.idproduct(anyString())).thenReturn(rating);

        Rating testRating = ratingController.getRatingByproductId("34");

        mockMvc.perform(get("/ratings/34"))
                .andExpect(status().is(200));

        assertEquals(testRating, rating);
        verify(ratingRepository,times(2)).idproduct(anyString());
    }

    @Test
    public void createRating() throws Exception {

        Rating rating = new Rating();
        rating.set_id(ObjectId.createFromLegacyFormat(1,1,1));

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(rating);

        when(ratingRepository.save(anyObject())).thenReturn(rating);

        Rating newRating = ratingController.createRating(rating);


        mockMvc.perform(post("/ratings/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(200));

        verify(ratingRepository,times(2)).save(anyObject());

    }


    @Test
    public void deleteRating() {
        Rating rating = new Rating();
        ObjectId id = ObjectId.createFromLegacyFormat(1,1,1);
        rating.set_id(id);

        ratingController.deleteRating(id);

        verify(ratingRepository,times(1)).delete(any());

    }
}