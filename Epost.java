import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Epost{
    public static void main(String[] args) throws IOException {
        System.out.println("yolo");

        //String u = "https://www.internal.org/Ezra_Pound/Grace_Before_Song";
        String u = "https://www.oslomet.no/om/ansatteoversikt?q=Institutt+for+informasjonsteknologi&f" +
                "ilter%5B%5D=&filter%5B%5D=Fakultet+for+teknologi%2C+kunst+og+design";

        boolean b = isWebpage(u);

        System.out.println(b);
        if(b) {
            ReadPage(u);
        }
    }


    // Check if URL is validoo
    public static Boolean isWebpage(String u){

        try{
            new URL(u).toURI();
            return true;
        }

        catch (Exception e){
            return false;
        }
    }


    // Read dat page!!!
    public static void ReadPage(String s) throws IOException {

        URL u = new URL(s);
        Boolean hasEmails = false;

        System.out.println(s);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(u.openStream()));

        String inLine;
        while ((inLine = in.readLine()) != null) {
            //System.out.println(inLine);

            Pattern pattern = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])");
            Matcher matcher = pattern.matcher(inLine);

            while(matcher.find()){
                System.out.println(matcher.group());
            }

        }
        in.close();
        }




}


