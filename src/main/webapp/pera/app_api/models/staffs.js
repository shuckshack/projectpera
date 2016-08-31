var mongoose = require('mongoose');

var timeInOutSchema = new mongoose.Schema({
    timeIn: {
        type: Date
    },
    timeOut: {
        type: Date
    }
},{ _id : true });

var staffSchema = new mongoose.Schema({
    firstName: {
        type: String,
        required: true
    },
    lastName: {
        type: String,
        required: true
    },
    cardNumber: {
        type: Number,
        required: true
    },
    department: {
        type: String,
        required: true
    },
    project: {
        type: String,
        required: true
    },
    position: {
        type: String,
        required: true
    },
    teamLeadName: {
        type: String,
        required: true
    },
    timeInOutList: [timeInOutSchema]
});

mongoose.model('Staff', staffSchema);
