(function () {
  var app = angular.module('peraApp', ['ngRoute', 'xeditable']);
  
  app.run(function(editableOptions, editableThemes) {
    editableOptions.theme = 'bs3';
  });
  
  function config ($routeProvider, $locationProvider) {
    $routeProvider
      .when('/', {
        templateUrl: '/login/login.view.html',
        controller: 'loginCtrl',
        controllerAs: 'vm'
      })
      .when('/profile/:cardNumber', {
        templateUrl: '/profile/profile.view.html',
        controller: 'profileCtrl',
        controllerAs: 'vm'
      })
      .otherwise({redirectTo: '/'});
    
    $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    });
  }
  
  angular
    .module('peraApp')
    .config(['$routeProvider', '$locationProvider', config]);
})();