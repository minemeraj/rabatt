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
