import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;

public class Principal {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/69b13c94abd0a8a0c0aaa376/latest/USD"; // Cambia YOUR_API_KEY por tu clave de API

    private static final String[][] CURRENCIES = {
            {"AED", "Dírham de los Emiratos Árabes Unidos"},
            {"AFN", "Afgano afgano"},
            {"ALL", "Lek albanés"},
            {"AMD", "Dram armenio"},
            {"ANG", "Florín de las Antillas Neerlandesas"},
            {"AOA", "Kwanza angoleño"},
            {"ARS", "Peso argentino"},
            {"AUD", "Dólar australiano"},
            {"AWG", "Florín de Aruba"},
            {"AZN", "Manat azerbaiyano"},
            {"BAM", "Marca de Bosnia y Herzegovina"},
            {"BBD", "Dólar de Barbados"},
            {"BDT", "Taka de Bangladesh"},
            {"BGN", "Lev búlgaro"},
            {"BHD", "Dinar bahreiní"},
            {"BIF", "Franco burundiano"},
            {"BMD", "Dólar de Bermudas"},
            {"BND", "Dólar de Brunei"},
            {"BOB", "Boliviano"},
            {"BRL", "Real brasileño"},
            {"BSD", "Dólar de las Bahamas"},
            {"BTN", "Ngultrum butanés"},
            {"BWP", "Pula de Botsuana"},
            {"BYN", "Rublo bielorruso"},
            {"BZD", "Dólar beliceño"},
            {"CAD", "Dólar canadiense"},
            {"CDF", "Franco congoleño"},
            {"CHF", "Franco suizo"},
            {"CLP", "Peso chileno"},
            {"CNY", "Renminbi chino"},
            {"COP", "Peso colombiano"},
            {"CRC", "Colón costarricense"},
            {"CUP", "Peso cubano"},
            {"CVE", "Escudo de Cabo Verde"},
            {"CZK", "Corona checa"},
            {"DJF", "Franco de Yibuti"},
            {"DKK", "Corona danesa"},
            {"DOP", "Peso dominicano"},
            {"DZD", "Dinar argelino"},
            {"EGP", "Libra egipcia"},
            {"ERN", "Nakfa de Eritrea"},
            {"ETB", "Birr etíope"},
            {"EUR", "Euro"},
            {"FJD", "Dólar de Fiji"},
            {"FKP", "Libra de las Islas Malvinas"},
            {"FOK", "Corona de las Islas Feroe"},
            {"GBP", "Libra esterlina"},
            {"GEL", "Lari georgiano"},
            {"GGP", "Libra de Guernsey"},
            {"GHS", "Cedi de Ghana"},
            {"GIP", "Libra de Gibraltar"},
            {"GMD", "Dalasi de Gambia"},
            {"GNF", "Franco guineano"},
            {"GTQ", "Quetzal guatemalteco"},
            {"GYD", "Dólar guyanés"},
            {"HKD", "Dólar de Hong Kong"},
            {"HNL", "Lempira hondureña"},
            {"HRK", "Kuna croata"},
            {"HTG", "Gourde haitiano"},
            {"HUF", "Florín húngaro"},
            {"IDR", "Rupia indonesia"},
            {"ILS", "Nuevo shekel israelí"},
            {"INR", "Rupia india"},
            {"IQD", "Dinar iraquí"},
            {"IRR", "Rial iraní"},
            {"ISK", "Corona islandesa"},
            {"JMD", "Dólar jamaiquino"},
            {"JPY", "Yen japonés"},
            {"KES", "Chelín keniano"},
            {"KGS", "Som kirguís"},
            {"KHR", "Riel camboyano"},
            {"KMF", "Franco comorense"},
            {"KRW", "Won surcoreano"},
            {"KWD", "Dinar kuwaití"},
            {"KYD", "Dólar de las Islas Caimán"},
            {"KZT", "Tenge kazajo"},
            {"LAK", "Kip lao"},
            {"LBP", "Libra libanesa"},
            {"LKR", "Rupia de Sri Lanka"},
            {"LRD", "Dólar liberiano"},
            {"LSL", "Loti de Lesoto"},
            {"MAD", "Dirham marroquí"},
            {"MDL", "Leu moldavo"},
            {"MGA", "Ariary malgache"},
            {"MKD", "Denar macedonio"},
            {"MMK", "Kyat birmano"},
            {"MNT", "Tögrög mongol"},
            {"MOP", "Pataca de Macao"},
            {"MUR", "Rupia de Mauricio"},
            {"MXN", "Peso mexicano"},
            {"MYR", "Ringgit de Malasia"},
            {"MZN", "Metical mozambiqueño"},
            {"NAD", "Dólar de Namibia"},
            {"NGN", "Naira nigeriana"},
            {"NIO", "Córdoba nicaragüense"},
            {"NOK", "Corona noruega"},
            {"NPR", "Rupia nepalí"},
            {"NZD", "Dólar neozelandés"},
            {"OMR", "Rial omaní"},
            {"PAB", "Balboa panameño"},
            {"PEN", "Sol peruano"},
            {"PHP", "Peso filipino"},
            {"PKR", "Rupia pakistaní"},
            {"PLN", "Zloty polaco"},
            {"PYG", "Guaraní paraguayo"},
            {"QAR", "Rial qatarí"},
            {"RON", "Leu rumano"},
            {"RSD", "Dinar serbio"},
            {"RUB", "Rublo ruso"},
            {"SAR", "Rial saudí"},
            {"SCR", "Rupia de Seychelles"},
            {"SEK", "Corona sueca"},
            {"SGD", "Dólar de Singapur"},
            {"SHP", "Libra de Santa Elena"},
            {"SLL", "Leona de Sierra Leona"},
            {"SOS", "Chelín somalí"},
            {"SRD", "Dólar surinamés"},
            {"SSP", "Libra de Sudán del Sur"},
            {"SYP", "Libra siria"},
            {"SZL", "Lilangeni de Eswatini"},
            {"THB", "Baht tailandés"},
            {"TJS", "Somoni tayiko"},
            {"TMT", "Manat turcomano"},
            {"TND", "Dinar tunecino"},
            {"TRY", "Lira turca"},
            {"TTD", "Dólar de Trinidad y Tobago"},
            {"TWD", "Nuevo dólar taiwanés"},
            {"TZS", "Chelín tanzano"},
            {"UAH", "Grivna ucraniana"},
            {"UGX", "Chelín ugandés"},
            {"USD", "Dólar estadounidense"},
            {"UYU", "Peso uruguayo"},
            {"UZS", "Som uzbeko"},
            {"VES", "Bolívar venezolano"},
            {"VND", "Dong vietnamita"},
            {"ZAR", "Rand sudafricano"},
            {"ZMW", "Kwacha zambiano"}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Mostrar menú de monedas
        System.out.println("Selecciona la moneda que deseas convertir:");
        for (int i = 0; i < CURRENCIES.length; i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, CURRENCIES[i][1], CURRENCIES[i][0]);
        }

