package ApexEngine.Assets;

import java.io.InputStream;

public class LoadedAsset {
    private InputStream data;
    private String filePath;

    public InputStream getData() {
        return data;
    }

    public void setData(InputStream value) {
        data = value;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String value) {
        filePath = value;
    }

    public LoadedAsset(InputStream data, String filePath) {
        this.data = data;
        this.filePath = filePath;
    }

}


