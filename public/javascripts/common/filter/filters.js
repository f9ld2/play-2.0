var filterCommon = angular.module('filters', []);

filterCommon.filter('ccStrDateFilter', function() {
    return function(input) {
        if (isEmpty(input)) {
            return '';
        }
        var match = input.match(/(\d{4})(\d{2})(\d{2})/);
        return match[1] + '/' + match[2] + '/' + match[3];
    };
});

filterCommon.filter('ccStrTimeFilter', function() {
    return function(input) {
        if (isEmpty(input)) {
            return '';
        }
        var match = input.match(/(\d{2})(\d{2})(\d{2})/);
        return match[1] + ':' + match[2] + ':' + match[3];
    };
});

filterCommon.filter('ccPostalCodeFilter', function() {
    return function(input) {
        if (isEmpty(input)) {
            return '';
        }
        return input.replace(/^(\d{3})(\d{4})/, '$1-$2')
    };
});

filterCommon.filter('ccTriCdFilter', function() {
    return function(input) {
        if (isEmpty(input)) {
            return '';
        }
        return input.replace(/^(\d{6})(\d{3})/, '$1-$2')
    };
});

filterCommon.filter('ccTextNumber', function($filter) {
    return function(number, size) {
        number = $filter('number')(toString(number), size);
        number = number.replace(new RegExp(',', 'g'), '');
        return number;
    };
});

filterCommon.filter('ccNumberFilter', function($filter) {
    return function(number, size) {
        if (number == null) {
            return "";
        }
        number = $filter('number')(toString(number), size);
        return number;
    };
});

filterCommon.filter('truncate', function() {
    return function(text, length) {
        if (isNaN(length)) {
            length = 10;
        }

        var end = "...";

        if (text.length <= length) {
            return text;
        } else {
            return String(text).substring(0, length - 3) + end;
        }
    };
});