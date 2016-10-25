var passport = require('passport');

var sendJSONresponse = function(res, status, content) {
  res.status(status);
  res.json(content);
};

module.exports.login = function(req, res) {
  if (!req.body.username || !req.body.password) {
    sendJSONresponse(res, 400, {
      "message": "All fields required"
    });
    return;
  }
  
  passport.authenticate('atlassian-crowd', function(err, user, info) {
    var token;
    
    if (err) {
      sendJSONresponse(res, 404, err);
      return;
    }
    
    if (user) {
/*
      token = user.generateJwt();
      sendJSONresponse(res, 200, {
        "token" : token
      });
*/
      sendJSONresponse(res, 200, user);
    } else {
      sendJSONresponse(res, 401, info);
    }
  })(req, res);
};