package com.laposte;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;

import org.scribe.model.*;

import org.scribe.utils.*;

public class LaposteApi extends DefaultApi20
{

	private static final String AUTHORIZE_URL = "{{base_url}}/oauth/v2/authorize?response_type=code&client_id=%s&redirect_uri=%s&scope=openid+email+phone+profile&state=123456";
	private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";

	@Override
	public String getAccessTokenEndpoint() {
		return "{{base_url}}/v2";
	}

	@Override
	public Verb getAccessTokenVerb() {
		return Verb.POST;
	}

	@Override
	public String getAuthorizationUrl(OAuthConfig config) {
		Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Facebook does not support OOB");

		// Append scope if present
		if(config.hasScope())
		{
			return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope()));
		}
		else
		{
			return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
		}
	}

}