'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:SchoolProfileCtrl
 * @description
 * # SchoolProfileCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('SchoolProfileCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var schoolId = $routeParams.schoolId;
    var school = Restangular.one('schools', schoolId);

    // This will query /schools/:schoolId and return a promise.
    school.customGET().then(function (school) {
      $scope.school = school;
    });
  }]);
