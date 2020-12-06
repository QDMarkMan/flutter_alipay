import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class AlipayResult {
  ///
  final String memo;

  /// 支付后结果
  final String result;

  /// 支付状态，参考支付宝的文档https://docs.open.alipay.com/204/105695/
  /// 返回码，标识支付状态，含义如下：
  /// 9000——订单支付成功         上面的result有值
  /// 8000——正在处理中
  /// 4000——订单支付失败
  /// 5000——重复请求
  /// 6001——用户中途取消
  /// 6002——网络连接出错
  final String resultStatus;

  AlipayResult({this.memo, this.result, this.resultStatus});

  @override
  String toString() {
    return "{meno: $memo, resultStatus:$resultStatus, result:$result}";
  }
}

class FlutterAlipay {
  static const MethodChannel _channel = const MethodChannel('flutter_alipay');

  static Future<AlipayResult> pay(String payInfo,
      {bool sandbox = false}) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'payInfo': payInfo,
      'sandbox': sandbox
    };
    var res = await _channel.invokeMethod('pay', params);
    return new AlipayResult(
        result: res['result'],
        resultStatus: res['resultStatus'],
        memo: res['memo']);
  }

  /// 判断是否安装了支付宝
  static Future<bool> isInstalled() async {
    var res = await _channel.invokeMethod('isInstalled');
    return res['result'];
  }

  static Future setIosUrlSchema(String urlSchema) async {
    if (Platform.isIOS) {
      await _channel.invokeMethod('setIosUrlSchema', {"schema": urlSchema});
    }
  }
}
