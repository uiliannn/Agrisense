package startup.androidapp.agrisense.formulario.Controllers;

import android.content.Intent;

import startup.androidapp.agrisense.formulario.Helpers.GlobalHelper;
import startup.androidapp.agrisense.formulario.Views.DetailActivity;

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
}
