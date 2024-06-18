package com.github.nowickimaciej.occupancy;

import com.github.nowickimaciej.occupancy.dto.OccupancyCalculationRequest;
import com.github.nowickimaciej.occupancy.dto.OccupancyCalculationResponse;
import org.springframework.stereotype.Service;

@Service
public class RoomOccupancyService {

	public OccupancyCalculationResponse calculateOccupancy(OccupancyCalculationRequest request) {
		RoomOccupancyManager roomOccupancyManager = RoomOccupancyManager.calculate(
				request.freeEconomyRooms(),
				request.freePremiumRooms(),
				request.customerRates()
		);

		return new OccupancyCalculationResponse(
				roomOccupancyManager.getEconomyRoomsUsed(),
				roomOccupancyManager.getPremiumRoomsUsed(),
				roomOccupancyManager.getEconomyRoomsValue(),
				roomOccupancyManager.getPremiumRoomsValue()
		);
	}

}
