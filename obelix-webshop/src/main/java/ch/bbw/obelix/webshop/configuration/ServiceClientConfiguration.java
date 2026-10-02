package ch.bbw.obelix.webshop.configuration;

import ch.bbw.obelix.common.basket.BasketApi;
import ch.bbw.obelix.quarry.api.QuarryApi;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableConfigurationProperties(ClientProperties.class)
public class ServiceClientConfiguration {

	@Bean
	WebClient.Builder webClientBuilder(ObservationRegistry observationRegistry) {
		return WebClient.builder().observationRegistry(observationRegistry);
	}

	@Bean
	QuarryApi quarryClient(ClientProperties properties, WebClient.Builder builder) {
		var webClient = builder.clone().baseUrl(properties.quarryUrl()).build();
		var proxyFactory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
		return proxyFactory.createClient(QuarryApi.class);
	}

	@Bean
	BasketApi basketClient(ClientProperties properties, WebClient.Builder builder) {
		var webClient = builder.clone().baseUrl(properties.basketUrl()).build();
		var proxyFactory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
		return proxyFactory.createClient(BasketApi.class);
	}
}