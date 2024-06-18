package com.github.nowickimaciej.occupancy;

import com.github.nowickimaciej.occupancy.dto.OccupancyCalculationRequest;
import com.github.nowickimaciej.occupancy.dto.OccupancyCalculationResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomOccupancyServiceTest {

	private static final List<BigDecimal> CUSTOMER_RATES = List.of(
			new BigDecimal(23),
			new BigDecimal(45),
			new BigDecimal(155),
			new BigDecimal(374),
			new BigDecimal(22),
			new BigDecimal("99.99"),
			new BigDecimal(100),
			new BigDecimal(101),
			new BigDecimal(115),
			new BigDecimal(209)
	);

	private static final List<BigDecimal> ONLY_PREMIUM_RATES = List.of(
			new BigDecimal(100),
			new BigDecimal(101),
			new BigDecimal(102)
	);

	private static final List<BigDecimal> ONLY_ECONOMY_RATES = List.of(
			new BigDecimal(50),
			new BigDecimal(51),
			new BigDecimal(52)
	);

	private final RoomOccupancyService roomOccupancyService = new RoomOccupancyService();

	@ParameterizedTest
	@MethodSource("shouldCalculateOccupancyArguments")
	void shouldCalculateOccupancy(OccupancyCalculationRequest request, OccupancyCalculationResponse response) {
		OccupancyCalculationResponse calculatedResponse = roomOccupancyService.calculateOccupancy(request);

		assertEquals(response, calculatedResponse);
	}

	private static Stream<Arguments> shouldCalculateOccupancyArguments() {
		return Stream.of(
				Arguments.of(new OccupancyCalculationRequest(3, 3, CUSTOMER_RATES), new OccupancyCalculationResponse(3, 3, new BigDecimal("167.99"), new BigDecimal(738))),
				Arguments.of(new OccupancyCalculationRequest(5, 7, CUSTOMER_RATES), new OccupancyCalculationResponse(4, 6, new BigDecimal("189.99"), new BigDecimal(1054))),
				Arguments.of(new OccupancyCalculationRequest(7, 2, CUSTOMER_RATES), new OccupancyCalculationResponse(4, 2, new BigDecimal("189.99"), new BigDecimal(583))),
				 Arguments.of(new OccupancyCalculationRequest(1, 7, CUSTOMER_RATES), new OccupancyCalculationResponse(1, 7, new BigDecimal(45), new BigDecimal("1153.99"))),
				// following test does not work. Having the smallest value equal to "99.99", it is impossible to get "45.99" as a result
				// Arguments.of(new OccupancyCalculationRequest(1, 7, CUSTOMER_RATES), new OccupancyCalculationResponse(1, 7, new BigDecimal("45.99"), new BigDecimal(1153)))
				Arguments.of(new OccupancyCalculationRequest(5, 5, emptyList()), new OccupancyCalculationResponse(0, 0, BigDecimal.ZERO, BigDecimal.ZERO)),
				Arguments.of(new OccupancyCalculationRequest(0, 0, emptyList()), new OccupancyCalculationResponse(0, 0, BigDecimal.ZERO, BigDecimal.ZERO)),
				Arguments.of(new OccupancyCalculationRequest(0, 0, CUSTOMER_RATES), new OccupancyCalculationResponse(0, 0, BigDecimal.ZERO, BigDecimal.ZERO)),
				Arguments.of(new OccupancyCalculationRequest(2, 2, ONLY_PREMIUM_RATES), new OccupancyCalculationResponse(0, 2, BigDecimal.ZERO, new BigDecimal(203))),
				Arguments.of(new OccupancyCalculationRequest(0, 2, ONLY_ECONOMY_RATES), new OccupancyCalculationResponse(0, 2, BigDecimal.ZERO, new BigDecimal(103))),
				Arguments.of(new OccupancyCalculationRequest(2, 0, ONLY_ECONOMY_RATES), new OccupancyCalculationResponse(2, 0, new BigDecimal(103), BigDecimal.ZERO))
		);
	}

}
