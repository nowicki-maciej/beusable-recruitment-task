package com.github.nowickimaciej;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomOccupancyManagerApplicationTests {

	private static final String PAYLOAD = """
			{
			    "freeEconomyRooms": 1,
			    "freePremiumRooms": 7,
			    "customerRates": [
			        23,
			        45,
			        155,
			        374,
			        22,
			        99.99,
			        100,
			        101,
			        115,
			        209
			    ]
			}""";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldCalculateRoomOccupancy() throws Exception {
		mockMvc.perform(post("/room-occupancy").content(PAYLOAD).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.economyRoomsUsed").value(1))
				.andExpect(jsonPath("$.premiumRoomsUsed").value(7))
				.andExpect(jsonPath("$.economyRoomsValue").value(45))
				.andExpect(jsonPath("$.premiumRoomsValue").value("1153.99"));
	}


}
