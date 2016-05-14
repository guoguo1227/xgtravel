/**
 * jQuery Form Validator
 * ------------------------------------------
 *
 * Spanish language package
 *
 * @website http://formvalidator.net/
 * @license Dual licensed under the MIT or GPL Version 2 licenses
 * @version 2.2.53
 */
(function($, window) {

  "use strict";

  $(window).bind('validatorsLoaded', function() {

    $.formUtils.LANG = {
      errorTitle: '提交失败！',
          requiredFields: '此处不能为空',
          badTime: '时间格式不正确',
          badEmail: '邮箱格式不正确',
          badTelephone: '手机号格式不正确',
          badSecurityAnswer: 'You have not given a correct answer to the security question',
          badDate: '时间格式不正确',
          lengthBadStart: '字数必须限制在 ',
          lengthBadEnd: ' 个字',
          lengthTooLongStart: 'The input value is longer than ',
          lengthTooShortStart: 'The input value is shorter than ',
          notConfirmed: 'Input values could not be confirmed',
          badDomain: 'Incorrect domain value',
          badUrl: 'url格式不正确',
          badCustomVal: '格式不正确',
          andSpaces: ' and spaces ',
          badInt: '请输入整数',
          badSecurityNumber: 'Your social security number was incorrect',
          badUKVatAnswer: 'Incorrect UK VAT Number',
          badStrength: 'The password isn\'t strong enough',
          badNumberOfSelectedOptionsStart: 'You have to choose at least ',
          badNumberOfSelectedOptionsEnd: ' answers',
          badAlphaNumeric: 'The input value can only contain alphanumeric characters ',
          badAlphaNumericExtra: ' and ',
          wrongFileSize: 'The file you are trying to upload is too large (max %s)',
          wrongFileType: 'Only files of type %s is allowed',
          groupCheckedRangeStart: '请至少选择一个（',
          groupCheckedTooFewStart: 'Please choose at least ',
          groupCheckedTooManyStart: 'Please choose a maximum of ',
          groupCheckedEnd: ' ）项',
          badCreditCard: 'The credit card number is not correct',
          badCVV: 'The CVV number was not correct',
          wrongFileDim : 'Incorrect image dimensions,',
          imageTooTall : 'the image can not be taller than',
          imageTooWide : 'the image can not be wider than',
          imageTooSmall : 'the image was too small',
          min : '最小值为',
          max : '最大值为',
          imageRatioNotAccepted : 'Image ratio is not be accepted',
          badBrazilTelephoneAnswer: 'The phone number entered is invalid',
          badBrazilCEPAnswer: 'The CEP entered is invalid',
          badBrazilCPFAnswer: 'The CPF entered is invalid'
    };

  });

})(jQuery, window);


