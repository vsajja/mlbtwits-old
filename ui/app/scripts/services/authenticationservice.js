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

    function SetCredentials(username, password) {
      console.log(username);
      console.log(password);
      var authdata = $base64.encode(username + ':' + password);
      console.log(authdata);
      $rootScope.globals = {
        currentUser: {
          username: username,
          authdata: authdata
        }
      };

      // set default auth header for http requests
      $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line

      // store user details in globals cookie that keeps user logged in for 1 week (or until they logout)
      var cookieExp = new Date();
      cookieExp.setDate(cookieExp.getDate() + 7);
      $cookies.putObject('globals', $rootScope.globals, { expires: cookieExp });
    }

    function ClearCredentials() {
      $rootScope.globals = {};
      $cookies.remove('globals');
      $http.defaults.headers.common.Authorization = 'Basic';
    }

    var service = {};

    service.SetCredentials = SetCredentials;
    service.ClearCredentials = ClearCredentials;

    return service;
  });
