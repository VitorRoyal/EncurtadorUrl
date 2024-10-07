package br.com.url.controller;

import br.com.url.controller.dto.EncurtadorUrlRequest;
import br.com.url.controller.dto.UrlEncurtadaResponse;
import br.com.url.entities.UrlEntity;
import br.com.url.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class UrlController {

    private final UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostMapping("/encurtador-url")
    public ResponseEntity<UrlEncurtadaResponse> encurtarUrl(@RequestBody EncurtadorUrlRequest request,
                                            HttpServletRequest httpServletRequest) {

        String id;
        do {
            id = RandomStringUtils.randomAlphanumeric(5,10);
        } while (urlRepository.existsById(id));

        urlRepository.save(new UrlEntity(id, request.url(), LocalDateTime.now().plusMinutes(5)));

        var redireciona = httpServletRequest.getRequestURL().toString().replace("encurtador-url", id);

        return ResponseEntity.ok(new UrlEncurtadaResponse(redireciona));
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Void> redirecionaUrl(@PathVariable String id){
        var url = urlRepository.findById(id);

        if(url.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.get().getUrlOriginal()));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

}
