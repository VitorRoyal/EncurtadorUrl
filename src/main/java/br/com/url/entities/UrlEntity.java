package br.com.url.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "urls")
public class UrlEntity {

    @Id
    private String id;

    private String urlOriginal;

    @Indexed(expireAfterSeconds = 0)
    private LocalDateTime expiracao;

    public UrlEntity() {
    }

    public UrlEntity(String id, String urlOriginal, LocalDateTime expiracao) {
        this.id = id;
        this.urlOriginal = urlOriginal;
        this.expiracao = expiracao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlOriginal() {
        return urlOriginal;
    }

    public void setUrlOriginal(String urlOriginal) {
        this.urlOriginal = urlOriginal;
    }

    public LocalDateTime getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(LocalDateTime expiracao) {
        this.expiracao = expiracao;
    }
}
