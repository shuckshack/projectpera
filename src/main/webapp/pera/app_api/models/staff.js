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
    },
    lastName: {
        type: String,
    },
    employeeName: {
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
    projectName: {
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
    emailAddress: {
        type: String,
        required: true
    },
    timeInOutList: [timeInOutSchema]
}, { collection: 'staff' });

mongoose.model('Staff', staffSchema);
