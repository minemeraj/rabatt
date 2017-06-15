(function () {
  angular.module('rabattApp')
        .config(['$httpProvider', function ($httpProvider) {
          const injectParams = ['$q', '$rootScope'];

          const httpInterceptor401 = function ($q, $rootScope) {
            const success = function (response) {
              return response;
            };

            const error = function (res) {
              if (res.status === 401) {
                    // Raise event so listener (navbarController) can act on it
                $rootScope.$broadcast('redirectToLogin', null);
                return $q.reject(res);
              }
              return $q.reject(res);
            };

            return function (promise) {
              return promise.then(success, error);
            };
          };

          httpInterceptor401.$inject = injectParams;

          $httpProvider.interceptors.push(httpInterceptor401);
        }]);
}());
