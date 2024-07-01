package pl.belka.ecommerce.payu;

public class PayUCredentials {
    private final String clientSecret;
    private final String clientId;
    private boolean sandbox;

    public PayUCredentials(String clientId, String clientSecret, boolean sandbox){
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.sandbox = sandbox;
    }

    public static PayUCredentials sandbox(String clientId, String clientSecret) {
        return new PayUCredentials(clientId, clientSecret, true);
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public String getBaseUrl(){
        if (sandbox) {
            return "https://secure.snd.payu.com";
        } else {
            return "https://secure.payu.com";
        }
    }
}