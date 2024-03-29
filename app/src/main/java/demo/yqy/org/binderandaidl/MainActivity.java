package demo.yqy.org.binderandaidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import itboy.mylibrary.IMyAidlInterface;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("test.aidl.service");

                /**
                 * 主要这个package不是Service所在的报名
                 */
                intent.setPackage("demo.yqy.org.binderandaidl.service");

                boolean succ = bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                Toast.makeText(getApplicationContext(), "开始绑定 " + succ, Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.btn_unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
            }
        });


    }

    private IMyAidlInterface stub;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            stub = IMyAidlInterface.Stub.asInterface(service);

            try {
                final String info = stub.getString();
                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            stub = null;
        }
    };
}
