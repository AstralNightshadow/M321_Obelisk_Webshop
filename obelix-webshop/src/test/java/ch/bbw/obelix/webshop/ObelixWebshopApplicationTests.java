package ch.bbw.obelix.webshop;

import java.util.List;
import java.util.UUID;

import ch.bbw.obelix.common.basket.BasketApi;
import ch.bbw.obelix.common.basket.BasketDto;
import ch.bbw.obelix.quarry.api.DecorativenessDto;
import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.quarry.api.QuarryApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@AutoConfigureWebTestClient
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ObelixWebshopApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private QuarryApi quarryApi;

	@MockitoBean
	private BasketApi basketApi;

	@BeforeEach
	void configureRemoteServices() {
		var menhirId = UUID.randomUUID();
		var menhir = new MenhirDto(menhirId, 1.0, "Roman marble", DecorativenessDto.PLAIN, "");
		when(quarryApi.getAllMenhirs()).thenReturn(List.of(menhir));
		when(quarryApi.getMenhirById(any(UUID.class))).thenReturn(menhir);
		when(basketApi.offer(any())).thenReturn(BasketDto.empty());
		when(basketApi.isGoodOffer(anyInt())).thenReturn(false, true, false);
	}

	@Test
	void buyMenhir() {
		var anyId = webTestClient.get()
			.uri("/api/menhirs")
			.exchange()
			.expectBodyList(MenhirDto.class)
			.returnResult()
			.getResponseBody()
			.getFirst()
			.id();
		webTestClient.post().uri("/api/basket/buy/{id}", anyId).exchange().expectStatus().isBadRequest();
		webTestClient.put().uri("/api/basket/offer").bodyValue(new BasketDto.BasketItem("boar", 2)).exchange().expectStatus().isOk();
		webTestClient.post().uri("/api/basket/buy/{id}", anyId).exchange().expectStatus().isOk();
		webTestClient.post().uri("/api/basket/buy/{id}", anyId).exchange().expectStatus().isBadRequest();
	}
}
