package ch.bbw.obelix.quarry.configuration;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import ch.bbw.obelix.quarry.repository.MenhirRepository;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@RequiredArgsConstructor
public class QuarryMetricsConfiguration {

	@Bean
	MeterBinder menhirCountGauge(MenhirRepository menhirRepository) {
		return registry -> registry.gauge("obelix.hinkelsteine", menhirRepository, MenhirRepository::count);
	}
}