package ir.navaco.core.lra.coordinator.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class HttpUtils {

    private HttpUtils() {
    }

    public static <T> HttpEntity<T> createHeader(T body, HttpMethod httpMethodType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Stream.of(MediaType.APPLICATION_JSON).collect(Collectors.toList()));
        headers.setAllow(new HashSet<HttpMethod>() {
            {
                add(httpMethodType);
            }
        });

        return Objects.isNull(body) ? new HttpEntity<>(headers) : new HttpEntity<>(body, headers);
    }

}
