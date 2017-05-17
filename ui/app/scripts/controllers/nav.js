'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:NavCtrl
 * @description
 * # NavCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('NavCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {

    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var mlbtwits = Restangular.all('mlbtwits');

    $scope.getMLBTwits = function getMLBTwits() {
      mlbtwits.customGET().then(function (mlbtwits) {
        $scope.mlbtwits = mlbtwits;
      });
    };

    function initController() {
      $scope.getMLBTwits();
    }

    initController();
  }]);
