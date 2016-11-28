'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:StudentRegisterCtrl
 * @description
 * # StudentRegisterCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('StudentRegisterCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.student = {};
    $scope.alerts = [];

    $scope.registerStudent = function () {
      var students = Restangular.all('students');

      students.post($scope.student).then(function (addedStudent) {
        // console.log(addedStudent);
        $scope.alerts.push({type: 'success', msg: 'Success! Added student! Email: ' + addedStudent.emailAddress});
      }, function () {
        $scope.alerts.push({type: 'danger', msg: 'Error! Unable to add student.'});
      });
    };

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };
  }]);
