'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:JobsCtrl
 * @description
 * # JobsCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('JobsCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var jobs = Restangular.all('jobs');

    // This will query /jobs and return a promise.
    jobs.getList().then(function(jobs) {
      $scope.jobList = jobs;
      $scope.jobCollection = [].concat($scope.jobList);
    });
  }]);
