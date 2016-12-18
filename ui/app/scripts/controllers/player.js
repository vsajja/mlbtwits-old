'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:PlayerCtrl
 * @description
 * # PlayerCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('PlayerCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var playerId = $routeParams.playerId;
    var player = Restangular.one('players', playerId);

    // This will query /players/:playerId and return a promise.
    player.customGET().then(function (player) {
      $scope.player = player;
    });
  }]);
