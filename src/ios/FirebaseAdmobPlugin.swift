import Firebase
import GoogleMobileAds

var testDevices: [Any] = [kGADSimulatorID]

@objc(FirebaseAdmobPlugin)
class FirebaseAdmobPlugin : CDVPlugin {

  var applicationId: String = "ca-app-pub-3940256099942544~1458002511"
  var interstitial: GADInterstitial!
  var interstitialUnitId: String = "ca-app-pub-3940256099942544/4411468910"

  @objc(addTestDevice:)
  func addTestDevice(command: CDVInvokedUrlCommand) {
      DispatchQueue.global(qos: .userInitiated).async {
          let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK)
          let testDeviceId = command.arguments[0] as? String ?? ""

          if !testDeviceId.isEmpty {
              testDevices.append(testDeviceId)
          }

          self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
      }
  }

  @objc(getTestDevices:)
  func getTestDevices(command: CDVInvokedUrlCommand) {
    DispatchQueue.global(qos: .userInitiated).async {
      self.commandDelegate.send(
        CDVPluginResult(status: CDVCommandStatus_OK, messageAs: testDevices),
        callbackId: command.callbackId
      )
    }
  }

  @objc(requestInterstitial)
  func requestInterstitial() {
      DispatchQueue.global(qos: .userInitiated).async {
          self.interstitial = GADInterstitial(adUnitID: self.interstitialUnitId)

          let request = GADRequest()

          request.testDevices = testDevices

          self.interstitial.load(request)
      }
  }

  @objc(setAdmobAppId:)
  func setAdmobAppId(command: CDVInvokedUrlCommand) {
      DispatchQueue.global(qos: .userInitiated).async {
          let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK)

          self.applicationId = command.arguments[0] as? String ?? "ca-app-pub-3940256099942544~1458002511"

          DispatchQueue.main.async {
              GADMobileAds.configure(withApplicationID: self.applicationId)

              self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
          }
      }
  }

  @objc(setInterstitialId:)
  func setInterstitialId(command: CDVInvokedUrlCommand) {
      DispatchQueue.global(qos: .userInitiated).async {
        let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK)

          self.interstitialUnitId = command.arguments[0] as? String ?? "ca-app-pub-3940256099942544/4411468910"

          self.requestInterstitial()

          self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
      }
  }

  @objc(showInterstitial:)
  func showInterstitial(command: CDVInvokedUrlCommand) {
      DispatchQueue.global(qos: .userInitiated).async {
          var pluginResult = CDVPluginResult(status: CDVCommandStatus_OK)

          DispatchQueue.main.async {
              if self.interstitial != nil && self.interstitial.isReady {
                  self.interstitial.present(fromRootViewController: self.viewController)
              } else {
                  pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR)
              }

              self.requestInterstitial()

              self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
          }
      }
  }
}
