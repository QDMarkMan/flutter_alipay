# flutter_alipay

A flutter plugin to use alipay.

从[flutter_alipay](https://github.com/best-flutter/flutter_alipay)fork, 增加沙箱功能，如果要使用请下载到本地进行调试使用。


## 功能列表

* 调用支付


## 安装

增加依赖 pubspec.yaml
```
dependencies:
  flutter_alipay: "^1.0.0"
```

## 开始

* ios集成



  + 在info.plist增加一条URL scheme

```
    <key>CFBundleURLTypes</key>
    <array>
        <dict>
            <key>CFBundleTypeRole</key>
            <string>Editor</string>
            <key>CFBundleURLSchemes</key>
            <array>
                <string>org.zoomdev.flutter.alipay</string>
            </array>
        </dict>
    </array>
```
  如果需要定制URL scheme可以这么做：

  + 或者

```
 <key>CFBundleURLTypes</key>
    <array>
        <dict>
            <key>CFBundleTypeRole</key>
            <string>Editor</string>
            <key>CFBundleURLSchemes</key>
            <array>
                <string>__YOUR APP SCHEME NAME__</string>
            </array>
        </dict>
    </array>
```

然后在app中调用

```
await FlutterAlipay.setIosUrlSchema('YOUR APP SCHEME NAME');
```



 ## 使用
```
import 'package:flutter_alipay/flutter_alipay.dart';
```


* 调取支付

```dart
// var result = await FlutterAlipay.pay("you pay info from server");
class PayConstants {
  static const String ALIPAY_SUCCESS = "9000";
  static const String ALIPAY_CANCLE = "6001";
  static const String ALIPAY_ERROR = "4000";
  static const String ALIPAY_NET_ERROR = "6002";
}

void aliPay(String sign) async {
    if (sign == null || sign.length == 0) {
      return;
    }
    FlutterAlipay.pay(sign, sandbox: true).then((payResult) {
      var _payResult = payResult;
      print('>>>>>  ${_payResult.toString()}');
      String payResultStatus = _payResult.resultStatus;
      print(payResultStatus);
    }).catchError((e) {
      // todo
    });
  }
```


