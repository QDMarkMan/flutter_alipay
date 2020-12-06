package org.zoomdev.flutter.alipay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.app.EnvUtils;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import java.util.HashMap;
import java.util.Map;


/**
 * FlutterAlipayPlugin
 */
public class FlutterAlipayPlugin implements MethodCallHandler {

  private Registrar _registrar;


  public FlutterAlipayPlugin(Registrar registrar){
    _registrar = registrar;
  }

  /**
   * Plugin registration.
   */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_alipay");
    channel.setMethodCallHandler(new FlutterAlipayPlugin(registrar));

  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {

    if (call.method.equals("pay")) {
      String payInfo = call.argument("payInfo");
      boolean sandbox = call.argument("sandbox");
      pay(_registrar.activity() ,payInfo,result , sandbox);
    } else {
      result.notImplemented();
    }
  }


  public static void pay(final Activity currentActivity, final String payInfo, final Result callback, final boolean sandBox){


    if (sandBox) {
      EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
    }

    new AsyncTask<String,Object,Map<String,String>>(){

      @Override
      protected Map<String, String> doInBackground(String... strings) {
//
        try{
          PayTask alipay = new PayTask(currentActivity);
          Map<String, String> result = alipay.payV2(payInfo, true);
          return result;
        }catch (Exception e){
          Map<String,String> result = new HashMap<>();
          result.put("$error",e.getMessage());

          return result;
        }
      }

      @Override
      protected void onPostExecute(Map<String, String> result) {
        String error = result.get("$error");
        if(error!=null){
          callback.error(error,"支付发生错误",null);
        }else{
          callback.success(result);
        }

      }
    }.execute();


//    Thread payThread = new Thread(payRunnable);
//    payThread.start();
  }
}
