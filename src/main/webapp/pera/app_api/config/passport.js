var passport = require('passport');
var AtlassianCrowdStrategy = require('passport-atlassian-crowd').Strategy;

passport.use(new AtlassianCrowdStrategy({
    crowdServer : "http://crowd.champ.aero",
    crowdApplication : "nodejs",
    crowdApplicationPassword : "password",
    retrieveGroupMemberships : false
  },
  function(userprofile, done) {
    console.log('chiem userprofile ' + userprofile);
    return done(null, userprofile);
  }
));