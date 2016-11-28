'use strict';

/**
 * @ngdoc function
 * @name jobmineApp.controller:CompanyRegisterCtrl
 * @description
 * # CompanyRegisterCtrl
 * Controller of the jobmineApp
 */
angular.module('jobmineApp')
  .controller('CompanyRegisterCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.company = {};
    $scope.alerts = [];

    $scope.registerCompany = function () {
      var companies = Restangular.all('companies');

      companies.post($scope.company).then(function (addedCompany) {
        // console.log(addedCompany);
        $scope.alerts.push({type: 'success', msg: 'Success! Added company! Name: ' + addedCompany.name});
      }, function () {
        $scope.alerts.push({type: 'danger', msg: 'Error! Unable to add company.'});
      });
    };

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };
  }]);
