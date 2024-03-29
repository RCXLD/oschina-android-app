package cn.gov.psxq.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import cn.gov.psxq.AppConfig;
import cn.gov.psxq.AppContext;
import cn.gov.psxq.AppException;
import cn.gov.psxq.R;
import cn.gov.psxq.bean.Post;
import cn.gov.psxq.bean.Result;
import cn.gov.psxq.common.StringUtils;
import cn.gov.psxq.common.UIHelper;

/**
 * 发表帖子
 * @author liux (http://my.oschina.net/liux)
 * @version 1.1
 * @created 2014-7-11
 */
public class QuestionPub extends BaseActionBarActivity {

    private EditText           mTitle;
    private EditText           mContent;
    private Spinner            mCatalog;
    private CheckBox           mEmail;
    private ProgressDialog     mProgress;
    private Post               post;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_pub);

        this.initView();

    }

    //初始化视图控件
    private void initView() {
        getSupportActionBar().setTitle(R.string.question_pub_head_title);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        mTitle = (EditText) findViewById(R.id.question_pub_title);
        mContent = (EditText) findViewById(R.id.question_pub_content);
        mEmail = (CheckBox) findViewById(R.id.question_pub_email);
        mCatalog = (Spinner) findViewById(R.id.question_pub_catalog);

        mCatalog.setOnItemSelectedListener(catalogSelectedListener);
        //编辑器添加文本监听
        mTitle.addTextChangedListener(UIHelper.getTextWatcher(this, AppConfig.TEMP_POST_TITLE));
        mContent.addTextChangedListener(UIHelper.getTextWatcher(this, AppConfig.TEMP_POST_CONTENT));

        //显示临时选择分类
        String position = ((AppContext) getApplication()).getProperty(AppConfig.TEMP_POST_CATALOG);
        mCatalog.setSelection(StringUtils.toInt(position, 0));
    }

    private AdapterView.OnItemSelectedListener catalogSelectedListener = new AdapterView.OnItemSelectedListener() {
                                                                           public void onItemSelected(AdapterView<?> parent,
                                                                                                      View view,
                                                                                                      int position,
                                                                                                      long id) {
                                                                               //保存临时选择的分类
                                                                               ((AppContext) getApplication())
                                                                                   .setProperty(
                                                                                       AppConfig.TEMP_POST_CATALOG,
                                                                                       position
                                                                                               + "");
                                                                           }

                                                                           public void onNothingSelected(AdapterView<?> parent) {
                                                                           }
                                                                       };

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_actionbar_pub, menu);
        return true;
    }

    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch (item.getItemId()) {
            case R.id.actionbar_pub:
                publish();
                return true;

            default:
                return false;
        }
    }

    private void publish() {
        //隐藏软键盘
        imm.hideSoftInputFromWindow(mContent.getWindowToken(), 0);

        String title = mTitle.getText().toString();
        if (StringUtils.isEmpty(title)) {
            UIHelper.ToastMessage(QuestionPub.this, "请输入标题");
            return;
        }
        String content = mContent.getText().toString();
        if (StringUtils.isEmpty(content)) {
            UIHelper.ToastMessage(QuestionPub.this, "请输入提问内容");
            return;
        }

        final AppContext ac = (AppContext) getApplication();
        if (!ac.isLogin()) {
            UIHelper.showLoginDialog(getSupportFragmentManager());
            return;
        }

        mProgress = ProgressDialog.show(QuestionPub.this, null, "发布中···", true, true);

        post = new Post();
        post.setAuthorId(ac.getLoginUid());
        post.setTitle(title);
        post.setBody(content);
        post.setCatalog(mCatalog.getSelectedItemPosition() + 1);
        if (mEmail.isChecked())
            post.setIsNoticeMe(1);

        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (mProgress != null)
                    mProgress.dismiss();
                if (msg.what == 1) {
                    Result res = (Result) msg.obj;
                    UIHelper.ToastMessage(QuestionPub.this, res.getErrorMessage());
                    if (res.OK()) {
                        //发送通知广播
                        if (res.getNotice() != null) {
                            UIHelper.sendBroadCast(QuestionPub.this, res.getNotice());
                        }
                        //清除之前保存的编辑内容
                        ac.removeProperty(AppConfig.TEMP_POST_TITLE, AppConfig.TEMP_POST_CATALOG,
                            AppConfig.TEMP_POST_CONTENT);
                        //跳转到文章详情
                        finish();
                    }
                } else {
                    ((AppException) msg.obj).makeToast(QuestionPub.this);
                }
            }
        };
        new Thread() {
            public void run() {
                Message msg = new Message();
                try {
                    Result res = ac.pubPost(post);
                    msg.what = 1;
                    msg.obj = res;
                } catch (AppException e) {
                    e.printStackTrace();
                    msg.what = -1;
                    msg.obj = e;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }
}
