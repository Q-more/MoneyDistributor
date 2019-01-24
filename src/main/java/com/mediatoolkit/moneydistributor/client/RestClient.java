package com.mediatoolkit.moneydistributor.client;

import com.mediatoolkit.moneydistributor.api.model.BalanceGroupResponse;
import com.mediatoolkit.moneydistributor.api.model.BalanceResponse;
import com.mediatoolkit.moneydistributor.api.model.TransactionRequest;
import com.mediatoolkit.moneydistributor.api.model.UserDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestClient {
    private final RestTemplate template;
    private final String host;

    public RestClient(RestTemplate template, String host) {
        this.template = template;
        this.host = host;
    }

    public Long saveUser(UserDto userDto) {
        return template.postForObject(getUrl("/users"), userDto, Long.class);
    }

    public UserDto getUser(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        return template.getForObject(getUrl("/users/{id}"), UserDto.class, params);
    }

    public List<UserDto> getAllUsers() {
        return template.exchange(
            getUrl("/users"),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<UserDto>>() {
            })
            .getBody();
    }

    public void saveTransaction(TransactionRequest request) {
        template.postForObject(getUrl("/transactions"), request, ResponseEntity.class);
    }

    public BalanceResponse getBalancesAsBorrower(String borrowerId) {
        Map<String, String> params = new HashMap<>();
        params.put("borrowerId", borrowerId);

        return template.getForObject(getUrl("/balances/borrower/{borrowerId}"), BalanceResponse.class, params);
    }

    public BalanceResponse getBalancesAsLander(String lenderId) {
        Map<String, String> params = new HashMap<>();
        params.put("lenderId", lenderId);

        return template.getForObject(getUrl("/balances/lender/{lenderId}"), BalanceResponse.class, params);
    }

    public BalanceGroupResponse getGroupBalance(List<String> users) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getUrl("/balances/group"))
            .queryParam("users", users.toArray(new String[0]));

        return template.getForObject(builder.toUriString(), BalanceGroupResponse.class);
    }

    private String getUrl(String path) {
        return host + path;
    }
}
