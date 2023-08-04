package me.imlc.examples;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.httpproxy.HttpProxy;
import io.vertx.httpproxy.ProxyOptions;

public class MixServer {

    public static final int PORT = 8888;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        var proxyClient = vertx.createHttpClient();
        var proxyOptions = new ProxyOptions();
        proxyOptions.setSupportWebSocket(true);
        var proxy = HttpProxy.reverseProxy(proxyOptions, proxyClient);
//        proxy.origin(8889, "localhost");
        proxy.origin(80, "glowingrelaxedlushlove.neverssl.com");


        Router router = Router.router(vertx);
        router.get("/api/*").handler(ctx -> ctx.response().end("OK"));
        router.get("/static/*").handler(StaticHandler.create("."));
        router.get("/proxy/*").handler(ctx -> {
            // caution! forward from http://localhost:8888/proxy/** to http://<dst-host>:<dst-port>/proxy/**
            // the uri does not change
            proxy.handle(ctx.request());
        });
        vertx.createHttpServer()
                .requestHandler(router)
                .webSocketHandler(ws -> {
                    if(ws.path().equals("/ws")) {
                        ws.writeTextMessage("Hello world!");
                    } else {
                        ws.reject();
                    }
                })
                .listen(PORT);

        System.out.printf("HTTP server started on port %d%n", PORT);
    }

}
