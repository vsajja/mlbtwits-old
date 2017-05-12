'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('LoginCtrl', function ($location, AuthenticationService) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var vm = this;

    // reset login status
    AuthenticationService.ClearCredentials();

    function login() {
      vm.dataLoading = true;
      AuthenticationService.Login(vm.username, vm.password, function (response) {
        if (response.success) {
          AuthenticationService.SetCredentials(vm.username, vm.password);
          $location.path('/');
        } else {
          // FlashService.Error(response.message);
          vm.dataLoading = false;
        }
      });
    }

    vm.login = login;
  });
