import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains information about a company e.g. the owner, company name and value, and it's setters and getters.
 * Added for Assignment 5 Part 2
 */
class Company {

    private String ownerName;
    private String companyName;
    private long value;

    static final String inputTag = "COMPANY";

    Company(HashMap<String, ArrayList<Tag>> tags) throws ParseException {
        ownerName = tags.get("OWNER_NAME").get(0).getValue();
        companyName = tags.get("COMPANY_NAME").get(0).getValue();
        value = Long.parseLong(tags.get("VALUE").get(0).getValue());
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public long getValue() { return value; }
}