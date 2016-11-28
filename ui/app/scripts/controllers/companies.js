'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:CompaniesCtrl
 * @description
 * # CompaniesCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('CompaniesCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    var companies = Restangular.all('companies');

    // This will query /companies and return a promise.
    companies.getList().then(function (companies) {
      $scope.companyList = companies;
      $scope.companyCollection = [].concat($scope.companyList);
    });
  }]);
