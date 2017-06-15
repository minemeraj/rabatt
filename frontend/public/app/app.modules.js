const app = angular.module('rabattApp', ['ngRoute', 'ngCookies']);
const BACKEND_API = 'http://localhost:8080/rabatt/api/v1';
const COOKIES_KEY = 'current_user';

app.run(function ($rootScope, $location, AuthService) {
  $rootScope.logout = function () {
    AuthService.logout();
    $location.path('/');
  };
});

app.factory('FlashService', ['$rootScope', function ($rootScope) {
  function clearFlashMessage() {
    const flash = $rootScope.flash;
    if (flash) {
      if (!flash.keepAfterLocationChange) {
        delete $rootScope.flash;
      } else {
                        // only keep for a single location change
        flash.keepAfterLocationChange = false;
      }
    }
  }

  function initService() {
    $rootScope.$on('$locationChangeStart', function () {
      clearFlashMessage();
    });
  }

  function Success(message, keepAfterLocationChange) {
    $rootScope.flash = {
      message,
      type: 'success',
      keepAfterLocationChange,
    };
  }

  function Error(message, keepAfterLocationChange) {
    $rootScope.flash = {
      message,
      type: 'error',
      keepAfterLocationChange,
    };
  }

  const service = {};
  initService();
  service.Success = Success;
  service.Error = Error;
  return service;
}]);
