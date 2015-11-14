/* globals $ */
'use strict';

angular.module('cabzzaApp')
		.directive('slidersAndDatepickers', function () {
			return {
				restrict: 'E',
				link: function (scope, element, attrs) {
					scope.$on('sliders', function () {
						var classes = ["fa-angle-double-down", "fa-angle-double-up", "fa-arrows-h"];

						$(".stateIcon.fa").each(function () {
							$(this).addClass(classes[~~(Math.random() * classes.length)]);
						});


						/*****SLIDERS****/

						/*https://github.com/seiyria/bootstrap-slider.git*/

						/*****slider for security*****/

						$('#bezpieczenstwo').slider({
							formatter: function (value) {
								return value + "%";
							}
						});

						/*****slider for profit*****/

						$('#preofit').slider({
							formatter: function (value) {
								return value + "%";
							}
						});

						/****DATEPICKERS****/

						/*****datepicker for start*****/

						$(function () {
							$('#datepickerStart').datetimepicker({
								language: 'pt-BR'
							});
						});

						/*****datepicker for end*****/

						$(function () {
							$('#datepickerEnd').datetimepicker({
								language: 'pt-BR'
							});
						});

						/*****datepicker for range*****/

						$(function () {
							$('#datepickerRange').datetimepicker({
								language: 'pt-BR'
							});
						});
					});
				}
			};
		});