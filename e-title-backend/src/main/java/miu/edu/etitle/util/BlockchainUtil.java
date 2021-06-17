package miu.edu.etitle.util;

import lombok.extern.log4j.Log4j2;
import miu.edu.etitle.service.impl.blockchain.dto.FabCarRequestDto;
import miu.edu.etitle.service.impl.blockchain.dto.FabCarResponseDto;
import miu.edu.etitle.service.impl.blockchain.dto.RegisterUserDto;
import miu.edu.etitle.service.impl.blockchain.dto.RegisterUserRequestDto;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class BlockchainUtil {

    @Bean
    public RestTemplate okhttp3Template() {
        RestTemplate restTemplate = new RestTemplate();

        // create the okhttp client builder
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        ConnectionPool okHttpConnectionPool = new ConnectionPool(50, 30, TimeUnit.SECONDS);
        builder.connectionPool(okHttpConnectionPool);
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(false);

        // embed the created okhttp client to a spring rest template
        restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(builder.build()));

        return restTemplate;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://3b9547546da6.ngrok.io").build();
    }

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient webClient;

    public Mono<RegisterUserDto> registerUser(String userName, String orgName) {
        RegisterUserRequestDto dto = new RegisterUserRequestDto();
        dto.setUsername(userName);
        dto.setOrgName(orgName);

        Mono<RegisterUserDto> response = webClient.post()
                .uri("/users")
                .body(Mono.just(dto), RegisterUserRequestDto.class)
                .retrieve()
                .bodyToMono(RegisterUserDto.class);


        return response;
    }

    public Mono<FabCarResponseDto> fabcar(String accessToken, FabCarRequestDto dto) {
        Mono<FabCarResponseDto> response = webClient.post()
                .uri("/channels/mychannel/chaincodes/fabcar")
                .header("Authorization", "Bearer " + accessToken)
                .body(Mono.just(dto), FabCarRequestDto.class)
                .retrieve()
                .bodyToMono(FabCarResponseDto.class);


        return response;
    }

    public static void main(String[] args) {

    }
}
