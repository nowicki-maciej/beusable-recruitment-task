package com.github.nowickimaciej.occupancy;

import com.github.nowickimaciej.occupancy.dto.OccupancyCalculationRequest;
import com.github.nowickimaciej.occupancy.dto.OccupancyCalculationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room-occupancy")
class RoomOccupancyController {

	private final RoomOccupancyService roomOccupancyService;

	RoomOccupancyController(RoomOccupancyService roomOccupancyService) {
		this.roomOccupancyService = roomOccupancyService;
	}

	@PostMapping
	OccupancyCalculationResponse calculateOccupancy(@RequestBody OccupancyCalculationRequest request) {
		return roomOccupancyService.calculateOccupancy(request);
	}

}
