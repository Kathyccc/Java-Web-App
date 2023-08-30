/*
This is a class for Json Object and store the attributes
 */
public class Emoji {
    private String name;
    private String code;
    private String unicode;
    private String image;

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
}
