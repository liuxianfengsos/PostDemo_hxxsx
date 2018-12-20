package asynchttp.net.postdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

public class Success extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        Intent i = getIntent();//获取Intent
        Bundle bundle = i.getExtras();//获取绑定信息
        String msg = bundle.get("msg").toString();
        EditText editText = findViewById(R.id.editText);
        editText.setText(msg);
    }
}
