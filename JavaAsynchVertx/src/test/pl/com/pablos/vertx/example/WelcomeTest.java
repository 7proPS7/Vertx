package pl.com.pablos.vertx.example;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class WelcomeTest {

	private Vertx vertx;
	private Integer port;

	@Before
	public void setUp(TestContext context) throws IOException {
		vertx = Vertx.vertx();

		ServerSocket socket = new ServerSocket(0);
		port = socket.getLocalPort();
		socket.close();

		// Use Deployment option to set port
		DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", port));

		vertx.deployVerticle(Welcome.class.getName(), options, context.asyncAssertSuccess());
	}

	@After
	public void tearDown(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}

	@Test
	public void testMyApp(TestContext context) { 
		final Async async = context.async();

		vertx.createHttpClient().getNow(port, "localhost", "/", response -> {
			response.handler(body -> {
				context.assertTrue(body.toString().contains("Hello"));
				async.complete();
			});
		});
	}

}