(function(){
  angular
    .module('peraApp')
    .controller('profileCtrl', profileCtrl);
  
  profileCtrl.$inject = ['$scope'];
  function profileCtrl ($scope) {
    var vm = this;
    vm.pageHeader = {
      title: 'User Profile',
      strapline: 'User Profile Name'
    };
  }
})();