/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 12/2/16
 */

package com.astir_trotter.atcustom.ui.activity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.astir_trotter.atcustom.R;
import com.astir_trotter.atcustom.singleton.MultiLangStringRes;
import com.astir_trotter.atcustom.ui.activity.base.AutoCrashReporter;

// Do not use BaseActivity nor AppCompatActivity
public class CrashReporterActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crashreport);

        setFinishOnTouchOutside(false);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        init();
    }

    private void init() {
        TextView tvTitle = (TextView) findViewById(R.id.cr_title);
        tvTitle.setText(MultiLangStringRes.getInstance().get(R.string.crashreport_title));
        TextView tvDescription = (TextView) findViewById(R.id.cr_description);
        tvDescription.setText(MultiLangStringRes.getInstance().get(R.string.crashreport_description));

        TextView tvCancel = (TextView) findViewById(R.id.cr_cancel);
        tvCancel.setText(MultiLangStringRes.getInstance().get(android.R.string.cancel));
        tvCancel.setOnClickListener(this);
        TextView tvReport = (TextView) findViewById(R.id.cr_ok);
        tvReport.setText(MultiLangStringRes.getInstance().get(android.R.string.ok));
        tvReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cr_ok)
            AutoCrashReporter.get(getApplication()).checkErrorAndSendMail(this);

        finish();
    }
}


