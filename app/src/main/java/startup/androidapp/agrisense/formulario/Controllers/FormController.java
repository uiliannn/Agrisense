package startup.androidapp.agrisense.formulario.Controllers;

import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;

import startup.androidapp.agrisense.formulario.DataAccess.ServerDataAccessListener;
import startup.androidapp.agrisense.formulario.Helpers.GlobalHelper;
import startup.androidapp.agrisense.formulario.Models.DataConstant;
import startup.androidapp.agrisense.formulario.Models.DataTask;
import startup.androidapp.agrisense.formulario.Views.FormActivity;
import startup.androidapp.agrisense.formulario.Views.MainActivity;
import okhttp3.Request;

public class FormController {
    private FormActivity activity;
    private GlobalHelper globalHelper;

    public FormController(FormActivity act) {
        activity = act;
        this.globalHelper = new GlobalHelper(activity);
    }

    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.activity.startActivityForResult(Intent.createChooser(intent, "Select File"), DataConstant.SELECT_FILE);
    }

    private void toActivity(Class<?> actCls) {
        Intent intent = new Intent(activity, actCls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    public void doSave() {
        String state = activity.state;
        DataTask data = new DataTask();
        data.setId(activity.task_id);
        data.setTitle(activity.txtTitle.getText().toString());
        data.setDescription(activity.txtDescription.getText().toString());
        data.setCreatedDate(activity.txtCreatedDate.getText().toString());
        data.setFileDocumentation(activity.file_documentation);

        final AlertDialog dialog = activity.getGlobalHelper().getUIHelper().getSpotsDialog(this.activity);
        dialog.show();

        globalHelper.getServerDataAccess().doSave(state, data, new ServerDataAccessListener() {
            @Override
            public void onFailure(Request request, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        globalHelper.getUIHelper().ShowAlertDialogNotification("Erro de conexão", "por favor tente novamente", "OK");
                    }
                });
            }

            @Override
            public void onResponseSuccess(final String response) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.isEmpty() && response != null) {
                            Toast.makeText(activity, "Processado com sucesso", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            toActivity(MainActivity.class);
                        }
                        else {
                            globalHelper.getUIHelper().ShowAlertDialogNotification("Falha", "Erro ao processar a solicitação", "OK");
                            dialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onResponseError(final String response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        globalHelper.getUIHelper().ShowAlertDialogNotification("Failed", "Process Failed", "OK");
                    }
                });
            }
        });
    }

    public void doDelete() {
        String task_id = activity.task_id;

        final AlertDialog dialog = activity.getGlobalHelper().getUIHelper().getSpotsDialog(this.activity);
        dialog.show();

        globalHelper.getServerDataAccess().doDelete(task_id, new ServerDataAccessListener() {
            @Override
            public void onFailure(Request request, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        globalHelper.getUIHelper().ShowAlertDialogNotification("Connection Error", "Connection error please try again.", "OK");
                    }
                });
            }

            @Override
            public void onResponseSuccess(final String response) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.isEmpty() && response != null) {
                            Toast.makeText(activity, "Process successful", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            toActivity(MainActivity.class);
                        }
                        else {
                            globalHelper.getUIHelper().ShowAlertDialogNotification("Failed", "Process Failed", "OK");
                            dialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onResponseError(final String response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        globalHelper.getUIHelper().ShowAlertDialogNotification("Failed", "Process Failed", "OK");
                    }
                });
            }
        });
    }
}
