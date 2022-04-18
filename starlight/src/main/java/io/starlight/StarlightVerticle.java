package io.starlight;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;

/**
 *
 * @author denny
 */
public class StarlightVerticle extends AbstractVerticle {

    @Override
    public void init(Vertx vertx, Context context) {
        
        ComponentManager.initVerticle(this, vertx);
        super.init(vertx, context);
    }

}
