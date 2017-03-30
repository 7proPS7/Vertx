package main.pl.com.pablos.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class HelloWeb extends AbstractVerticle {
	//main app

	@Override
	public void start(Future<Void> future) throws Exception {

		
		Router router = Router.router(vertx);
		
		
		router.route("/").handler(routingContext ->{
			HttpServerResponse response =routingContext.response();
			response.putHeader("content-type", "text/html").end("<h2>Web vertx app</h2>");
		});	
		
		
		
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
