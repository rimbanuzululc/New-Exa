package io.starlight.test.http;

import io.starlight.StarlightVerticle;
import io.starlight.http.EnableWebServer;

/**
 *
 * @author denny
 */
@EnableWebServer(port = "${port}")
public class TestHTTPVerticle extends StarlightVerticle {

    @Override
    public void start() throws Exception {
        
        System.out.println("START");
    }
}
