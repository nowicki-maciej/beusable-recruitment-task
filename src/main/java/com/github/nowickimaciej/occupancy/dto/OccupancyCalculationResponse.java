package com.github.nowickimaciej.occupancy.dto;

import java.math.BigDecimal;

public record OccupancyCalculationResponse(
		int economyRoomsUsed,
		int premiumRoomsUsed,
		BigDecimal economyRoomsValue,
		BigDecimal premiumRoomsValue) {
}
