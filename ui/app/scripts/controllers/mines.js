'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:MinesCtrl
 * @description
 * # MinesCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('MinesCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var mines = Restangular.all('mines');

    // This will query /jobs and return a promise.
    mines.getList().then(function (mines) {
      $scope.mineList = mines;
      $scope.mineCollection = [].concat($scope.mineList);
    });
  }]);
