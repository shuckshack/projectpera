(function () {
  angular
    .module('peraApp')
    .service('authentication', authentication);
  
  authentication.$inject = ['$http', '$window'];
  function authentication ($http, $window) {
    login = function(user) {
      return $http.post('/api/login', user).success(function(data) {
      });
    };
    
    return {
      login : login
    };
  }
})();