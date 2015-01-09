package com.laposte;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

import java.io.IOException;
import java.util.Arrays;

public class LaposteServlet extends HttpServlet {

  private static final Token EMPTY_TOKEN = null;

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
         OAuthService service = new ServiceBuilder()
                    .provider(LaposteApi.class)
                    .apiKey("_API_KEY_")
                    .apiSecret("_API_SECRET_")
                    .callback("http://127.0.0.1/callback")
                    .build();

        resp.getWriter().println("=== Laposte's OAuth Workflow ===");
        resp.getWriter().println("Fetching the Authorization URL...");
        String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
        resp.getWriter().println("Got the Authorization URL!");
        resp.getWriter().println("Now go and authorize here:");
        resp.getWriter().println(authorizationUrl);
        resp.sendRedirect(authorizationUrl);

  }
}