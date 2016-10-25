var passport = require('passport');
var AtlassianCrowdStrategy = require('passport-atlassian-crowd').Strategy;

var mongoose = require('mongoose');
var User = mongoose.model('User');

passport.use(new AtlassianCrowdStrategy({
    crowdServer : "http://vl29.champ.aero:9095/crowd/",
    crowdApplication : "pera",
    crowdApplicationPassword : "pera",
    retrieveGroupMemberships : false
  },
  function(userprofile, done) {
    User.findOne({ username: userprofile.username }, function (err, user) {
      if (err) { return done(err); }
      if (!user) {
        return done(null, false, {
          message: 'User not allowed. Contact the Project Pera team.'
        });
      }
      return done(null, user);
    });
  }
));