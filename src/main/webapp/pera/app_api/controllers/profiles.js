var mongoose = require('mongoose');
var Staff = mongoose.model('Staff');

var sendJSONResponse = function(res, status, content) {
  res.status(status);
  res.json(content);
}

module.exports.profilesReadOne = function (req, res) {
  if (req.params && req.params.cardNumber) {
    Staff.findOne({ "cardNumber" : req.params.cardNumber })
         .select('cardNumber firstName lastName employeeName projectName department position teamLeadName emailAddress')
         .exec(function(err, staff) {
           if (!staff) {
             sendJSONResponse(res, 404, {
               "message" : "cardNumber not found"
             });
             return;
           }
           sendJSONResponse(res, 200, staff);
         });
  }
  else {
    sendJSONResponse(res, 404, {
      "message" : "No cardNumber in request"
    });
  }
};

module.exports.profilesUpdateOne = function (req, res) {
  if (!req.params.cardNumber) {
    sendJSONResponse(res, 404, {
      "message": "Not found, cardNumber is required"
    });
    return;
  }
    Staff.findOne({ "cardNumber" : req.params.cardNumber })
       .select('employeeName projectName department position teamLeadName emailAddress')
       .exec(
         function(err, staff) {
           if (!staff) {
             sendJSONResponse(res, 404, {
               "message": "cardNumber not found"
           });
           return;
         } 
         else if (err) {
           sendJSONResponse(res, 400, err);
           return;
         }
         staff.employeeName = req.body.employeeName;
         staff.projectName = req.body.projectName;
         staff.department = req.body.department;
         staff.position = req.body.position;
         staff.teamLeadName = req.body.teamLeadName;
         staff.emailAddress = req.body.emailAddress;
         staff.save(function(err, staff) {
           if (err) {
             sendJSONResponse(res, 404, err);
           }
           else {
             sendJSONResponse(res, 200, staff);
           }
         });
       }
  );
};
