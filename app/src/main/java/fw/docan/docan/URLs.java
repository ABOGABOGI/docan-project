package fw.docan.docan;

/**
 * Created by Faishal Wahiduddin on 3/13/2018.
 */

public class URLs {

    //private static final String ROOT_URL = "http://docan.faishal.id/laravel/public/api/";

    private static final String ROOT_URL = "https://docan.id/api/";

    public static final String URL_REGISTER = ROOT_URL + "register";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String URL_LOG= ROOT_URL + "create-log";
    public static final String URL_SEND_BALANCE= ROOT_URL + "send-balance";
    public static final String URL_CHECK_BALANCE= ROOT_URL + "check-balance";

    public static final String URL_PROMO = ROOT_URL + "getpromo";
    public static final String URL_TRANSACTION= ROOT_URL + "transactions";
    public static final String URL_SELLER_TRANSACTION= ROOT_URL + "seller-transactions";
    public static final String URL_CREATE_TRANSACTION= ROOT_URL + "create-transaction";
    public static final String URL_CREATE_SELLING_TRANSACTION= ROOT_URL + "create-selling-transaction";
    public static final String URL_GET_TRANSACTION= ROOT_URL + "single-transaction";
    public static final String URL_PAY_TRANSACTION= ROOT_URL + "pay-transaction";
    public static final String URL_GET_TOKEN_CHIP= ROOT_URL + "get-token";
    public static final String URL_DEVICE_INFO = ROOT_URL + "verifikasiperangkat";


    public static final String URL_KIRIM= ROOT_URL + "transfer";
    public static final String URL_KIRIM_COMPLETED= ROOT_URL + "transfer/completed";

    public static final String URL_GASSTRATION= ROOT_URL + "gasstation";
    public static final String URL_GASSTRATION_COMPLETED= ROOT_URL + "gasstation/completed";


    public static final String URL_GET_PRODUCTS= ROOT_URL + "getproduct";
    public static final String URL_CREATE_PRODUCTS= ROOT_URL + "createproduct";

}
