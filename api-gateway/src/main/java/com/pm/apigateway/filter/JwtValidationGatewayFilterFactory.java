package com.pm.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

//NOTE: Below we have created a Filter class:
//A filter class in Spring boot is a custom class that allows us to intercept HTTP requests, apply
//custom logic and then decide whether or not to continue processing the request or to cancel the request.
//We are ussing a filter to call the Auth service validation end point to check if a token is valid or not and depending on that we can handle the request
//in the appropriate way.
@Component //This tag tells the spring that this class is a Spring bean and we want to manage it in the spring lifecycle
public class JwtValidationGatewayFilterFactory
    extends AbstractGatewayFilterFactory<Object>
    //By extending from the AbstractGatewayFilterFactory and implementing the apply method below we are telling Spring boot and the spring boot cloud dependencies that we want to add this Filter i.e., the Filter class to
    //the request life cycle, so cloud gateway will automatically apply our filter to all the requests coming in, and all we have to do is apply our business logic inside the apply method below, for how we want to process and
    //handle the request.
{

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder
    , @Value("{$auth.service.url}") String authServiceUrl)
    {
        this.webClient=webClientBuilder.baseUrl(authServiceUrl).build();
    }
//Above we are initializing a web client using the AUTH service URL that we get from the env variables.
@Override
public GatewayFilter apply(Object config)
{
return (exchange, chain) -> {
    String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if(token==null|| !token.startsWith("Bearer :"))
    {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    //The reason why we do the if() part above is to be more efficient,
    //as there is no point trying to make an API call to the Auth service validation end point
    //If we know that the token is going to be bad, so there's no point in sending a bad or an empty token if it's in an invalid format.

//So above we validated if token is valid or not now the next step is to actually call the validate end point on the AUTH service to let the AUTH service to do its validation steps:
//Below we are making a GET request using the web client to the URI "/validate"
    return webClient.get()
            .uri("/validate") //Filter calling the '/validate' end point on the Auth service
            .header(HttpHeaders.AUTHORIZATION, token)
            .retrieve()
            .toBodilessEntity()
            .then(chain.filter(exchange));

    //Note: This "chain.filter(exchange))" tells Spring that our filter has finished processing the request and all was successful (i.e., Status code 200) and
    //we are happy for the request to continue down the chain.
    //Sop next thing in that chain could be another filter or it could be you continue the request on to its destination such as the patient service microservice, also we
    //do not need to know explicitly what the next step is going to be, all we have to say is above step is completed and then Spring Boot and Spring API gateway are going to manage
    //the next steps.
};
}
}
