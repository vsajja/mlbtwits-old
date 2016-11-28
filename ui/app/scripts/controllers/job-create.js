'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:JobCreateCtrl
 * @description
 * # JobCreateCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('JobCreateCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.job = {};
    $scope.alerts = [];

    var companyId = $routeParams.companyId;

    $scope.postJob = function () {

      console.log($scope.job);

      var company = Restangular.one('companies', companyId);

      company.post('jobs', $scope.job).then(function (addedJob) {
        console.log(addedJob);
        $scope.alerts.push({type: 'success', msg: 'Success! Job posted! Title: ' + addedJob.title});
      }, function () {
        $scope.alerts.push({type: 'danger', msg: 'Error! Unable to post job.'});
      });
    };

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };
  }]);
