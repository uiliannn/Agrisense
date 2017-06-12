package startup.androidapp.agrisense.formulario.Controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

import startup.androidapp.agrisense.formulario.Helpers.GlobalHelper;
import startup.androidapp.agrisense.formulario.Libraries.ImageLoader;
import startup.androidapp.agrisense.formulario.Models.DataConstant;
import startup.androidapp.agrisense.formulario.Views.DetailActivity;

import static startup.androidapp.agrisense.formulario.Models.DataConstant.ASSET_URL;

public class DetailController {
    private DetailActivity activity;
    private GlobalHelper globalHelper;

    public DetailController(DetailActivity act) {
        activity = act;
        globalHelper = new GlobalHelper(activity);
    }

    public void toActivity(Class<?> actCls) {
        Intent intent = new Intent(activity, actCls);
        intent.putExtra("state", "edit");
        intent.putExtra("title", activity.title);
        intent.putExtra("description", activity.description);
        intent.putExtra("created_date", activity.created_date);
        intent.putExtra("task_id", activity.task_id);
        activity.startActivity(intent);
    }
    public void share() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Titulo: "+activity.title+",   Descrição: "+activity.description+", Link da imagem: "+ASSET_URL+activity.file_documentation);
        shareIntent.setType("text/plain");
        shareIntent.setPackage("com.whatsapp");
        activity.startActivity(shareIntent);





    }
}
