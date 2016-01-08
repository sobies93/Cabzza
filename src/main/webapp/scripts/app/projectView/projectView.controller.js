'use strict';

angular.module('cabzzaApp')
    .controller('ProjectViewController', function ($scope, $state, $stateParams, $rootScope, NewStockWallet, PortfolioStoreByWallet) {

        $scope.pieChartJson = [];

//        $scope.pieChartJson = [
//                    {
//                        "company": "Asseco Poland",
//                        "percent": 20
//                    },
//                    {
//                        "company": "Wartsila Ship Design",
//                        "percent": 25
//                    },
//                    {
//                        "company": "UXpin",
//                        "percent": 35
//                    },
//                    {
//                        "company": "DiabetesLab",
//                        "percent": 20
//                    }
//                ]
        $scope.loadStockWallet = function (id) {
            NewStockWallet.get({id: id}, function(result) {
                $scope.stockWallet = result;
                PortfolioStoreByWallet.get({id: id}, function(result) {
                    $scope.stockWallet.portfolioStore = result;
                    //_.each(result, function (portfolioStore) {
                    for(var i = 0; i< result.length; i++) {
                        var portfolioStore = result [i];
                        $scope.pieChartJson.push({"company": portfolioStore.stockInfo.name, "percent": portfolioStore.percent});
                    }
                    $scope.pieChartInit();
                });
            });
        };
        $scope.loadStockWallet($stateParams.id);



        /*$scope.lineChartInit = function() {
            var chart;
            var average = 90.4;
            // SERIAL CHART
            chart = new AmCharts.AmSerialChart();

            chart.dataProvider = lineChartJson;
            chart.categoryField = "date";
            chart.dataDateFormat = "YYYY-MM-DD";
            chart.fontFamily = "Lato-Regular";
            chart.fontSize = 15;
            chart.color = "rgba(119,34,17,1)";



            // AXES
            // category
            var categoryAxis = chart.categoryAxis;
            categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
            categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
            categoryAxis.dashLength = 1;
            categoryAxis.gridAlpha = 0.15;
            categoryAxis.axisColor = "#DADADA";
            categoryAxis.fontSize = 10;


            // value
            var valueAxis = new AmCharts.ValueAxis();
            valueAxis.axisColor = "#DADADA";
            valueAxis.dashLength = 1;
            valueAxis.logarithmic = false; // this line makes axis logarithmic
            valueAxis.fontSize = 10;
            chart.addValueAxis(valueAxis);


            // GUIDE for average
            var guide = new AmCharts.Guide();
            guide.value = average;
            guide.lineColor = "#CC0000";
            guide.dashLength = 4;
            guide.label = "average";
            guide.inside = true;
            guide.lineAlpha = 1;
            valueAxis.addGuide(guide);


            // GRAPH
            var graph = new AmCharts.AmGraph();
            graph.type = "smoothedLine";
            graph.bullet = "round";
            graph.bulletColor = "#FFFFFF";
            graph.useLineColorForBulletBorder = true;
            graph.bulletBorderAlpha = 1;
            graph.bulletBorderThickness = 2;
            graph.bulletSize = 7;
            graph.title = "Call me a Chart";
            graph.valueField = "value";
            graph.lineThickness = 2;
            graph.lineColor = "#0000FF";
            chart.addGraph(graph);


            // CURSOR
            var chartCursor = new AmCharts.ChartCursor();
            chartCursor.cursorPosition = "mouse";
            chart.addChartCursor(chartCursor);

            // SCROLLBAR
            var chartScrollbar = new AmCharts.ChartScrollbar();
            chartScrollbar.graph = graph;
            chartScrollbar.scrollbarHeight = 30;
            chart.addChartScrollbar(chartScrollbar);

            chart.creditsPosition = "bottom-right";

            // WRITE
            chart.write("chartdiv2");


        };
        $scope.lineChartInit();*/
        //TODO: (refactoring) move to service
        $scope.pieChartInit = function () {

            var chart;
            var legend;

            // PIE CHART
            chart = new AmCharts.AmPieChart();
            chart.dataProvider = $scope.pieChartJson;
            chart.titleField = "company";
            chart.valueField = "percent";
            chart.alpha = 0.6;
            chart.hoverAlpha = 1;
            chart.fontFamily = "Lato-Regular";

            //quite weird for sizing the font, but labels make the chart tiny while font is changed only on @mobile and not changed fot 760-1200px vw
            chart.fontSize = 15;

            if ($(window).width() < 768) {
                chart.labelsEnabled = false;
                chart.fontSize = 10;
            }

            chart.color = "rgba(119,34,17,1)";
            chart.colors = [
                "#008000",
                "#800000",
                "#FAA460",
                "#FF4500",
                "#FFD700",
                "#FFDEAD",
                "#006400",
                "#9ACD32",
                "#808000",
                "#90EE90",
                "#DC143C",
                "#DAA520",
                "#FFF8DC"
            ];

            $scope.roundTwoDigits = function (value) {
                return (Math.round(value*100) / 100);
            };
            // LEGEND
            legend = new AmCharts.AmLegend();
            legend.align = "center";
            legend.markerType = "square";
            chart.balloonText = "[[title]]<br><span><b>[[value]]</b> ([[percents]]%)</span>";
            chart.addLegend(legend);
            legend.fontFamily = "Lato-Regular";
            legend.fontSize = 15;
            legend.color = "rgba(119,34,17,1)";
            legend.equalWidths = false;
            if ($(window).width() < 768) {
                legend.fontSize = 12;
                legend.equalWidths = true;
            }

            // WRITE
            chart.write("chartdiv1");
        };

    });
