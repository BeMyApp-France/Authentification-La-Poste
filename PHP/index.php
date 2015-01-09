<?php
require 'vendor/autoload.php';
require 'config.php';

$client = new OAuth2\Client(CLIENT_ID, CLIENT_SECRET);
if (!isset($_GET['code']))
{
    $params   = array('state'=>uniqid(), 'scope'=>'openid email phone profile');
    $auth_url = $client->getAuthenticationUrl(AUTHORIZATION_ENDPOINT, REDIRECT_URI, $params);
    header('Location: ' . $auth_url);
}
else
{
    $params = array('code' => $_GET['code'], 'redirect_uri' => REDIRECT_URI);
    $response = $client->getAccessToken(TOKEN_ENDPOINT, 'authorization_code', $params);
    var_dump($response);
    $client->setAccessToken($response['result']['access_token']);
    $fetch_me = $client->fetch( API . '/me' );
    var_dump($fetch_me);
}
