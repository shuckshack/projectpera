(function(){
  angular
    .module('peraApp')
    .service('staffData', staffData);
  
  staffData.$inject = ['$http'];
  function staffData ($http) {
    var getStaffByCardNumber = function (cardNumber) {
      return $http.get('/api/profile/' + cardNumber);
    };
    
    var updateStaffByCardNumber = function (cardNumber, data) {
      return $http.put('/api/profile/' + cardNumber, data);
    };
    
    return {
      getStaffByCardNumber : getStaffByCardNumber,
      updateStaffByCardNumber : updateStaffByCardNumber
    };
  };
})();