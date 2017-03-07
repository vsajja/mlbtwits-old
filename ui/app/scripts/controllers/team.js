'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:TeamCtrl
 * @description
 * # TeamCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('TeamCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var teamId = $routeParams.teamId;
    var team = Restangular.one('teams', teamId);

    team.customGET().then(function (team) {
      $scope.team = team;
    });

  }]);
