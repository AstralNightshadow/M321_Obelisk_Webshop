package ch.bbw.obelix.basket.controller;

import ch.bbw.obelix.basket.service.BasketService;
import ch.bbw.obelix.common.basket.BasketApi;
import ch.bbw.obelix.common.basket.BasketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasketController implements BasketApi {

	private final BasketService basketService;

	@Override
	public BasketDto offer(BasketDto.BasketItem basketItem) {
		return basketService.offer(basketItem);
	}

	@Override
	public void leave() {
		basketService.leave();
	}

	@Override
	public boolean isGoodOffer(int stoneWorth) {
		return basketService.isGoodOffer(stoneWorth);
	}
}