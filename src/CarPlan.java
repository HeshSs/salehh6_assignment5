import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class CarPlan extends Plan {
    static final String inputTag = "CAR_PLAN";
    RangeCriterion mileageCriterion = new RangeCriterion();

    CarPlan(HashMap<String, ArrayList<Tag>> tags) {
        super(tags);

        ArrayList<Tag> carMileageTags = tags.get("CAR.MILEAGE");
        if (carMileageTags != null) {
            int carMileageSize = carMileageTags.size();
            mileageCriterion.addCriterion(carMileageTags.get(carMileageSize-1));
            if (carMileageSize > 1) {
                mileageCriterion.addCriterion(carMileageTags.get(carMileageSize-2));
            }
        }

        // If the customer applies for car plan, check if their wealth is in range - Added for Assignment 5 Part 2
        ArrayList<Tag> customerWealthTags = tags.get("CUSTOMER.WEALTH");
        if (customerWealthTags != null) {
            int customerWealthSize = customerWealthTags.size();
            customerWealthCriterion.addCriterion(customerWealthTags.get(customerWealthSize-1));
            if (customerWealthSize > 1) {
                customerWealthCriterion.addCriterion(customerWealthTags.get(customerWealthSize-2));
            }
        }
    }

    @Override
    boolean isEligible(Insurable insurable, Date date) {
        if (!(insurable instanceof Car))
            return false;
        Car car = (Car) insurable;
        return mileageCriterion.isInRange(car.getMileage());
    }

    @Override
    ArrayList<? extends Insurable> getInsuredItems(Customer customer, Database database) {
        return database.getCarsByOwnerName(customer.getName());
    }

    @Override
    Insurable getInsuredItem(String insurableID, Database database) {
        return database.getCarByPlateNumber(insurableID);
    }

}
