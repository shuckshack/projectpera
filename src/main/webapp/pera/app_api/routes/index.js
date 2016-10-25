var express = require('express');
var router = express.Router();

var ctrlProfiles = require('../controllers/profiles');
var ctrlAuth = require('../controllers/authentication');

router.get('/profile/:cardNumber', ctrlProfiles.profilesReadOne);
router.put('/profile/:cardNumber', ctrlProfiles.profilesUpdateOne);

router.post('/login', ctrlAuth.login);

module.exports = router;
