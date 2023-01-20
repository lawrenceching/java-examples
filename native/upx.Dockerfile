FROM aerysinnovation/upx as BUILDER

WORKDIR /build
COPY ./target/native hello

RUN upx --lzma --best hello -o hello.upx

FROM scratch

USER app
COPY --from BUILDER /build/hello.upx /

