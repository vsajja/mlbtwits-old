'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('LoginCtrl', function ($scope, $location, AuthenticationService) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    // reset login status
    AuthenticationService.ClearCredentials();

    $scope.login = function login() {
      $scope.dataLoading = true;
      AuthenticationService.Login($scope.username, $scope.password, function (response) {
        if (response.success) {
          AuthenticationService.SetCredentials($scope.username, $scope.password);
          $location.path('/');
        } else {
          $scope.alerts.push({type: 'danger', msg: 'Error! Unable to login, check credentials.'});
          $scope.dataLoading = false;
        }
      });
    };

    $scope.alerts = [];

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };
  });
