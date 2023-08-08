package com.kchan.chromascan.model;

import org.python.core.Py;
import org.python.core.PyInstance;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;  

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

public class ColourEvaluator {

    // private String file;
    private BufferedImage image;
    private ArrayList<PixelPosition> pp;
    private ArrayList<ColourBreakdown> cb;
    private int numPos;

    private static final String OPENAI_API_KEY = "YOUR_OPENAI_API_KEY";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";


    // send a list of positions
    public ColourEvaluator(String file, int x, int y){
        this.image = createImage(file);
        this.pp = new ArrayList<PixelPosition>(){{
            add(new PixelPosition(x, y));
        }};
        this.cb = new ArrayList<ColourBreakdown>();
        populateColourBreakdown();
    }

    public ColourEvaluator(String file, ArrayList<PixelPosition> pp){
        this.image = createImage(file);
        this.pp = pp;
        this.cb = new ArrayList<ColourBreakdown>();
        populateColourBreakdown();
    }

    private BufferedImage createImage(String file){
        try {
            return ImageIO.read(getClass().getResource(file)); 
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void populateColourBreakdown(){
        Iterator<PixelPosition> positions = pp.iterator();
        while(positions.hasNext()){
            PixelPosition p = positions.next();
            findColour(p.getX(), p.getY());
        }
    }

    private void findColour(int x, int y) {
        // Get data for creating colour object
        Rgb rgbObj = getRgbFromPosition(this.image, x, y);
        String hex = getHexFromRgb(rgbObj);
        // create colour object
        // Colour colour = new Colour(rgbObj, hex);
        String name = createName(hex);

        cb.add(new ColourBreakdown(rgbObj, hex, name, 100));

        System.out.println(cb.get(cb.size()-1).getRgb());
        System.out.println(cb.get(cb.size()-1).getHex());
    }

    private String getHexFromRgb(Rgb rgb){
        return String.format("#%02X%02X%02X", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }

    private Rgb getRgbFromPosition(BufferedImage image, int x, int y){
        int pixel = image.getRGB(x, y);
        Color color = new Color(pixel, true);
        return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    }

    private String createName(String hex){
        PythonInterpreter.initialize(System.getProperties(), System.getProperties(), new String[0]);  
        PythonInterpreter pi = new PythonInterpreter();
        pi.exec("import sys");
        pi.exec("sys.path.append('src/main/java/com/kchan/chromascan')");
        pi.exec("from api.OpenAiJython import OpenAiJython");
        pi.exec("openai_instance = OpenAiJython()");
        
        // pi.execfile("src/main/java/com/kchan/chromascan/api/openai_api.py");
        // PyInstance openai = (PyInstance) pi.eval("OpenAiAPI()");
        // PyObject name = openai.invoke_ex("create_name", new PyString(hex));
        PyObject name = pi.eval("openai_instance.createName(#000000)");
        pi.close();
        return name.asString();

        // String requestBody = "{"
        //     + "model='gpt-3.5-turbo',"
        //     + "messages=["
        //     + "{'role': 'system', 'content': 'You are a creative assistant.'},"
        //     +      "{'role': 'user', 'content': 'Come up with a fun name that describes the color #ffffff.'}"
        //     + "],"
        //     + "temperature=1,"
        //     + "max_tokens=20"
        //     +"}";

        // URL url = new URL(API_URL);
        // HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // connection.setRequestMethod("POST");
        // connection.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
        // connection.setDoOutput(true);

        // try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
        //     writer.write(requestBody);
        // }

        // int responseCode = connection.getResponseCode();
        // if (responseCode == HttpURLConnection.HTTP_OK) {
        //     try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        //         StringBuilder response = new StringBuilder();
        //         String line;
        //         while ((line = reader.readLine()) != null) {
        //             response.append(line);
        //         }
        //         return response.toString();
        //     }
        // } else {
        //     throw new IOException("API request failed with response code: " + responseCode);
        // }
    }

    public BufferedImage getImage() {
        return image;
    }

    public ArrayList<PixelPosition> getPixelPosition() {
        return pp;
    }

    public ArrayList<ColourBreakdown> getColourBreakdown() {
        return cb;
    }

    
}
