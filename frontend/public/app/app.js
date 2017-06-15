const app = angular.module('rabattApp', ['ngRoute', 'ngCookies']);
const BACKEND_API = 'http://localhost:8080/rabatt/api/v1';
const COOKIES_KEY = 'current_user';

app.run(function ($rootScope, $window, AuthService, $cookieStore) {
  $rootScope.currentUser = $cookieStore.get(COOKIES_KEY);
  $rootScope.loggedIn = !!$rootScope.currentUser;
  $rootScope.logout = function () {
    AuthService.logout();
    $window.location.href = '/#';
    $window.location.reload();
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

app.config(['$routeProvider',
  function ($routeProvider) {
    $routeProvider.when('/', {
      templateUrl: 'app/components/home/homeView.html',
      controller: 'homeController',
    });
    $routeProvider.when('/login', {
      templateUrl: 'app/components/login/loginView.html',
      controller: 'loginController',
    });
    $routeProvider.when('/register', {
      templateUrl: 'app/components/register/registerView.html',
      controller: 'registerController',
    });
    $routeProvider.when('/discount', {
      templateUrl: 'app/components/discount/discountView.html',
      controller: 'discountController',
    });
  },
]);

app.factory('AuthService', ['$http', '$q', '$rootScope', '$cookieStore', function ($http, $q, $rootScope, $cookieStore) {
  function login(data) {
    const deferred = $q.defer();
    $http.post(`${BACKEND_API}/login`, data)
            .then(
            function (response) {
              deferred.resolve(response.data);
            },
            function (errResponse) {
              deferred.reject(errResponse);
            },
        );
    return deferred.promise;
  }

  function currentUser(token) {
    const deferred = $q.defer();
    $http.defaults.headers.common.Authorization = `Bearer ${token}`;
    $http.get(`${BACKEND_API}/current_user`)
            .then(
            function (response) {
              $cookieStore.put(COOKIES_KEY, response.data);
              deferred.resolve(response.data);
            },
            function (errResponse) {
              deferred.reject(errResponse);
            },
        );
    return deferred.promise;
  }

  function logout() {
    delete $http.defaults.headers.common.Authorization;
    $cookieStore.remove(COOKIES_KEY);
  }

  const factory = {
    login,
    currentUser,
    logout,
  };

  return factory;
}]);

app.controller('discountController', function ($scope) {

});

app.controller('homeController', function ($scope) {

});

app.controller('loginController', function ($scope, AuthService, $rootScope, $location, $window) {
  if ($rootScope.loggedIn) {
    $location.path('/');
    return;
  }

  $scope.data = {
    username: 'fabrice',
    password: 'fab123',
  };

  $scope.submit = function () {
    AuthService.login($scope.data)
      .then(
        function (response) {
          AuthService.currentUser(response.token)
          .then(
            function (response) {
              $window.location.href = '/';
              $window.location.reload();
            },
          );
        },
        function (errResponse) {
          console.error(errResponse);
        },
    );
  };
});

app.controller('registerController', function ($scope, $rootScope) {
  if ($rootScope.loggedIn) {
    $location.path('/');
  }
});
