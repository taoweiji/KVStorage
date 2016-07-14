package cn.taoweiji.aptpreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.taoweiji.apt.Test;

@Test("haha")
public class MainActivity extends AppCompatActivity {

    Settings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = SettingsPreference2.get(this,"name");
        settings.getFirstUse();
    }
}
