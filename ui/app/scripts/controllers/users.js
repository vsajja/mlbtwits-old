'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:UsersCtrl
 * @description
 * # UsersCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('UsersCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var users = Restangular.all('users');

    // This will query /users and return a promise.
    users.getList().then(function (users) {
      $scope.userList = users;
      $scope.userCollection = [].concat($scope.userList);
    });
  }]);
