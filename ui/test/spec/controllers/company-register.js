'use strict';

describe('Controller: CompanyRegisterCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var CompanyRegisterCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CompanyRegisterCtrl = $controller('CompanyRegisterCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(CompanyRegisterCtrl.awesomeThings.length).toBe(3);
  });
});
