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
  setApplicationId: function (appId) {
    return new Promise(
      function (resolve, reject) {
        exec(resolve, reject, 'FirebaseAdmobPlugin', 'setAdmobAppId', [appId]);
      }
    )
  },
  setInterstitialId: function (unitId) {
    return new Promise(
      function(resolve, reject) {
        exec(resolve, reject, 'FirebaseAdmobPlugin', 'setInterstitialId', [unitId]);
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
