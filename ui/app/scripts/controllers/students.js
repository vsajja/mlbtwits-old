'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:StudentsCtrl
 * @description
 * # StudentsCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('StudentsCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var students = Restangular.all('students');

    // This will query /schools and return a promise.
    students.getList().then(function (students) {
      $scope.rowList = students;
      $scope.rowCollection = [].concat($scope.rowList);
    });
  }]);
