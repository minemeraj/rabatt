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
