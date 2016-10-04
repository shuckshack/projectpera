(function() {
  angular
    .module('peraApp')
    .controller('loginCtrl', loginCtrl);
  
  loginCtrl.$inject = ['$location', 'authentication'];
  function loginCtrl($location, authentication) {
    var vm = this;
    
    vm.credentials = {
      email : "",
      password : ""
    };
    
    vm.onSubmit = function() {
      vm.formError = "";
      if (!vm.credentials.username || !vm.credentials.password) {
        vm.formError = "All fields required, please try again.";
        return false;
      } else {
        authentication
          .login(vm.credentials)
          .error(function(err) {
            vm.formError = err;
          })
          .success(function(user) {
            $location.path('/profile/' + user.cardNumber);
          });
      }
    };
  }
})();