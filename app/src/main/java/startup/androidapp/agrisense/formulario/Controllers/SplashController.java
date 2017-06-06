package startup.androidapp.agrisense.formulario.Controllers;

import android.content.Intent;

import startup.androidapp.agrisense.formulario.Helpers.GlobalHelper;
import startup.androidapp.agrisense.formulario.Views.MainActivity;
import startup.androidapp.agrisense.formulario.Views.SplashActivity;

public class SplashController {
    private SplashActivity splashActivity;
    private GlobalHelper globalHelper;

    public SplashController(SplashActivity activity) {
        this.splashActivity = activity;
        this.globalHelper = new GlobalHelper(activity);
    }

    public void doStartApplication() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    toActivity(MainActivity.class);
                }
            }
        };
        timerThread.start();
    }

    private void toActivity(Class<?> actCls) {
        Intent intent = new Intent(splashActivity, actCls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        splashActivity.startActivity(intent);
        splashActivity.finish();
    }
}
