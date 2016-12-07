'use strict';

/**
 * @ngdoc overview
 * @name mlbTwitsApp
 * @description
 * # mlbTwitsApp
 *
 * Main module of the application.
 */
var mlbTwitsApp = angular
  .module('jobmineApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap',
    'smart-table',
    'restangular',
    'xeditable'
  ])
  .config(function ($routeProvider, $compileProvider, RestangularProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .otherwise({
        redirectTo: '/'
      });

    // release
    // $compileProvider.debugInfoEnabled(false);
    // RestangularProvider.setBaseUrl('/api/v1');

    // dev
    RestangularProvider.setBaseUrl('http://localhost:5050/api/v1');
  });
//
mlbTwitsApp.run(function (editableOptions) {
  editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
});
