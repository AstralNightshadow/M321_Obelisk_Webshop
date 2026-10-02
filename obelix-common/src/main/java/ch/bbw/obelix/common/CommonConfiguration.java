package ch.bbw.obelix.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class CommonConfiguration {

	@Bean
	public CommonsRequestLoggingFilter logFilter() {
		var filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setBeforeMessagePrefix(">>>>> [");
		filter.setAfterMessagePrefix("<<<<< [");
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(10000);
		filter.setIncludeHeaders(false);
		return filter;
	}
}
