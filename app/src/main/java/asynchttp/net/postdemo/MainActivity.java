package asynchttp.net.postdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.callumtaylor.asynchttp.AsyncHttpClient;
import net.callumtaylor.asynchttp.obj.NameValuePair;
import net.callumtaylor.asynchttp.response.JsonResponseHandler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends Activity {
    EditText e_username;
    EditText e_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void postClick(View view) {
        Log.v("postClick走到这个方法了","postClick走到这个方法了");
        e_username = findViewById(R.id.username);
        e_password = findViewById(R.id.password);

        String username = e_username.getText().toString().trim();
        String password = e_password.getText().toString().trim();

        Log.v("log1",username+"--"+password);
//开始准备访问服务器
        AsyncHttpClient client = new AsyncHttpClient("http://192.168.25.1:8080");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new NameValuePair("username", username));
        params.add(new NameValuePair("password", password));

        Headers headers = Headers.of("Header", "");

        client.post("testPost/", params, headers, new JsonResponseHandler()
        {
            @Override public void onSuccess()
            {
                JsonElement result = getContent();
                JsonObject jsonObject = result.getAsJsonObject();
                String msg = jsonObject.get("msg").getAsString();
                Log.v("msg1",msg);
               // msg = msg.substring(1,msg.length()-1);
                if("loginsuccess".equals(msg)){
                    Intent i = new Intent(MainActivity.this,Success.class);
                    i.putExtra("msg",msg);
                    startActivity(i);
                }else {
                   // midToast("用户名密码错误",2000);
                    Looper.prepare();
                    Toast.makeText(MainActivity.this,"用户名密码错误",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
    //包装土司方法   我没使用哦我没使
    void midToast(String str, int showTime) {
        Toast toast = Toast.makeText(MainActivity.this, str, showTime);
        //设置显示位置
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        //设置字体颜色
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.YELLOW);
        toast.show();
    }

}
