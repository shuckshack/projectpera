var mongoose = require('mongoose');

var userSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    cardNumber: {
        type: Number,
        required: true
    },
    userType: {
        type: String,
        "default": 'user',
        required: true
    },
});

mongoose.model('User', userSchema);
