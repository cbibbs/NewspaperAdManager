package net.bibbs.newspaperadmanager;

/**
 * Created by Christopher on 6/28/2014.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Newspaper {
    private String newspaperName;
    private String id;

    public Newspaper() {
    }

    public Newspaper(String newspaperName, String id) {
        this.newspaperName = newspaperName;
        this.id = id;
    }

    public String getNewspaperName() {
        return newspaperName;
    }

    public String getId() {
        return id;
    }

    public void setNewspaperName(String name) {
        this.newspaperName = name;
    }

    @Override
    public String toString() {
        return "[ name=" + newspaperName + ", ID=" + id + "]";
    }
}
