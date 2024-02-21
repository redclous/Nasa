package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient; // importing the libraries.
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
//    String url ="https://api.nasa.gov/planetary/apod" +
//            "?api_key=GrAnMBA0XX3376mu6MMt6heJXHehM26oAT9NcZS1";  //  with no date - it's today's
    String url ="https://api.nasa.gov/planetary/apod" +
            "?api_key=GrAnMBA0XX3376mu6MMt6heJXHehM26oAT9NcZS1" +
            "&date=2024-02-18";  //  with date added into the query
        CloseableHttpClient httpClient = HttpClients.createDefault();
            // instantiate object of class httpClient to work with net. it keeps the url address
        ObjectMapper mapper = new ObjectMapper();
            //  mapper object of class in Jackson library
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request); // here the server response saved
//        Scanner scanner = new Scanner(response.getEntity().getContent()); // class to read what is input ( from server in this case)
//        System.out.println(scanner.nextLine());
//        we run it and save the output ( formatted  with CNTR + Alt + L in the file Answer.json
//        so no we know how the response structure looks like, but we dont use it after - it's commeted out now.
        Nasa answer = mapper.readValue(response.getEntity().getContent(), Nasa.class);
        //this is a new object of the Nasa class that keeps the  transformation of the response from server
//        these below is only to  check that the data is requested and mapped and save to object correctly:
//        System.out.println(answer.url);
//        System.out.println(answer.title);

   // now we need to save the picture from url by sending the get request:
        HttpGet imageGet = new HttpGet(answer.url);
//        String filename ="NasaImage-"+ LocalDate.now() + ".jpg"; // so the files have different names:
//        there is a method to split the string  with / delimiter: we'll have these 7 items in that array:
//        "https://apod.nasa.gov/apod/image/2402/AM1054_Hubble_960.jpg"
//        "https:" +
//                "" +
//                "apod.nasa.gov" +
//                "apod" +
//                "image" +
//                "2402" +
//                "AM1054_Hubble_960.jpg"
        String[] urlSplited = answer.url.split("/");
        String filename = urlSplited[urlSplited.length -1]; // to access the last item of the array
        CloseableHttpResponse image = httpClient.execute(imageGet); // sending the request
        // now we need to save the image as a file:
        FileOutputStream fileOutputStream = new FileOutputStream(filename); // create an object
        image.getEntity().writeTo(fileOutputStream); // to get the image and write it into the file of the *.jpg
    }
}