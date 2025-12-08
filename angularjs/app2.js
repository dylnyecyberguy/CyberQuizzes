var app = angular.module('studentApp', ['ngRoute']);

// routing config
app.config(['$routeProvider', function($routeProvider) {

    $routeProvider
        .when('/home',        { templateUrl: 'home.html' })
        .when('/students',    { templateUrl: 'students.html' })
        .when('/contact-us',  { templateUrl: 'contact-us.html' })
        .when('/about-us',    { templateUrl: 'about-us.html' })
        .otherwise({
            redirectTo: '/home'
        });

}]);
