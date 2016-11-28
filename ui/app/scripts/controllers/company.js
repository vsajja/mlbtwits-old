'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:CompanyProfileCtrl
 * @description
 * # CompanyProfileCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('CompanyProfileCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var companyId = $routeParams.companyId;
    var company = Restangular.one('companies', companyId);

    // This will query /companies/:companyId and return a promise.
    company.customGET().then(function (company) {
      $scope.company = company;
    });
  }]);
