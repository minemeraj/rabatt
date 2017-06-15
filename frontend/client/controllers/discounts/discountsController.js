(function () {
  const injectParams = ['$scope', '$location', '$filter', '$window',
    '$timeout', 'authService', 'discountService', 'modalService', 'pagerService', '$routeParams'];

  const DiscountsController = function ($scope, $location, $filter, $window,
    $timeout, authService, discountService, modalService, pagerService, $routeParams) {
    const discountId = ($routeParams.discountId) ? parseInt($routeParams.discountId, 10) : 0;
    $scope.discounts = [];
    $scope.options = {
      keyword: null,
      page: 0,
      size: 5,
      sort: null,
    };

    $scope.pager = {};
    $scope.setPage = setPage;

    $scope.upVote = function (index) {
      const item = $scope.discounts[index];
      item.voteTemp += 1;
      updateDiscount(item, index);
    };

    $scope.downVote = function (index) {
      const item = $scope.discounts[index];
      item.voteTemp -= 1;
      updateDiscount(item, index);
    };

    function init() {
      if (discountId > 0) {
        getDiscountById(discountId);
      } else {
        getDiscounts(true);
      }
    }

    function setPage(page) {
      if (page < 1 || page > $scope.pager.totalPages) {
        return;
      }

      $scope.pager = pagerService.getPager($scope.totalElements, page);
      $scope.options.page = page - 1;
      $scope.options.size = $scope.pager.pageSize;
      getDiscounts();
    }

    function getDiscounts(init) {
      discountService.getDiscounts($scope.options).then(
        function (data) {
          $scope.discounts = data._embedded.discounts;
          $scope.totalElements = data.page.totalElements;
          if (init) {
            setPage(1);
          }
        },
        function (error) {
          $window.alert(`Sorry, an error occurred: ${error.data.message}`);
        });
    }

    function getDiscountById(discountId) {
      discountService.getDiscountById(discountId).then(
        function (data) {
          console.log(data);
          $scope.discount = data;
        },
        function (error) {
          $window.alert(`Sorry, an error occurred: ${error.data.message}`);
        });
    }

    function updateDiscount(discount, index) {
      discountService.updateDiscount(discount).then(
        function (data) {
          $scope.discounts[index] = data;
        },
        function (error) {
          $window.alert(`Sorry, an error occurred: ${error.data.message}`);
        });
    }

    init();
  };

  DiscountsController.$inject = injectParams;

  angular.module('rabattApp').controller('DiscountsController', DiscountsController);
}());
