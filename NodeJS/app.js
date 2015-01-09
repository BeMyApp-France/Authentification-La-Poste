var express = require('express'),
    app = express();

var oauth2 = require('simple-oauth2')({
  clientID: "",
  clientSecret: "",
  site: '{{base_url}}',
  authorizationPath: '/oauth/v2/authorize',
  tokenPath: '/oauth/v2'
});

// Authorization uri definition
var authorization_uri = oauth2.authCode.authorizeURL({
  redirect_uri: 'http://localhost:3000/callback',
  scope: 'openid email phone profile',
  state: '3(#0/!~'
});

// Initial page redirecting to Github
app.get('/auth', function (req, res) {
    res.redirect(authorization_uri);
});

// Callback service parsing the authorization token and asking for the access token
app.get('/callback', function (req, res) {
  var code = req.query.code;
  console.log('/callback');
  oauth2.authCode.getToken({
    code: code,
    redirect_uri: 'http://localhost:3000/callback'
  }, saveToken);

  function saveToken(error, result) {
    if (error) { console.log('Access Token Error', error.message); }
    token = oauth2.accessToken.create(result);
    console.log("token : " + token.token.access_token);
    oauth2.api('GET', '/me', {access_token: token.token.access_token}, function (err, data) {
      if (err) { console.log(err);res.send(err);return; }
      console.log(data);
      res.send(data);
    });
  }
});

app.get('/', function (req, res) {
  res.send('<a href="/auth">Identification</a>');
});

app.listen(3000);

console.log('Express server started on port 3000');
