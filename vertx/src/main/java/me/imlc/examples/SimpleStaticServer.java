package me.imlc.examples;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class SimpleStaticServer {

    public static final int PORT = 8888;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.get("/*").handler(StaticHandler.create("."));
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(PORT);
        System.out.printf("HTTP server started on port %d%n", PORT);
    }


}
