(function () {
  const injectParams = ['$http', '$rootScope', '$cookieStore'];

  const authFactory = function ($http, $rootScope, $cookieStore) {
    const factory = {
      user: {
        data: $cookieStore.get(COOKIES_KEY),
        isAuthenticated: !!$cookieStore.get(COOKIES_KEY),
      },
    };

    factory.login = function (username, password) {
      data = {
        username,
        password,
      };
      return $http.post(`${BACKEND_API}/login`, data).then(
        function (results) {
          return factory.currentUser(results.data.token);
        },
        function (err) {
          return err;
        });
    };

    factory.currentUser = function (token) {
      $http.defaults.headers.common.Authorization = `Bearer ${token}`;
      return $http.get(`${BACKEND_API}/current_user`).then(
        function (results) {
          $cookieStore.put(COOKIES_KEY, results.data);
          changeAuth();
          return results;
        },
        function (err) {
          changeAuth();
          return err;
        },
      );
    };

    factory.logout = function () {
      delete $http.defaults.headers.common.Authorization;
      $cookieStore.remove(COOKIES_KEY);
      factory.user = {};
      changeAuth();
    };

    function changeAuth() {
      factory.user.data = $cookieStore.get(COOKIES_KEY);
      factory.user.isAuthenticated = !!$cookieStore.get(COOKIES_KEY);
    }

    return factory;
  };

  authFactory.$inject = injectParams;

  angular.module('rabattApp').factory('authService', authFactory);
}());

