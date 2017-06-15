(function () {
  const injectParams = ['$scope', '$location', '$filter', '$window',
    '$timeout', 'authService', 'dataService', 'modalService'];

  const DiscountsController = function ($scope, $location, $filter, $window,
    $timeout, authService, dataService, modalService) {
    $scope.discounts = [];

    $scope.upVote = function (index) {
      const item = $scope.discounts[index];
      item.voteTemp += 1;
      updateDiscount(item);
    };

    $scope.downVote = function (index) {
      const item = $scope.discounts[index];
      item.voteTemp -= 1;
      updateDiscount(item);
    };

    function init() {
      getDiscounts();
    }

    function getDiscounts() {
      dataService.getDiscounts($scope.currentPage - 1).then(function (data) {
        $scope.$apply(function () {
          $scope.discounts = data;
        });
      }, function (error) {
        $window.alert(`Sorry, an error occurred: ${error.data.message}`);
      });
    }

    function updateDiscount(discount) {
      dataService.updateDiscount(discount).then(function () {
                // TODO:

      }, function (error) {
        $window.alert(`Sorry, an error occurred: ${error.data.message}`);
      });
    }

    init();
  };

  DiscountsController.$inject = injectParams;

  angular.module('rabattApp').controller('DiscountsController', DiscountsController);
}());
