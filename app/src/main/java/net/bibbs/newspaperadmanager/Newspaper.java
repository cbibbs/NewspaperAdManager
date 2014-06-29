package net.bibbs.newspaperadmanager;

/**
 * Created by Christopher on 6/28/2014.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Newspaper {
    private String newspaperName;
    private String id;

    public String getNewspaperName() {
        return newspaperName;
    }

    public String getId() {
        return id;
    }
}
