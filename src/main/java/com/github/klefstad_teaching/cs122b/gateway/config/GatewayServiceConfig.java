package com.github.klefstad_teaching.cs122b.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@ConstructorBinding
@ConfigurationProperties(prefix = "gateway")
public class GatewayServiceConfig
{
    private final URI idm;
    private final URI movies;
    private final URI billing;
    private final URI idmAuthenticate;

    public GatewayServiceConfig(String idm,String movies,String billing, String authenticatePath)
        throws URISyntaxException
    {
        this.idm = new URI(idm);
        this.movies = new URI(movies);
        this.billing = new URI(billing);
        this.idmAuthenticate = new URI(idm + authenticatePath);
    }

    public URI getIdm()
    {
        return idm;
    }

    public URI getMovies()
    {
        return movies;
    }

    public URI getBilling()
    {
        return billing;
    }

    public URI getIdmAuthenticate()
    {
        return idmAuthenticate;
    }
}
