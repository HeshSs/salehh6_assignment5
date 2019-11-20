import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

class Customer {
    private String name;
    private Date dateOfBirth;
    private long income;

    static final String inputTag = "CUSTOMER";

    Customer(HashMap<String, Tag> tags) throws ParseException {
        name = tags.get("NAME").getValue();
        dateOfBirth = Utils.convertDate(tags.get("DATE_OF_BIRTH").getValue());
        income = Long.parseLong(tags.get("INCOME").getValue());
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public long getIncome() {
        return income;
    }

    public static String getInputTag() {
        return inputTag;
    }
}
