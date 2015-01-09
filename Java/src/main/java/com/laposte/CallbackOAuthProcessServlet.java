package com.laposte;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;


public class CallbackOAuthProcessServlet extends HttpServlet {

    private Log log = LogFactory.getLog(getClass());
    private static final String PROTECTED_RESOURCE_URL = "{{base_url}}/v2/me";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

		String code = req.getParameter(OAuthConstants.VERIFIER);

         OAuthService service = new ServiceBuilder()
                    .provider(LaposteApi.class)
                    .apiKey("_API_KEY_")
                    .apiSecret("_API_SECRET_")
                    .callback("http://127.0.0.1/callback")
                    .build();

	    Token requestToken = service.getRequestToken();

	    Verifier verifier = new Verifier(code);

	    Token accessToken = service.getAccessToken(requestToken, verifier);

        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);

        Response response = request.send();

        resp.getWriter().println(response.getCode());
        resp.getWriter().println(response.getBody());

    }

}