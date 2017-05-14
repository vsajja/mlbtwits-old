'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:RegisterCtrl
 * @description
 * # RegisterCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('RegisterCtrl', function ($scope, UserService, $location) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.register = function register() {
      $scope.dataLoading = true;
      UserService.Create($scope.user)
        .then(function (response) {
          if (response.success) {
            $scope.alerts.push({type: 'success', msg: 'Registration successful!'});
            $location.path('/login');
          } else {
            $scope.alerts.push({type: 'danger', msg: 'Error! ' + response.message});
            $scope.dataLoading = false;
          }
        });
    };

    $scope.alerts = [];

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };
  });
