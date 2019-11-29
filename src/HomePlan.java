import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class HomePlan extends Plan {
    static final String inputTag = "HOME_PLAN";
    private RangeCriterion homeValueCriterion = new RangeCriterion();
    private RangeCriterion homeAgeCriterion = new RangeCriterion();

    HomePlan(HashMap<String, ArrayList<Tag>> tags) {
        super(tags);

        ArrayList<Tag> homeValueTags = tags.get("HOME.VALUE");
        if (homeValueTags != null) {
            int homeValueSize = homeValueTags.size();
            customerAgeCriterion.addCriterion(homeValueTags.get(homeValueSize-1));
            if (homeValueSize > 1) {
                customerAgeCriterion.addCriterion(homeValueTags.get(homeValueSize-2));
            }
        }

        ArrayList<Tag> homeAgeTags = tags.get("HOME.AGE");
        if (homeAgeTags != null) {
            int homeAgeSize = homeAgeTags.size();
            customerAgeCriterion.addCriterion(homeAgeTags.get(homeAgeSize-1));
            if (homeAgeSize > 1) {
                customerAgeCriterion.addCriterion(homeAgeTags.get(homeAgeSize-2));
            }
        }
    }

    @Override
    boolean isEligible(Insurable insurable, Date date) {
        if (!(insurable instanceof Home))
            return false;
        Home home = (Home) insurable;
        if (!homeValueCriterion.isInRange(home.getValue()))
            return false;

        // Extracting the age of the home
        LocalDate localCurrentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localBuiltDate = home.getBuildDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long age = localCurrentDate.getYear() - localBuiltDate.getYear();;
        // Checking if the age is in the range.
        return homeAgeCriterion.isInRange(age);
    }

    @Override
    ArrayList<? extends Insurable> getInsuredItems(Customer customer, Database database) {
        return database.getHomesByOwnerName(customer.getName());
    }

    @Override
    Insurable getInsuredItem(String insurableID, Database database) {
        return database.getHomeByPostalCode(insurableID);
    }
}
