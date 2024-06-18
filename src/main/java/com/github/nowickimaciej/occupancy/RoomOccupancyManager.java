package com.github.nowickimaciej.occupancy;

import java.math.BigDecimal;
import java.util.*;

class RoomOccupancyManager {

	private static final BigDecimal PREMIUM_CUSTOMER_THRESHOLD = new BigDecimal(100);

	private final List<BigDecimal> premiumRoomOccupants = new ArrayList<>();
	private final Deque<BigDecimal> economyRoomOccupants = new ArrayDeque<>();
	private final int economyRoomCount;
	private final int premiumRoomCount;

	RoomOccupancyManager(int economyRoomCount, int premiumRoomCount) {
		this.economyRoomCount = economyRoomCount;
		this.premiumRoomCount = premiumRoomCount;
	}

	static RoomOccupancyManager calculate(int economyRoomCount, int premiumRoomCount, List<BigDecimal> customers) {
		RoomOccupancyManager manager = new RoomOccupancyManager(economyRoomCount, premiumRoomCount);
		manager.addCustomers(customers);
		return manager;
	}

	int getEconomyRoomsUsed() {
		return economyRoomOccupants.size();
	}

	int getPremiumRoomsUsed() {
		return premiumRoomOccupants.size();
	}

	BigDecimal getEconomyRoomsValue() {
		return sum(economyRoomOccupants);
	}

	BigDecimal getPremiumRoomsValue() {
		return sum(premiumRoomOccupants);
	}

	private void addCustomers(List<BigDecimal> customers) {
		customers.stream()
				.sorted(Comparator.<BigDecimal>naturalOrder().reversed())
				.forEach(this::addCustomer);
	}

	private void addCustomer(BigDecimal rate) {
		if (isPremiumCustomer(rate)) {
			addPremiumCustomer(rate);
		} else {
			addEconomyCustomer(rate);
		}
	}

	private void addPremiumCustomer(BigDecimal rate) {
		if (isPremiumRoomFree()) {
			premiumRoomOccupants.add(rate);
		}
	}

	private void addEconomyCustomer(BigDecimal rate) {
		if (isEconomyRoomFree()) {
			economyRoomOccupants.addLast(rate);
			return;
		}

		if (!isPremiumRoomFree()) {
			return;
		}

		if (economyRoomCount == 0) {
			premiumRoomOccupants.add(rate);
		} else {
			BigDecimal mostValuableEconomyCustomer = economyRoomOccupants.removeFirst();
			premiumRoomOccupants.add(mostValuableEconomyCustomer);
			economyRoomOccupants.addLast(rate);
		}
	}

	private boolean isPremiumCustomer(BigDecimal rate) {
		return rate.compareTo(PREMIUM_CUSTOMER_THRESHOLD) >= 0;
	}

	private boolean isPremiumRoomFree() {
		return premiumRoomOccupants.size() < premiumRoomCount;
	}

	private boolean isEconomyRoomFree() {
		return economyRoomOccupants.size() < economyRoomCount;
	}

	private BigDecimal sum(Collection<BigDecimal> rates) {
		return rates.stream()
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}

}
