package me.imlc.examples;

import io.vertx.core.Vertx;

public class SimpleWebSocketServer {
    public static final int PORT = 8888;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.createHttpServer()
                .webSocketHandler(event -> event.writeTextMessage("""
                        { "timestamp": "%d" }""".formatted(System.currentTimeMillis())))
                .listen(PORT);
        System.out.printf("HTTP server started on port %d%n", PORT);
    }

}
