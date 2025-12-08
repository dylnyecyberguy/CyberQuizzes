var app = angular.module('studentApp', []);

app.controller('StudentController', ['$scope', '$http',
    function ($scope, $http) {

        var API_BASE = "http://localhost:8080/api/students";

        $scope.students = [];
        $scope.selectedStudent = {};
        $scope.message = "";
        $scope.error = "";

        // -----------------------------------------------------
        // Utility: convert "yyyy-MM-dd" or ISO string -> JS Date
        // (LOCAL, NO TIMEZONE SHIFT)
        // -----------------------------------------------------
        function toDateNull(value) {
            if (!value) return null;
            let d = new Date(value);
            return isNaN(d) ? null : d;
        }

        // -----------------------------------------------------
        // Utility: convert JS Date -> "yyyy-MM-dd" string
        // -----------------------------------------------------
        function toIsoDateNull(dateObj) {
            if (!dateObj) return null;
            if (isNaN(new Date(dateObj))) return null;
            let d = new Date(dateObj);
            return d.toISOString().substring(0, 10);
        }

        // -----------------------------------------------------
        // Load all students
        // -----------------------------------------------------
        $scope.loadStudents = function () {
            $http.get(API_BASE)
                .then(function (response) {

                    $scope.students = response.data || [];

                    // convert dates for safe binding to <input type="date">
                    $scope.students.forEach(function (s) {
                        s.dateOfBirth = toIsoDateNull(s.dateOfBirth);
                        s.enrollmentDate = toIsoDateNull(s.enrollmentDate);
                    });

                    $scope.message = "Loaded " + $scope.students.length + " student(s).";
                    $scope.error = "";
                })
                .catch(function (error) {
                    console.error(error);
                    $scope.error = "Failed to load students.";
                    $scope.message = "";
                });
        };

        // -----------------------------------------------------
        // Reset form
        // -----------------------------------------------------
        $scope.resetForm = function () {
            $scope.selectedStudent = {};
            $scope.message = "";
            $scope.error = "";
        };

        // -----------------------------------------------------
        // Edit Student
        // -----------------------------------------------------
        $scope.editStudent = function (student) {
            $scope.selectedStudent = angular.copy(student);

            // convert ISO string -> JS Date for date picker
            $scope.selectedStudent.dateOfBirth =
                toDateNull($scope.selectedStudent.dateOfBirth);

            $scope.selectedStudent.enrollmentDate =
                toDateNull($scope.selectedStudent.enrollmentDate);

            $scope.message = "";
            $scope.error = "";
        };

        // -----------------------------------------------------
        // Save (Create or Update)
        // -----------------------------------------------------
        $scope.saveStudent = function () {
            var payload = angular.copy($scope.selectedStudent);

            // turn JS Date back into yyyy-MM-dd string
            payload.dateOfBirth = toIsoDateNull(payload.dateOfBirth);
            payload.enrollmentDate = toIsoDateNull(payload.enrollmentDate);

            if (payload.studentId) {
                // UPDATE
                var url = API_BASE + "/" + payload.studentId;
                $http.put(url, payload)
                    .then(function (response) {
                        $scope.message = "Student updated successfully.";
                        $scope.error = "";
                        $scope.loadStudents();
                        $scope.resetForm();
                    })
                    .catch(function (error) {
                        console.error(error);
                        $scope.error = "Failed to update student.";
                        $scope.message = "";
                    });

            } else {
                // CREATE
                $http.post(API_BASE, payload)
                    .then(function (response) {
                        $scope.message = "Student created successfully.";
                        $scope.error = "";
                        $scope.loadStudents();
                        $scope.resetForm();
                    })
                    .catch(function (error) {
                        console.error(error);
                        $scope.error = "Failed to create student.";
                        $scope.message = "";
                    });
            }
        };

        // -----------------------------------------------------
        // Delete student
        // -----------------------------------------------------
        $scope.deleteStudent = function (student) {
            if (!confirm("Are you sure you want to delete student ID " + student.studentId + " ?")) {
                return;
            }

            var url = API_BASE + "/" + student.studentId;
            $http.delete(url)
                .then(function (response) {
                    $scope.message = "Student deleted successfully.";
                    $scope.error = "";
                    $scope.loadStudents();
                })
                .catch(function (error) {
                    console.error(error);
                    $scope.error = "Failed to delete student.";
                    $scope.message = "";
                });
        };

        // -----------------------------------------------------
        // Initial load
        // -----------------------------------------------------
        $scope.loadStudents();
    }
]);
