define([], function () {
    'use strict';

    /**
     * Controls the index page
     */
    var HomeCtrl = function ($scope, $rootScope, $http, $uibModal, $location, helper) {
        console.log(helper.sayHi());
        $rootScope.pageTitle = 'springboot-angular-seed';

        $scope.queryString = "";
        $scope.blacklistHitsDesc = "";

        $scope.gridOptions = {
            multiSelect: false,
            enableRowSelection: true,
            enableRowHeaderSelection: false,
            enableColumnMenus: false,
            enableSorting: false,
            enablePaginationControls: false,
            paginationPageSize: 25,
            columnDefs: [
                {displayName: 'ID', field: '_id', visible: false},
                {displayName: '姓名', field: 'name'},
                {displayName: '电话', field: 'tel'},
                {displayName: '邮箱', field: 'email'},
                {
                    displayName: '详情',
                    field: 'details',
                    cellTemplate: '<div class="ui-grid-cell-contents">' +
                    '<button type="button" class="btn btn-xs btn-primary" ' +
                    'ng-click="grid.appScope.showDetails(grid, row)">' +
                    '<i class="fa fa-edit"></i>查看详情' +
                    '</button>' +
                    '</div>'
                }
            ],
            data: []
        };

        $scope.gridOptions.onRegisterApi = function (gridApi) {
            $scope.gridApi = gridApi;
        };

        $scope.search = function () {
            if ($scope.queryString.trim().length == 0) {
                return;
            }
            var queryUrl = "/blacklist/query?q=" + $scope.queryString;
            $http.get(queryUrl)
                .success(function (response, status, headers, config) {
                    $scope.gridOptions.data = JSON.parse(response.data);

                    var totalHits = $scope.gridOptions.data.length;
                    if (totalHits == 0) {
                        $scope.blacklistHitsDesc = "没有找到符合条件的记录";
                    } else {
                        $scope.blacklistHitsDesc = "共搜索到" + totalHits + "条记录";
                    }
                })
                .error(function (response, status, headers, config) {
                    console.log(response);
                });
        };

        $scope.showDetails = function (grid, row) {
            delete row.entity._id;
            delete row.entity.$$hashKey;

            var content = JSON.stringify(row.entity, null, '\t').replace(/null/g, "\"\"");

            $uibModal.open({
                template: '<div>' +
                '<div class="modal-header">' +
                '<h3 class="modal-title">详细信息</h3>' +
                '</div>' +
                '<div class="modal-body">' +
                '<textarea style="width: 560px; height: 270px" readonly="readonly">' +
                content +
                '</textarea>' +
                '</div>' +
                '<div class="modal-footer">' +
                '<button class="btn btn-warning" ng-click="$close()">关闭</button>' +
                '</div>' +
                '</div>',
                controller: ['$uibModalInstance', 'grid', 'row', BlacklistDetailsCtrl],
                controllerAs: grid.appScope,
                resolve: {
                    grid: function () {
                        return grid;
                    },
                    row: function () {
                        return row;
                    }
                }
            });
        }
    };
    HomeCtrl.$inject = ['$scope', '$rootScope', '$http', '$uibModal', '$location', 'helper'];

    var BlacklistDetailsCtrl = function ($uibModalInstance, grid, row) {
        var vm = this;

        vm.content = angular.copy(row.entity);
        vm.grid = grid;
        vm.row = row;
    };

    return {
        HomeCtrl: HomeCtrl
    };

});
