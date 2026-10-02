package ch.bbw.obelix.common.basket;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface BasketApi {

	@PutExchange("/internal/basket/offer")
	BasketDto offer(@RequestBody BasketDto.BasketItem basketItem);

	@DeleteExchange("/internal/basket")
	void leave();

	@PostExchange("/internal/basket/is-good")
	boolean isGoodOffer(@RequestBody int stoneWorth);
}