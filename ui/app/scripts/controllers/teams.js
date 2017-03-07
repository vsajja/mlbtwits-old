'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:TeamsCtrl
 * @description
 * # TeamsCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('TeamsCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var teams = Restangular.all('teams');

    // This will query /teams and return a promise.
    teams.getList().then(function (teams) {
      $scope.teamList = teams;
      $scope.teamCollection = [].concat($scope.teamList);
    });
  }]);
