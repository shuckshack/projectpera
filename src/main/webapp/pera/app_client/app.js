(function () {
  angular.module('peraApp', ['ngRoute']);
  
  function config ($routeProvider, $locationProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'profile/profile.view.html',
        controller: 'profileCtrl',
        controllerAs: 'vm'
      })
      .otherwise({redirectTo: '/'});
  }
  
  angular
    .module('peraApp')
    .config(['$routeProvider', '$locationProvider', config]);
})();