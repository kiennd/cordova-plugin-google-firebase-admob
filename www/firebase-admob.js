var exec = require('cordova/exec')

module.exports = {
  addTestDevice: function (deviceId) {
    return new Promise(
      function (resolve, reject) {
        exec(resolve, reject, 'FirebaseAdmobPlugin', 'addTestDevice', [deviceId]);
      }
    )
  },
  getTestDevices: function () {
    return new Promise(
      function (resolve, reject) {
        exec(resolve, reject, 'FirebaseAdmobPlugin', 'getTestDevices', []);
      }
    )
  },
  requestInterstitial: function () {
    return new Promise(
      function (resolve, reject) {
        exec(resolve, reject, 'FirebaseAdmobPlugin', 'requestInterstitial', []);
      }
    )
  },
  setApplicationId: function (applicationId) {
    return new Promise(
      function (resolve, reject) {
        exec(resolve, reject, 'FirebaseAdmobPlugin', 'setApplicationId', [applicationId]);
      }
    )
  },
  setInterstitialId: function (adUnitId) {
    return new Promise(
      function(resolve, reject) {
        exec(resolve, reject, 'FirebaseAdmobPlugin', 'setInterstitialId', [adUnitId]);
      }
    )
  },
  showInterstitial: function () {
    return new Promise(
      function (resolve, reject) {
        exec(resolve, reject, 'FirebaseAdmobPlugin', 'showInterstitial', []);
      }
    )
  }
}
