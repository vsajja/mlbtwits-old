'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('LoginCtrl', function ($scope, $location, AuthenticationService, $http, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    // reset login status
    AuthenticationService.ClearCredentials();

    $scope.alerts = [];

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };

    $scope.login = function login() {
      Restangular.one('login').customPOST($scope.user)
        .then(function () {
          AuthenticationService.SetCredentials($scope.user.username, $scope.user.password);
          $location.path('/');
        }, function (error) {
          if (error.status === 404) {
            $scope.alerts.push({type: 'danger', msg: 'Error! Unable to login, user does not exist!'});
          }
          else if (error.status === 401) {
            $scope.alerts.push({type: 'danger', msg: 'Error! Invalid username or password'});
          }
          else {
            $scope.alerts.push({type: 'danger', msg: 'Error! Unable to login due to unknown reason!'});
          }
        });
    };

    // TODO: Move register logic out to UserService
    $scope.register = function () {
      Restangular.one('register').customPOST($scope.newUser).then(function (registeredUser) {
        $scope.alerts.push({type: 'success', msg: 'Success! Registered user! Name: ' + registeredUser.username});
        $location.path('/login');
      }, function (error) {
        // conflict
        if (error.status === 409) {
          $scope.alerts.push({type: 'danger', msg: 'Error! Username ' + $scope.newUser.username + ' already exists!'});
        }
        else {
          $scope.alerts.push({type: 'danger', msg: 'Error! Unable to register user.'});
        }
      });
    };
  });
