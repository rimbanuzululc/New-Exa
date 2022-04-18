package io.starlight.test.config.client;

import io.starlight.AutoWired;
import io.starlight.StarlightVerticle;

/**
 *
 * @author denny
 */
public class ConfigClientVerticle extends StarlightVerticle {

    @AutoWired
    protected TestComponent component;
            
    @Override
    public void start() throws Exception {
        
        System.out.println("==>" + component.getConfigString());
        System.out.println("==>" + component.getDataInt());
    }
    
}
