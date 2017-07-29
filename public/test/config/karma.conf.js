module.exports = function(config){
    config.set({
    basePath : '../../',

    files : [
      'javascripts/jquery-1.9.0.min.js',
      'javascripts/jquery-ui.js',
      'javascripts/angular.js',
      'javascripts/angular-route.min.js',
      'javascripts/angular-cookies.min.js',
      'javascripts/angular-translate.min.js',
      'javascripts/angular-translate-handler-log.min.js',
      'javascripts/angular-translate-loader-static-files.min.js',
      'javascripts/angular-translate-storage-cookie.min.js',
      'javascripts/angular-translate-storage-local.min.js',
      'javascripts/angular-ui.js',
      'javascripts/bootstrap.min.js',
      'javascripts/angular-ui-select2/src/select2.js',
      'javascripts/select2.min.js',
      'javascripts/select2_locale_ja.js',
      'javascripts/app/member/controllers.js',
      'javascripts/app/member/services.js',
      'javascripts/common/directives/directiveCommon.js',
      'javascripts/common/directives/directive-conf.js',
      'javascripts/common/directives/cc-code-input.js',
      'javascripts/common/directives/cc-date-input.js',
      'javascripts/common/directives/cc-time-input.js',
      'javascripts/common/directives/cc-string-input.js',
      'javascripts/common/directives/cc-bmn-combobox.js',
      'javascripts/common/directives/cc-kbn-combobox.js',
      'javascripts/common/directives/cc-mei-combobox.js',
      'javascripts/common/directives/cc-kbn2-combobox.js',
      'javascripts/common/directives/cc-mtori-mltcombobox.js',
      'javascripts/common/directives/cc-mei-mltcombobox.js',
      'javascripts/common/directives/cc-bmn-mltcombobox.js',
      'javascripts/common/directives/cc-tri-mltcombobox.js',
      'javascripts/common/directives/cc-jigyobu-combobox.js',
      'javascripts/common/directives/cc-fromto-date-input.js',
      'javascripts/common/directives/cc-fromto-time-input.js',
      'javascripts/common/directives/cc-fromto-number-input.js',
      'javascripts/common/directives/cc-tencd-input.js',
      'javascripts/common/directives/cc-combobox.js',
      'javascripts/common/directives/cc-chubnrcd-input.js',
      'javascripts/common/directives/cc-shobnrcd-input.js',
      'javascripts/common/directives/cc-rejicd-input.js',
      'javascripts/common/directives/cc-mtoricd-input.js',
      'javascripts/common/directives/cc-tantocd-input.js',
      'javascripts/common/directives/cc-tengrpcd-input.js',
      'javascripts/common/directives/cc-trikmkcd-input.js',
      'javascripts/common/directives/cc-oyakkkcd-input.js',
      'javascripts/common/directives/cc-bnkstncd-input.js',
      'javascripts/common/directives/cc-kikakucd-input.js',
      'javascripts/common/directives/commonFunctions.js',


      'test/test/lib/angular/angular-mocks.js',
      'test/test/lib/angular/browserTrigger.js',
      
//      'test/test/unit/directiveCCCodeInput.js'
//      'test/test/unit/directiveCCDateInput.js',
//      'test/test/unit/directiveCCTimeInput.js',
//      'test/test/unit/directiveCCMax.js',
//      'test/test/unit/directiveCCMin.js',
//      'test/test/unit/directiveCCFromtoDateInput.js',
//      'test/test/unit/directiveCCFromtoTimeInput.js',
//      'test/test/unit/directiveCCValidateEqual.js',
//      'test/test/unit/directiveCCStringInput.js',
//      'test/test/unit/directiveCCBmnCombobox.js',

//      'test/test/unit/directiveCCOyakkkcdInput.js',
//      'test/test/unit/directiveCCBnkstncdInput.js',
//        'test/test/unit/directiveCCKikakucdInput.js',
//      
        'test/test/unit/directiveCCTencdInput.js',
        'test/test/unit/directiveCCChubnrcdInput.js',
        'test/test/unit/directiveCCShobnrcdInput.js',
        'test/test/unit/directiveCCRejicdInput.js',
        'test/test/unit/directiveCCMtoricdInput.js',
        'test/test/unit/directiveCCTantocdInput.js',
        'test/test/unit/directiveCCTengrpcdInput.js',
        'test/test/unit/directiveCCTrikmkcdInput.js',
        'test/test/unit/directiveCCFromtoNumberInput.js',
        'test/test/unit/directiveCCMtoriMltcombobox.js',
        'test/test/unit/directiveCCMeiMltcombobox.js',
        'test/test/unit/directiveCCBmnMltcombobox.js',
        'test/test/unit/directiveCCTriMltcombobox.js'
      

//       
//      'test/test/unit/directiveCCKbn2Combobox.js',
//      'test/test/unit/directiveCCKbnCombobox.js',
//      'test/test/unit/directiveCCMeiCombobox.js',
//      'test/test/unit/directiveCCJigyobuCombobox.js',
//      'test/test/unit/directiveCCCombobox.js'
    ],

    exclude : [
    ],

    autoWatch : true,

    frameworks: ['jasmine'],

    browsers : ['Chrome'],

    plugins : [
            'karma-junit-reporter',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine',
            'karma-htmlfile-reporter'
            ],

    junitReporter : {
      outputFile: 'Jasmine/config/target/unit.xml',
      suite: 'unit'
    },
    
    reporters: ['progress', 'html'],

    htmlReporter: {
      outputFile: 'tests/units.html'
    }

})}
