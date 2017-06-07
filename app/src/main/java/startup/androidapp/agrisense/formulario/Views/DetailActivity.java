package startup.androidapp.agrisense.formulario.Views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import startup.androidapp.agrisense.formulario.BaseActivity;
import startup.androidapp.agrisense.formulario.Controllers.DetailController;
import startup.androidapp.agrisense.formulario.Libraries.ImageLoader;
import startup.androidapp.agrisense.formulario.Models.DataConstant;
import startup.androidapp.agrisense.formulario.Models.DataTask;
import startup.androidapp.agrisense.formulario.R;

public class DetailActivity extends BaseActivity implements ImageLoader.Listener{

    private DetailController controller;
    private TextView txtTitle, txtDescription, txtCreatedDate;
    private ImageView imgDocumentation;
    private DataTask dataTask;
    private Bundle data;
    public String title, description, created_date, task_id, file_documentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        controller = new DetailController(this);
        initUI();
        initAttribute();
        initValue();
    }

    private void initUI() {
        this.setContentView(R.layout.activity_detail);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtCreatedDate = (TextView) findViewById(R.id.txtCreatedDate);
        imgDocumentation = (ImageView) findViewById(R.id.imgDocumentation);
    }

    private void initValue() {
        data = getIntent().getExtras();
        title = data.getString("title");
        description = data.getString("description");
        created_date = data.getString("created_date");
        file_documentation = data.getString("file_documentation");
        task_id = data.getString("task_id");
        txtTitle.setText(title);
        txtDescription.setText(description);
        txtCreatedDate.setText(created_date);
        new ImageLoader(this).execute(DataConstant.ASSET_URL + file_documentation);
    }

    private void initAttribute() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        else if (id == R.id.action_do) {
            controller.toActivity(FormActivity.class);
        }
        else if (id == R.id.action_share) {
            controller.share();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        imgDocumentation.setImageBitmap(bitmap);
    }

    @Override
    public void onError() {
        getGlobalHelper().getUIHelper().showToast(getApplicationContext(), "Erro ao carregar a imagem");
    }


}
