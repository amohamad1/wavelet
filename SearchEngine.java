import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    
    ArrayList<String> theStrings = new ArrayList<String>();
    String s;
    

    public String handleRequest(URI url) {
        
        if (url.getPath().equals("/")) {
            return String.format("Welcome to the search engine");
        }
        else if (url.getPath().contains("/search")) {
            String[] parameters1 = url.getQuery().split("=");
            if (parameters1[0].equals("s")) {
                s = parameters1[1];
                String found = "";
                for( String aString : theStrings ){

                    if(aString.contains(s)){
                        found = found + aString+"\n";
                    }

                }
                return String.format(found);
            }
        }
         else{
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters2 = url.getQuery().split("=");
                if (parameters2[0].equals("s")) {
                    theStrings.add(parameters2[1]);
                    return String.format("Added to list: ", parameters2[1]);
                }
            }
            return "404 Not Found!";
        }
        
        return "error";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

