'use strict';

describe('Controller: StudentRegisterCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var StudentRegisterCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    StudentRegisterCtrl = $controller('StudentRegisterCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(StudentRegisterCtrl.awesomeThings.length).toBe(3);
  });
});
