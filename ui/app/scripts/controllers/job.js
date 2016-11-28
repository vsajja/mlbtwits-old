'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:JobProfileCtrl
 * @description
 * # JobProfileCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('JobProfileCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var jobId = $routeParams.jobId;
    var job = Restangular.one('jobs', jobId);

    // This will query /jobs/:jobId and return a promise.
    job.customGET().then(function (job) {
      $scope.job = job;
    });
  }]);
