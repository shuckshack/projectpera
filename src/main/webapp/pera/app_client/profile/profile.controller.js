
(function(){
  angular
    .module('peraApp')
    .controller('profileCtrl', profileCtrl);
  
  profileCtrl.$inject = ['$scope', '$routeParams', 'staffData'];
  function profileCtrl ($scope, $routeParams, staffData) {
    var vm = this;
    vm.cardNumber = $routeParams.cardNumber;
    vm.pageHeader = {
      title: 'User Profile',
    };
    vm.message = "Loading your user profile...";
    
    staffData
      .getStaffByCardNumber(vm.cardNumber)
      .success(function(data) {
        vm.message = data.cardNumber != "" ? "" : "User Profile not found.";
        vm.data = { staff: data };
        if (!vm.data.staff.employeeName) {
          vm.data.staff.employeeName = vm.data.staff.firstName + ' ' + vm.data.staff.lastName;
        }
      })
      .error(function (e) {
        vm.message = "Sorry, something's gone wrong, please try again later.";
      });
    
    $scope.updateProfile = function() {
      staffData
        .updateStaffByCardNumber(vm.cardNumber, {
          employeeName : vm.data.staff.employeeName,
          projectName : vm.data.staff.projectName,
          department : vm.data.staff.department,
          position : vm.data.staff.position,
          teamLeadName : vm.data.staff.teamLeadName,
          emailAddress : vm.data.staff.emailAddress
        })
        .success(function (data) {
        })
        .error(function (data) {
          vm.formError = "Your profile has not been updated, please try again."
        });
      
      return false;
    };
  }
})();