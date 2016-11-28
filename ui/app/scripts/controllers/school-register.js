'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:SchoolRegisterCtrl
 * @description
 * # SchoolRegisterCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('SchoolRegisterCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.school = {};
    $scope.alerts = [];

    $scope.registerSchool = function () {
      var schools = Restangular.all('schools');

      schools.post($scope.school).then(function (addedSchool) {
        $scope.alerts.push({type: 'success', msg: 'Success! Added school! Name: ' + addedSchool.name});
      }, function () {
        $scope.alerts.push({type: 'danger', msg: 'Error! Unable to add school.'});
      });
    };

    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };
  }]);
