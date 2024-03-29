package com.github.clui951.grpc.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.logging.Logger;

public class HelloWorldServer {
    private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

    private int port = 42420;
    private Server server;

    private void start() throws Exception {
        logger.info("Starting the grpc server.");

        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start();

        logger.info("Server started. Listening on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** JVM is shutting down. Turning off grpc server as well ***");
            HelloWorldServer.this.stop();
            System.err.println("*** shutdown complete ***");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        logger.info("Server startup. Args = " + Arrays.toString(args));
        final HelloWorldServer helloWorldServer = new HelloWorldServer();

        helloWorldServer.start();
        helloWorldServer.blockUntilShutdown();

    }

    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            logger.info("sayHello endpoint hit.");
            HelloResponse response = HelloResponse.newBuilder().setMessage("Hello " + request.getName()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
