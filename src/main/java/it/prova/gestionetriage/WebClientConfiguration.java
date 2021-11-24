package it.prova.gestionetriage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfiguration {
	private static final String BASE_URL = "http://localhost:8081/dottore";

	@Bean
	public WebClient getWebClient() {
		HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
				.responseTimeout(Duration.ofMillis(10000))
				.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
						.addHandlerLast(new WriteTimeoutHandler(10000, TimeUnit.MILLISECONDS)));

		WebClient client = WebClient.builder().baseUrl(BASE_URL)
				.clientConnector(new ReactorClientHttpConnector(httpClient)).build();
		return client;
	}
}
