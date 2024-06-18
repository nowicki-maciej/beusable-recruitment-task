package com.github.nowickimaciej.occupancy.dto;

import java.math.BigDecimal;
import java.util.List;

public record OccupancyCalculationRequest(
		int freeEconomyRooms,
		int freePremiumRooms,
		List<BigDecimal> customerRates) {
}
