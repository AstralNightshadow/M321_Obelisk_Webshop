package ch.bbw.obelix.webshop.service;

import java.util.List;
import java.util.UUID;

import ch.bbw.obelix.common.basket.BasketApi;
import ch.bbw.obelix.common.basket.BasketDto;
import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.quarry.api.QuarryApi;
import ch.bbw.obelix.webshop.controller.ObelixWebshopController.UnknownMenhirException;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Note that Obelix is definitely not multitasking-capable.
 */
@Service
@RequiredArgsConstructor
public class ObelixWebshopService {

	private final QuarryApi quarryClient;
	private final BasketApi basketClient;
	private final ObservationRegistry observationRegistry;

	public List<MenhirDto> getAllMenhirs() {
		return quarryClient.getAllMenhirs();
	}

	public MenhirDto getMenhirById(UUID menhirId) {
		try {
			return quarryClient.getMenhirById(menhirId);
		} catch (WebClientResponseException exception) {
			if (exception.getStatusCode() == HttpStatus.BAD_REQUEST) {
				throw new UnknownMenhirException("unknown menhir with id " + menhirId);
			}
			throw exception;
		}
	}

	public void deleteById(UUID menhirId) {
		quarryClient.deleteById(menhirId);
	}

	public BasketDto offer(BasketDto.BasketItem basketItem) {
		return basketClient.offer(basketItem);
	}

	public void leave() {
		basketClient.leave();
	}

	public void exchange(UUID menhirId) {
		Observation.createNotStarted("webshop.menhir.exchange", observationRegistry).observe(() -> {
			var menhir = getMenhirById(menhirId);
			if (!basketClient.isGoodOffer(menhir.decorativeness().ordinal())) {
				throw new BadOfferException("Bad Offer: That won't even feed Idefix!");
			}
			quarryClient.deleteById(menhirId);
			basketClient.leave();
		});
	}

	@StandardException
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public static class BadOfferException extends RuntimeException {}
}
