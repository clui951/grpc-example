# Grpc Example

A HelloWorld application using Grpc.


# Commands


```
./gradew build
./gradew idea
``` 

```
./gradew runServer
./gradew runClient
```

```
./gradew installDist
build/install/grpc-example/bin/hello-world-server
build/install/grpc-example/bin/hello-world-client
```

```
grpc_cli ls localhost:42420
grpc_cli ls localhost:42420 helloworld.Greeter -l
grpc_cli ls localhost:42420 helloworld.Greeter.SayHello -l
grpc_cli call localhost:42420 SayHello "name : 'myName'"
```