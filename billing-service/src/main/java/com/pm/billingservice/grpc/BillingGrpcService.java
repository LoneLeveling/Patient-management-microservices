package com.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
                                     StreamObserver<billing.BillingResponse> responseObserver) {

        log.info("createBillingAccount request received {}",billingRequest.toString());

        //Business logic-e.g save to database, perform calculates etc


        BillingResponse response= BillingResponse.newBuilder().
                setAccountId("12345").
                setStatus("ACTIVE").
                build();

        responseObserver.onNext(response); //This line is used to send response from our GRPC service i.e., Billing service back to the client which in this case is going to be patient service.
        responseObserver.onCompleted();//And this says that the response is completed and we are ready to end the cycle in this response.
    }
}
