(function () {
  const injectParams = ['$http', '$q', 'config'];

  const discountsFactory = function ($http, $q, config) {
    const factory = {};
    const REST_SERVICE_URI = `${BACKEND_API}/discounts`;

    factory.getDiscounts = function (options) {
      return $http.get(REST_SERVICE_URI, { params: options }).then(
        function (response) {
          return response.data;
        },
        function (errResponse) {
          return errResponse.data;
        },
      );
    };

    factory.insertDiscount = function (discount) {
      return $http.post(REST_SERVICE_URI, discount).then(
        function (response) {
          discount.id = response.data.id;
          return response.data;
        },
        function (errResponse) {
          return errResponse.data;
        },
      );
    };

    factory.newDiscount = function () {
      return $q.when({ id: 0 });
    };

    factory.updateDiscount = function (discount) {
      return $http.put(discount._links.self.href, discount).then(
        function (response) {
          return response.data;
        },
        function (errResponse) {
          return errResponse.data;
        },
      );
    };

    factory.deleteDiscount = function (id) {
      return $http.delete(discount._links.self.href).then(
        function (response) {
          return response.data;
        },
        function (errResponse) {
          return errResponse.data;
        },
      );
    };

    factory.getDiscountById = function (id) {
      return $http.get(`${REST_SERVICE_URI}/${id}`).then(function (response) {
        return response.data;
      });
    };

    return factory;
  };

  discountsFactory.$inject = injectParams;

  angular.module('rabattApp').factory('discountService', discountsFactory);
}());
