package me.imlc.examples;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.httpproxy.HttpProxy;
import io.vertx.httpproxy.ProxyOptions;

/**
 * Requires additional dependencies:
 *
 * <dependency>
 *       <groupId>io.vertx</groupId>
 *       <artifactId>vertx-http-proxy</artifactId>
 *     </dependency>
 *     <dependency>
 *       <groupId>io.vertx</groupId>
 *       <artifactId>vertx-web-client</artifactId>
 * </dependency>
 */
public class SimpleReverseProxyServer {
    public static final int PORT = 8888;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        var proxyClient = vertx.createHttpClient();
        var proxyOptions = new ProxyOptions();
        proxyOptions.setSupportWebSocket(true);
        var proxy = HttpProxy.reverseProxy(proxyOptions, proxyClient);
        proxy.origin(443, "www.example.org");

        vertx.createHttpServer()
                .webSocketHandler(event -> event.writeTextMessage("""
                        { "timestamp": "%d" }""".formatted(System.currentTimeMillis())))
                .requestHandler(proxy)
                .listen(PORT);
        System.out.printf("HTTP server started on port %d%n", PORT);
    }

}
