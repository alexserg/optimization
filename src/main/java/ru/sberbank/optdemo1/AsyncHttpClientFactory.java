package ru.sberbank.optdemo1;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AsyncHttpClientFactory {

	public static AsyncHttpClient create(AsyncHttpClientConfig config) {
		DefaultAsyncHttpClientConfig.Builder builder = new DefaultAsyncHttpClientConfig.Builder();
		if (config.connectTimeout > 0) {
			builder = builder.setConnectTimeout(config.connectTimeout);
		}
		if (config.connectionRequestTimeout > 0) {
			builder = builder.setRequestTimeout(config.connectionRequestTimeout);
		}
		if (config.socketTimeout > 0) {
			builder = builder.setReadTimeout(config.socketTimeout);
		}
		if (config.connectionTtl > 0) {
			builder = builder.setConnectionTtl(config.connectionTtl);
		}
		if (config.maxConnections > 0) {
			builder = builder.setMaxConnections(config.maxConnections);
		}
		if (config.maxConnectionsPerRoute > 0) {
			builder = builder.setMaxConnectionsPerHost(config.maxConnectionsPerRoute);
		}
		if (config.retry >= 0) {
			builder = builder.setMaxRequestRetry(config.retry);
		}
		builder = builder.setThreadFactory(new DefaultThreadFactory(config.getName() + "-async-http", true));
		if (config.nettyTimer != null) {
			builder = builder.setNettyTimer(config.nettyTimer);
		} else {
			builder = builder.setThreadPoolName(config.getName() + "-async");
		}
		return new DefaultAsyncHttpClient(builder.build());
	}


	public static class AsyncHttpClientConfig {
		int connectTimeout = 0;
		int connectionRequestTimeout = 0;
		int socketTimeout = 0;
		int maxConnections = 0;
		int maxConnectionsPerRoute = 0;
		int connectionTtl = (int) Duration.ofMinutes(1).toMillis();
		int retry = -1;
		String name;
		HashedWheelTimerConfig timerConfig;
		Timer nettyTimer;

		@PostConstruct
		public void init() {
			if (timerConfig != null && nettyTimer == null) {
				HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(new DefaultThreadFactory(getName() + "-async-timer", true), timerConfig.getTickDurationInMillis(), TimeUnit.MILLISECONDS, timerConfig.getTicksPerWheel());
				nettyTimer = hashedWheelTimer;
				hashedWheelTimer.start();
			}
		}

		@PreDestroy
		public void destroy() {
			if (nettyTimer != null) {
				nettyTimer.stop();
			}
		}

		public int getConnectTimeout() {
			return connectTimeout;
		}

		public AsyncHttpClientConfig setConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
			return this;
		}

		public int getConnectionRequestTimeout() {
			return connectionRequestTimeout;
		}

		public AsyncHttpClientConfig setConnectionRequestTimeout(int connectionRequestTimeout) {
			this.connectionRequestTimeout = connectionRequestTimeout;
			return this;
		}

		public int getSocketTimeout() {
			return socketTimeout;
		}

		public AsyncHttpClientConfig setSocketTimeout(int socketTimeout) {
			this.socketTimeout = socketTimeout;
			return this;
		}

		public int getMaxConnections() {
			return maxConnections;
		}

		public AsyncHttpClientConfig setMaxConnections(int maxConnections) {
			this.maxConnections = maxConnections;
			return this;
		}

		public int getMaxConnectionsPerRoute() {
			return maxConnectionsPerRoute;
		}

		public AsyncHttpClientConfig setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
			this.maxConnectionsPerRoute = maxConnectionsPerRoute;
			return this;
		}

		public int getConnectionTtl() {
			return connectionTtl;
		}

		public AsyncHttpClientConfig setConnectionTtl(int connectionTtl) {
			this.connectionTtl = connectionTtl;
			return this;
		}

		public int getRetry() {
			return retry;
		}

		public AsyncHttpClientConfig setRetry(int retry) {
			this.retry = retry;
			return this;
		}

		public String getName() {
			return name;
		}

		public AsyncHttpClientConfig setName(String name) {
			this.name = name;
			return this;
		}

		public HashedWheelTimerConfig getTimerConfig() {
			return timerConfig;
		}

		public AsyncHttpClientConfig setTimerConfig(HashedWheelTimerConfig timerConfig) {
			this.timerConfig = timerConfig;
			return this;
		}

		public Timer getNettyTimer() {
			return nettyTimer;
		}

		public AsyncHttpClientConfig setNettyTimer(Timer nettyTimer) {
			this.nettyTimer = nettyTimer;
			return this;
		}
	}

	public static class HashedWheelTimerConfig {
		long tickDurationInMillis = 20;
		int ticksPerWheel = 4096;

		public long getTickDurationInMillis() {
			return tickDurationInMillis;
		}

		public HashedWheelTimerConfig setTickDurationInMillis(long tickDurationInMillis) {
			this.tickDurationInMillis = tickDurationInMillis;
			return this;
		}

		public int getTicksPerWheel() {
			return ticksPerWheel;
		}

		public HashedWheelTimerConfig setTicksPerWheel(int ticksPerWheel) {
			this.ticksPerWheel = ticksPerWheel;
			return this;
		}
	}
}
