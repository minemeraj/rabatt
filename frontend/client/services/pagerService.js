(function () {
  const pagerFactory = function () {
    const factory = {};

    factory.getPager = function (totalItems, currentPage, pageSize) {
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
    };

    return factory;
  };

  angular.module('rabattApp').factory('pagerService', pagerFactory);
}());
