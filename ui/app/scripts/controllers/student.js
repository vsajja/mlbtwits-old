'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:StudentProfileCtrl
 * @description
 * # StudentProfileCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('StudentProfileCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var studentId = $routeParams.studentId;
    var student = Restangular.one('students', studentId);

    // This will query /students/:studentId and return a promise.
    student.customGET().then(function (student) {
      $scope.student = student;
    });
  }]);
