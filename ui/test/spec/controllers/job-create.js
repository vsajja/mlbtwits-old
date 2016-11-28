'use strict';

describe('Controller: JobCreateCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var JobCreateCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    JobCreateCtrl = $controller('JobCreateCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(JobCreateCtrl.awesomeThings.length).toBe(3);
  });
});
