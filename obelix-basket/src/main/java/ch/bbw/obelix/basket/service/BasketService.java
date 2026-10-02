package ch.bbw.obelix.basket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jakarta.annotation.PostConstruct;

import ch.bbw.obelix.common.basket.BasketDto;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketService {

	private final ObservationRegistry observationRegistry;
	private BasketDto basket;

	@PostConstruct
	public synchronized void initialize() {
		leave();
	}

	public synchronized BasketDto offer(BasketDto.BasketItem basketItem) {
		var items = new ArrayList<>(basket.items());
		items.add(basketItem);
		basket = basket.withItems(List.copyOf(items));
		return basket;
	}

	public synchronized void leave() {
		basket = BasketDto.empty();
	}

	public synchronized boolean isGoodOffer(int stoneWorth) {
		return Observation.createNotStarted("basket.offer.evaluate", observationRegistry).observe(() -> {
			var basketWorth = basket.items().stream()
				.map(item -> switch (item.name().toLowerCase(Locale.ROOT)) {
					case "boar" -> 5;
					case "honey" -> 2;
					case "magic potion" -> 0;
					default -> 1;
				} * item.count())
				.reduce(0, Integer::sum);
			log.info("basket worth {} vs menhir worth {}", basketWorth, stoneWorth);
			return basketWorth >= stoneWorth;
		});
	}
}