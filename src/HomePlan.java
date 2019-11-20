import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class HomePlan extends Plan {
    static final String inputTag = "HOME_PLAN";
    private RangeCriterion homeValueCriterion = new RangeCriterion();
    private RangeCriterion homeAgeCriterion = new RangeCriterion();

    HomePlan(HashMap<String, Tag> tags) {
        super(tags);

        if (tags.get("HOME.VALUE") != null) {
            homeValueCriterion.addCriterion(tags.get("HOME.VALUE"));
        }
        if (tags.get("HOME.AGE") != null) {
            homeAgeCriterion.addCriterion(tags.get("HOME.AGE"));
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
