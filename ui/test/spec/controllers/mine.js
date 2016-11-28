'use strict';

describe('Controller: MineProfileCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var MineProfileCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    MineProfileCtrl = $controller('MineProfileCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(MineProfileCtrl.awesomeThings.length).toBe(3);
  });
});
