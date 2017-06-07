package startup.androidapp.agrisense.formulario.Controllers;

import android.content.Intent;
import startup.androidapp.agrisense.formulario.Helpers.GlobalHelper;
import startup.androidapp.agrisense.formulario.Models.DataTask;
import startup.androidapp.agrisense.formulario.Views.MainActivity;

import static startup.androidapp.agrisense.formulario.Models.DataConstant.ASSET_URL;

public class MainController {
    private MainActivity activity;
    private GlobalHelper globalHelper;

    public MainController(MainActivity act) {
        activity = act;
        globalHelper = new GlobalHelper(act);
    }

    public void toActivity(Class<?> actCls, DataTask data) {
        Intent intent = new Intent(activity, actCls);
        intent.putExtra("state", "add");
        intent.putExtra("title", data.getTitle());
        intent.putExtra("description", data.getDescription());
        intent.putExtra("created_date", data.getCreatedDate());
        intent.putExtra("file_documentation", data.getFileDocumentation());
        intent.putExtra("task_id", data.getId());
        activity.startActivity(intent);
    }

    public void share() {
        Intent shareIntent = new Intent();

        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Baixe agora o App da Agrisense em: http://ws.agrisense.technology/assets/agrisense.apk");
        shareIntent.setType("text/plain");
        //shareIntent.setPackage("com.whatsapp");
        activity.startActivity(shareIntent);
    }
}
