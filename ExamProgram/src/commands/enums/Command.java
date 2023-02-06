package commands.enums;

public enum Command {

    ADD_NEW("1", "Add new"),
    SHOW("2", "Show data"),
    DELETE("3", "Delete data"),
    UPDATE("4", "Update data"),

    EXIT("0", "Exit");

    public final String key;
    public final String title;

    Command(String key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("[%s] - %s", this.key, this.title);
    }
}
