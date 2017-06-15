(function () {
  const injectParams = ['$http', '$q', 'config'];

  const discountsFactory = function ($http, $q, config) {
    const serviceBase = '/api/v1/',
      factory = {},
            // TODO: FAKE data, remove this one
      dummydata = [
        {
          id: 1,
          category: 'Gearbest Angebote',
          link: 'http://google.com',
          avatar: 'default.jpg',
          title: '1 OnePlus 3T Global Version 4G Phablet - GLOBAL VERSION GRAY (B-20), 6GB RAM 64GB ROM Snapdragon 821 16MP Front Camera',
          description: 'Guten Abend, aktuell über Email only, geniales Angebot für OnePlus 3T mit geliebtem B-20. Nur für 355,99€ PVG: 399 € (Pandacheck) Berstellt mit Germany prior...',
          postDate: '12/06/2017',
          user: 'erast',
          price: 355.99,
          voteTemp: 734,
          commentCount: 48,
        }, {
          id: 2,
          category: 'Gearbest Angebote',
          link: 'http://google.com',
          avatar: 'default.jpg',
          title: '2 OnePlus 3T Global Version 4G Phablet - GLOBAL VERSION GRAY (B-20), 6GB RAM 64GB ROM Snapdragon 821 16MP Front Camera',
          description: 'Guten Abend, aktuell über Email only, geniales Angebot für OnePlus 3T mit geliebtem B-20. Nur für 355,99€ PVG: 399 € (Pandacheck) Berstellt mit Germany prior...',
          postDate: '12/06/2017',
          user: 'erast',
          price: 355.99,
          voteTemp: 734,
          commentCount: 48,
        }, {
          id: 3,
          category: 'Gearbest Angebote',
          link: 'http://google.com',
          avatar: 'default.jpg',
          title: '3 OnePlus 3T Global Version 4G Phablet - GLOBAL VERSION GRAY (B-20), 6GB RAM 64GB ROM Snapdragon 821 16MP Front Camera',
          description: 'Guten Abend, aktuell über Email only, geniales Angebot für OnePlus 3T mit geliebtem B-20. Nur für 355,99€ PVG: 399 € (Pandacheck) Berstellt mit Germany prior...',
          postDate: '12/06/2017',
          user: 'erast',
          price: 355.99,
          voteTemp: 734,
          commentCount: 48,
        }, {
          id: 4,
          category: 'Gearbest Angebote',
          link: 'http://google.com',
          avatar: 'default.jpg',
          title: '4 OnePlus 3T Global Version 4G Phablet - GLOBAL VERSION GRAY (B-20), 6GB RAM 64GB ROM Snapdragon 821 16MP Front Camera',
          description: 'Guten Abend, aktuell über Email only, geniales Angebot für OnePlus 3T mit geliebtem B-20. Nur für 355,99€ PVG: 399 € (Pandacheck) Berstellt mit Germany prior...',
          postDate: '12/06/2017',
          user: 'erast',
          price: 355.99,
          voteTemp: 734,
          commentCount: 48,
        },
      ];

    factory.getDiscounts = function () {
            // TODO: FAKE data, remove this one
      return new Promise(function (resolve, reject) {
        resolve(dummydata);
      });
            // TODO: UNMARK below and remove above code when api is ready
            // return $http.get(serviceBase + 'discounts').then(function (results) {
            //     return results.data;
            // });
    };

    factory.insertDiscount = function (discount) {
            // TODO: FAKE data, remove this one
      return new Promise(function (resolve, reject) {
        discount.id = dummydata[dummydata.length - 1].id + 1;
        discount.voteTemp = 0;
        dummydata.splice(dummydata.length, 0, discount);
        resolve(discount);
      });
            // TODO: UNMARK below and remove above code when api is ready
            // return $http.post(serviceBase + 'discounts', discount).then(function (results) {
            //     discount.id = results.data.id;
            //     return results.data;
            // });
    };

    factory.newDiscount = function () {
      return $q.when({ id: 0 });
    };

    factory.updateDiscount = function (discount) {
            // TODO: FAKE data, remove this one
      return new Promise(function (resolve, reject) {
        dummydata[req.params.id - 1] = discount;
        resolve({ status: true });
      });
            // TODO: UNMARK below and remove above code when api is ready
            // return $http.put(serviceBase + 'discounts/' + discount.id, discount).then(function (status) {
            //     return status.data;
            // });
    };

    factory.deleteDiscount = function (id) {
            // TODO: FAKE data, remove this one
      return new Promise(function (resolve, reject) {
        dummydata.splice(id - 1, 1);
        resolve({ status: true });
      });
            // TODO: UNMARK below and remove above code when api is ready
            // return $http.delete(serviceBase + 'discounts/' + id).then(function (status) {
            //     return status.data;
            // });
    };

    factory.getDiscountById = function (id) {
            // TODO: FAKE data, remove this one
      return new Promise(function (resolve, reject) {
        resolve(dummydata[id - 1]);
      });
            // TODO: UNMARK below and remove above code when api is ready
            // return $http.get(serviceBase + 'discounts/' + id).then(function (results) {
            //     return results.data;
            // });
    };

    return factory;
  };

  discountsFactory.$inject = injectParams;

  angular.module('rabattApp').factory('dataService', discountsFactory);
}());
