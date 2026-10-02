package ch.bbw.obelix.webshop.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("obelix.clients")
public record ClientProperties(String quarryUrl, String basketUrl) {}