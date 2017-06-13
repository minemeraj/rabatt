var baseLocation = 'app/components/';

app.config([ '$routeProvider',
	function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : baseLocation + 'users/usersView.html',
			controller : 'UserController',
		});
	},
]);

app.config([ '$routeProvider',
	function($routeProvider) {
		$routeProvider.when('/home', {
			templateUrl : baseLocation + 'home/homeView.html',
			controller : 'homeController',
		});
	},
]);

app.config([ '$routeProvider',
	function($routeProvider) {
		$routeProvider.when('/login', {
			templateUrl : baseLocation + 'login/loginView.html',
			controller : 'loginController',
		});
	},
]);