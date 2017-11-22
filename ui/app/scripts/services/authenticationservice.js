'use strict';

/**
 * @ngdoc service
 * @name mlbTwitsApp.AuthenticationService
 * @description
 * # AuthenticationService
 * Factory in the mlbTwitsApp.
 */
angular.module('mlbTwitsApp')
  .factory('AuthenticationService', function ($http, $cookies, $base64, $rootScope) {
    // Service logic
    // ...

    function SetCredentials(username, password, userId) {

      var authdata = $base64.encode(username + ':' + password);
      $rootScope.globals = {
        currentUser: {
          username: username,
          userId: userId,
          authdata: authdata
        }
      };

      // set default auth header for http requests
      $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
      // Restangular.setDefaultHeaders('Authorization', 'Basic ' + $rootScope.globals.currentUser.authdata);

      // store user details in globals cookie that keeps user logged in for 1 week (or until they logout)
      var cookieExp = new Date();
      cookieExp.setDate(cookieExp.getDate() + 7);
      $cookies.putObject('globals', $rootScope.globals, { expires: cookieExp });
    }

    function ClearCredentials() {
      $rootScope.globals = {};
      $cookies.remove('globals');
      // $http.defaults.headers.common.Authorization = 'Basic';
      // RestangularProvider.setDefaultHeaders({'Authorization': 'Basic'});
    }

    var service = {};

    service.SetCredentials = SetCredentials;
    service.ClearCredentials = ClearCredentials;

    return service;
  });