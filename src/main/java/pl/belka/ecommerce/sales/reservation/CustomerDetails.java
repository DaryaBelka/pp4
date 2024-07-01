package pl.belka.ecommerce.sales.reservation;

public class CustomerDetails {
    private final String customerId;
    private final String firstname;
    private final String lastname;
    private final String email;

    public CustomerDetails(String customerId, String firstName, String lastName, String email) {

        this.customerId = customerId;
        this.firstname = firstName;
        this.lastname = lastName;
        this.email = email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }
}