        int fromCurrencyIndex = getCurrencySelection(scanner, CURRENCIES.length);
        String fromCurrency = CURRENCIES[fromCurrencyIndex - 1][0];

        // Preguntar a qué moneda convertir
        System.out.println("Selecciona al tipo de la moneda a la que deseas convertir:");
        for (int i = 0; i < CURRENCIES.length; i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, CURRENCIES[i][1], CURRENCIES[i][0]);
        }

        int toCurrencyIndex = getCurrencySelection(scanner, CURRENCIES.length);
        String toCurrency = CURRENCIES[toCurrencyIndex - 1][0];

        System.out.print("Ingresa la cantidad a convertir: ");
        double amount = scanner.nextDouble();

        // Obtener el tipo de cambio
        try {
            double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
            double convertedAmount = amount * exchangeRate;
            System.out.printf("%.2f %s son %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);
        } catch (Exception e) {
            System.out.println("Error al obtener el tipo de cambio: " + e.getMessage());
        }
    }

    private static int getCurrencySelection(Scanner scanner, int maxIndex) {
        int selection;
        while (true) {
            System.out.print("Selecciona una opción (1-" + maxIndex + "): ");
            if (scanner.hasNextInt()) {
                selection = scanner.nextInt();
                if (selection >= 1 && selection <= maxIndex) {
                    return selection;
                }
            } else {
                scanner.next(); // Limpiar entrada inválida
            }
            System.out.println("Selección no válida, inténtalo de nuevo.");
        }
    }

    private static double getExchangeRate(String fromCurrency, String toCurrency) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(response.body(), Map.class);
        Map<String, Double> rates = (Map<String, Double>) data.get("conversion_rates");

        double fromRate = rates.get(fromCurrency);
        double toRate = rates.get(toCurrency);

        return toRate / fromRate;
    }
}