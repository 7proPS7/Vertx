package pl.com.pablos.vertx.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class Welcome extends AbstractVerticle{
	
	@Override
	public void start(Future<Void> future) throws Exception {

		vertx.createHttpServer().requestHandler(rh -> {
			rh.response().end("<h1>Hi my friends, there is something new!</h1>");
		}).listen(config().getInteger("http.port", 8080), result -> {
			if (result.succeeded()) {
				future.complete();
			} else {
				future.fail(result.cause());
			}
		});
	}

}
