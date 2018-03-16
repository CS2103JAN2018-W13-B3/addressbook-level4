package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    //TODO: Change the file path latter
    private String deskBoardFilePath = "data/deskboard.xml";
    private String deskBoardName = "MyDeskBoard";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getDeskBoardFilePath() {
        return deskBoardFilePath;
    }

    public void setDeskBoardFilePath(String deskBoardFilePath) {
        this.deskBoardFilePath = deskBoardFilePath;
    }

    public String getDeskBoardName() {
        return deskBoardName;
    }

    public void setDeskBoardName(String deskBoardName) {
        this.deskBoardName = deskBoardName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(deskBoardFilePath, o.deskBoardFilePath)
                && Objects.equals(deskBoardName, o.deskBoardName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, deskBoardFilePath, deskBoardName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + deskBoardFilePath);
        sb.append("\nDeskBoard name : " + deskBoardName);
        return sb.toString();
    }

}
