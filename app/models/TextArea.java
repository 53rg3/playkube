package models;

public class TextArea {

    public String text;

    @Override
    public String toString() {
        return "Config{" +
                "text='" + text + '\'' +
                '}';
    }

    public void setText(String text) {
        this.text = text;
    }
}
