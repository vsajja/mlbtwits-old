'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('MainCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var jobmine = Restangular.all('jobmine');

    // This will query /mlbtwits and return a promise.
    jobmine.customGET().then(function (jobmine) {
      $scope.jobmine = jobmine;
    });
  }]);

