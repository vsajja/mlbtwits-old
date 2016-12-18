'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:PlayersCtrl
 * @description
 * # PlayersCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('PlayersCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var players = Restangular.all('players');

    // This will query /players and return a promise.
    players.getList().then(function (players) {
      $scope.playerList = players;
      $scope.playerCollection = [].concat($scope.playerList);
    });
  }]);
