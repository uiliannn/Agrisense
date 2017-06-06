package startup.androidapp.agrisense.formulario.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataTask {

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        id = _id;
    }


    @SerializedName("title")
    @Expose
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String _title) {
        title = _title;
    }


    @SerializedName("description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        description = _description;
    }


    @SerializedName("created_date")
    @Expose
    private String created_date;

    public String getCreatedDate() {
        return created_date;
    }

    public void setCreatedDate(String _created_date) {
        created_date = _created_date;
    }


    @SerializedName("file_documentation")
    @Expose
    private String file_documentation;

    public String getFileDocumentation() {
        return file_documentation;
    }

    public void setFileDocumentation(String _file_documentation) {
        file_documentation = _file_documentation;
    }

}
