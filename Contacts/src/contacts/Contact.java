package contacts;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Contact {
    private String phoneNumber;
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    public Contact(String phoneNumber) {
        setPhoneNumber(phoneNumber);
    }

    public abstract void edit();

    public abstract void showInformation();

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (checkNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Wrong number format!");
            this.phoneNumber = "";
        }
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    private boolean checkNumber(String phoneNumber) {
        String mainPattern = "(\\+?)(((\\w+)((\\s|-)(\\w{2,}))*)|" +
                "(\\(\\w+\\)((\\s|-)(\\w{2,}))*)|" +
                "(\\w+(\\s|-)\\((\\w{2,})\\)((\\s|-)(\\w{2,}))*))";

        Pattern pattern = Pattern.compile(mainPattern, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public abstract String searchString();

    @Override
    public abstract String toString();

}
