import server.RequestListener;
import server.RequestParser;
import server.interfaces.IRequestListener;
import server.interfaces.IRequestParser;

import java.util.Scanner;

public class Main {
    //todo plus d'encapsulation au niveau des send/receive
    //todo diagramme
    //todo methode d'interface avec d'autre appli pour la base de donné genre get phone/email
    public static void main(String[] args) {

        IRequestParser requestParser = RequestParser.getInstance();

        IRequestListener requestListener = new RequestListener(requestParser);

        //noinspection InfiniteLoopStatement
        while(true) {
            Scanner scanner = new Scanner(System.in);
            requestListener.onReceive(scanner.nextLine());
        }
    }
}
