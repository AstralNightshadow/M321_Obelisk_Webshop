package ch.bbw.obelix.common.basket;

import java.util.List;

public record BasketDto(List<BasketItem> items) {
	public BasketDto {
		items = List.copyOf(items);
	}

	public static BasketDto empty() {
		return new BasketDto(List.of());
	}

	public BasketDto withItems(List<BasketItem> items) {
		return new BasketDto(items);
	}

	public record BasketItem(String name, int count) {
		public BasketItem {
			if (name == null || name.isEmpty()) {
				throw new IllegalArgumentException("name is empty");
			}
			if (count < 1) {
				throw new IllegalArgumentException("count must be greater than zero");
			}
		}
	}
}