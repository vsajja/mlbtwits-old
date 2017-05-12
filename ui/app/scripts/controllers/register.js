'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:RegisterCtrl
 * @description
 * # RegisterCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('RegisterCtrl', function (UserService, $location) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var vm = this;

    function register() {
      vm.dataLoading = true;
      UserService.Create(vm.user)
        .then(function (response) {
          if (response.success) {
            // FlashService.Success('Registration successful', true);
            $location.path('/login');
          } else {
            // FlashService.Error(response.message);
            vm.dataLoading = false;
          }
        });
    }

    vm.register = register;
  });
