'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:SchoolsCtrl
 * @description
 * # SchoolsCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('SchoolsCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var schools = Restangular.all('schools');

    // This will query /schools and return a promise.
    schools.getList().then(function (schools) {
      $scope.schoolList = schools;
      $scope.schoolCollection = [].concat($scope.schoolList);
    });
  }]);
