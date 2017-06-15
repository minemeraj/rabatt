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
    $http.post(`${BACKEND_API}/login`, data).then(
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
    $http.get(`${BACKEND_API}/current_user`).then(
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

app.factory('DiscountService', ['$http', '$q', '$rootScope', '$cookieStore', function ($http, $q, $rootScope, $cookieStore) {
  const REST_SERVICE_URI = `${BACKEND_API}/discounts`;

  function fetchAllDiscounts(options) {
    const deferred = $q.defer();
    $http.get(REST_SERVICE_URI, { params: options }).then(
      function (response) {
        deferred.resolve(response.data);
      },
      function (errResponse) {
        deferred.reject(errResponse);
      },
    );
    return deferred.promise;
  }

  function createDiscount(discount) {
    const deferred = $q.defer();
    $http.post(REST_SERVICE_URI, discount).then(
      function (response) {
        deferred.resolve(response.data);
      },
      function (errResponse) {
        deferred.reject(errResponse);
      },
    );
    return deferred.promise;
  }

  function updateDiscount(discount) {
    const deferred = $q.defer();
    $http.put(discount._links.self.href, discount).then(
      function (response) {
        deferred.resolve(response.data);
      },
      function (errResponse) {
        deferred.reject(errResponse);
      },
    );
    return deferred.promise;
  }

  function deleteDiscount(discount) {
    const deferred = $q.defer();
    $http.delete(discount._links.self.href).then(
      function (response) {
        deferred.resolve(response.data);
      },
      function (errResponse) {
        deferred.reject(errResponse);
      },
    );
    return deferred.promise;
  }

  const factory = {
    fetchAllDiscounts,
    createDiscount,
    updateDiscount,
    deleteDiscount,
  };

  return factory;
}]);

app.factory('PagerService', ['$http', '$q', '$rootScope', function ($http, $q, $rootScope) {
  function GetPager(totalItems, currentPage, pageSize) {
    currentPage = currentPage || 1;
    pageSize = pageSize || 5;
    const totalPages = Math.ceil(totalItems / pageSize);

    let startPage;
    let endPage;

    if (totalPages <= 10) {
      startPage = 1;
      endPage = totalPages;
    } else if (currentPage <= 6) {
      startPage = 1;
      endPage = 10;
    } else if (currentPage + 4 >= totalPages) {
      startPage = totalPages - 9;
      endPage = totalPages;
    } else {
      startPage = currentPage - 5;
      endPage = currentPage + 4;
    }

    const startIndex = (currentPage - 1) * pageSize;
    const endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);
    const pages = [];

    for (let i = startPage; i < endPage + 1; i += 1) {
      pages.push(i);
    }

    return {
      totalItems,
      currentPage,
      pageSize,
      totalPages,
      startPage,
      endPage,
      startIndex,
      endIndex,
      pages,
    };
  }

  const service = {};
  service.GetPager = GetPager;
  return service;
}]);

app.controller('discountController', function ($scope) {

});

app.controller('homeController', function ($scope, DiscountService, PagerService) {
  $scope.options = {
    keyword: null,
    page: 0,
    size: 5,
    sort: null,
  };

  $scope.pager = {};
  $scope.setPage = setPage;

  fetchAllDiscounts(true);

  function setPage(page) {
    if (page < 1 || page > $scope.pager.totalPages) {
      return;
    }

    $scope.pager = PagerService.GetPager($scope.totalElements, page);
    $scope.options.page = page - 1;
    $scope.options.size = $scope.pager.pageSize;
    fetchAllDiscounts();
  }

  function fetchAllDiscounts(init) {
    DiscountService.fetchAllDiscounts($scope.options).then(
      function (discounts) {
        $scope.discounts = discounts._embedded.discounts;
        $scope.totalElements = discounts.page.totalElements;
        if (init) {
          setPage(1);
        }
      },
      function (errResponse) {
        console.error('Error while fetching Users');
      },
    );
  }
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
    AuthService.login($scope.data).then(
      function (response) {
        AuthService.currentUser(response.token).then(
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
