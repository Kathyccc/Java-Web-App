package ds.project1task2;

/*
This is a class for Json Object and store the attributes
 */
public class countryInfo {
    public countryInfo(String name, String code, String unicode, String image, String emoji) {
        this.name = name;
        this.code = code;
        this.unicode = unicode;
        this.image = image;
        this.emoji = emoji;
    }

    private String name;
    private String code;
    private String unicode;
    private String image;

    private String emoji;

    public String getCode() {
        return code;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
