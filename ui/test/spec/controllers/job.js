'use strict';

describe('Controller: JobProfileCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var JobProfileCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    JobProfileCtrl = $controller('JobProfileCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(JobProfileCtrl.awesomeThings.length).toBe(3);
  });
});
