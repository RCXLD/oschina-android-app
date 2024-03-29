package cn.gov.psxq.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import cn.gov.psxq.R;

/**
 * 关于我们
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class About extends BaseActionBarActivity {

    private TextView mVersion;
    private Button   mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about);
        //getSupportActionBar().hide();

        //获取客户端版本信息
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            mVersion = (TextView) findViewById(R.id.about_version);
            mVersion.setText("版本：" + info.versionName);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }

        TextView text = (TextView) findViewById(R.id.about_text);
        text.setMovementMethod(LinkMovementMethod.getInstance());
        text.setText(Html
            .fromHtml("本应用由开源中国Android客户端的开源代码基础上修改而来，本应用源代码获取请"
                      + "<a href=\"http://git.oschina.net/tar/android-app.git\">点击链接</a>"));

        mUpdate = (Button) findViewById(R.id.about_update);
        mUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //UpdateManager.getUpdateManager().checkAppUpdate(About.this, true);
            }
        });
    }
}
