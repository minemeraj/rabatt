(function () {
  const injectParams = ['$http', '$rootScope'];

  const authFactory = function ($http, $rootScope) {
    const serviceBase = '/api/v1/',
      factory = {
        loginPath: '/login',
        user: {
          isAuthenticated: false,
          roles: null,
        },
      };

    factory.login = function (email, password) {
            // TODO: FAKE data, remove this one
      return new Promise(function (resolve, reject) {
        const loggedIn = true;
        changeAuth(loggedIn);
        resolve(loggedIn);
      });
            // TODO: UNMARK below and remove above code when api is ready
            // return $http.post(serviceBase + 'login', { userLogin: { userName: email, password: password } }).then(
            //     function (results) {
            //         var loggedIn = results.data.status;;
            //         changeAuth(loggedIn);
            //         return loggedIn;
            //     });
    };

    factory.logout = function () {
            // TODO: FAKE data, remove this one
      return new Promise(function (resolve, reject) {
        const loggedIn = true;
        changeAuth(loggedIn);
        resolve(loggedIn);
      });
            // TODO: UNMARK below and remove above code when api is ready
            // return $http.post(serviceBase + 'logout').then(
            //     function (results) {
            //         var loggedIn = !results.data.status;
            //         changeAuth(loggedIn);
            //         return loggedIn;
            //     });
    };

    factory.redirectToLogin = function () {
      $rootScope.$broadcast('redirectToLogin', null);
    };

    function changeAuth(loggedIn) {
      factory.user.isAuthenticated = loggedIn;
      $rootScope.$broadcast('loginStatusChanged', loggedIn);
    }

    return factory;
  };

  authFactory.$inject = injectParams;

  angular.module('rabattApp').factory('authService', authFactory);
}());

