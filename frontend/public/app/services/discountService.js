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
