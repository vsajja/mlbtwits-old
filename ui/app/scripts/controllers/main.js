'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('MainCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var mlbtwits = Restangular.all('mlbtwits');

    // This will query /mlbtwits and return a promise.
    mlbtwits.customGET().then(function (mlbtwits) {
      $scope.mlbtwits = mlbtwits;
    });
  }]);

