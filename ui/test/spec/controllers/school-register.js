'use strict';

describe('Controller: SchoolRegisterCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var SchoolRegisterCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    SchoolRegisterCtrl = $controller('SchoolRegisterCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(SchoolRegisterCtrl.awesomeThings.length).toBe(3);
  });
});
