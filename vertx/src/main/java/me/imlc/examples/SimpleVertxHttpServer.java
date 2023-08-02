package me.imlc.examples;

import io.vertx.core.*;
import io.vertx.ext.web.Router;

public class SimpleVertxHttpServer {
    public static final int PORT = 8888;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.get("/hello")
                .handler(ctx -> ctx.response().end("OK"));
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(PORT);
        System.out.printf("HTTP server started on port %d%n", PORT);
    }

}
