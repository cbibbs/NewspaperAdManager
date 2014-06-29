package net.bibbs.newspaperadmanager;

/**
 * Created by Christopher on 6/28/2014.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Advertisement {
    private String advertisementName;
    private String advertisementDescription;
    private String id;

    public String getAdvertisementName() {
        return advertisementName;
    }

    public String getAdvertisementDescription(){
        return advertisementDescription;
    }

    public String getId() {
        return id;
    }

    public void setAdvertisementName(String advertisementName) {
        this.advertisementName = advertisementName;
    }

    public void setAdvertisementDescription(String advertisementDescription) {
        this.advertisementDescription = advertisementDescription;
    }
}
