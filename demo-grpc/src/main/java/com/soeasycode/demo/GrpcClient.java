package com.soeasycode.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class GrpcClient {
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private static final String host="127.0.0.1";
    private static final int ip=50051;
    public GrpcClient(String host,int port){
        //usePlaintext表示明文传输，否则需要配置ssl
        //channel  表示通信通道
        channel= ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
        //存根
        blockingStub=GreeterGrpc.newBlockingStub(channel);
    }
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    public void  testResult(String name){
        TestRequest request=TestRequest.newBuilder().setName(name).build();
        TestResponse response=blockingStub.testHelloWorld(request);
        System.out.println(response.getMessage());
    }
    public static void main(String[] args) {
        GrpcClient client=new GrpcClient(host,ip);
        for (int i=0;i<=5;i++){
            client.testResult("<<<<<result>>>>>:"+i);
        }
    }
}
