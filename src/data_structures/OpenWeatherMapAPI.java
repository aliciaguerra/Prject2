/*
 *Alicia Guerra
 *CS 310: Data Structures
 *Professor Steve Price
 *masc 1529
*/

package data_structures;
/*BufferedReader reads text from a character-input stream, buffering characters so as to
provide efficient reading of characters, arrays, and lines. The buffer size may be specified,
or the default size may be used. The default size is large enough for both purposes. In general,
each read request made of a Reader must be made of the underlying character or byte stream. It
is therefore advisable to wrap a BufferedReader around any reader whose read() options may be costly,
such as filereaders and InputStreamReaders. Without buffering, each invocation of read() or readLine()
could cause bytes to be read from the file, converted into characters, and then returned, which can
be very inefficient.*/ 
import java.io.BufferedReader;
import java.io.IOException;
/*The InputStreamReader connects the program to a data source, and fetches data from that source to
make it avaiable to the program for manipulation. An InputStreamReader wraps around an InputStream
and converts the reading input stream from a byte stream to a character stream.*/
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
/*We use java.net.URL from the Java Standard Library to make HTTP and HTTPS connections from our
Java applications.*/
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
/*I added a JAR file containing the JSON library into NetBeans.*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
public class OpenWeatherMapAPI {
// I picked Mexico City because that's where my family's from ;) 
    static final String URL_OpenWeatherMap_weather_Mexico_City_mx =
            "http://api.openweathermap.org/data/2.5/weather?q=Mexico_City,mx";
 
    public static void main(String[] args) {
         
        String result = "";
 /*The first step to constructing an exception handler in Java is to enclose the code that might throw
an exception within a try block. The code consists of one or more legal lines of code that could 
throw an exception. To associate an exception handler with a try block, you must put a catch block after
it.*/        
        try {
            URL url_weather = new URL(URL_OpenWeatherMap_weather_Mexico_City_mx);
 
            HttpURLConnection httpURLConnection = (HttpURLConnection) url_weather.openConnection();
 
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
 
                InputStreamReader inputStreamReader =
                    new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader =
                    new BufferedReader(inputStreamReader, 8192);
                String line = null;
/*Since the lines aren't null, we keep reading them in.*/
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                 
                bufferedReader.close();
                 
                String weatherResult = ParseResult(result);
                 
                System.out.println(weatherResult);
 
            } else {
                System.out.println("Error in httpURLConnection.getResponseCode()!!!");
            }
 
        } catch (MalformedURLException ex) {
            Logger.getLogger(OpenWeatherMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpenWeatherMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(OpenWeatherMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/* After we create an instance of JSONParser, we create a JSONObject by parsing the FileReader of 
our .json file. This JSONObject contains a collection of key-value pairs, from which we can get 
every value of the json file. To retrieve primitive objects, get() method of the JSONObject's 
instance is called, defining the specified key as an argument. It is important to add the suitable 
cast to the method. For array types in json file, JSONArray is used that represents an ordered 
sequence of values. As you can notice in the code, an Iterator should be used in order to take 
each value of the json array. A structure in the json file, signs the creation of a new 
JSONObject in order to retrieve the values.*/   
    static private String ParseResult(String json) throws JSONException{
         
        String parsedResult = "";
      
/*A JSONObject is an unordered collection of name/value pairs. Its external form is a string 
wrapped in curly braces with colons between the names and values, and commas between the values 
and names. The internal form is an object having get and opt methods for accessing the values 
by name, and put methods for adding or replacing values by name.*/ 
        JSONObject jsonObject = new JSONObject(json);
 
        parsedResult += "Number of object = " + jsonObject.length() + "\n\n";
      
        
        JSONObject JSONObject_coord = jsonObject.getJSONObject("coord");
        Double result_lon = JSONObject_coord.getDouble("lon");
        Double result_lat = JSONObject_coord.getDouble("lat");
      
       
        JSONObject JSONObject_sys = jsonObject.getJSONObject("sys");
        String result_country = JSONObject_sys.getString("country");
        int result_sunrise = JSONObject_sys.getInt("sunrise");
        int result_sunset = JSONObject_sys.getInt("sunset");
         
 /*A JSONArray is an ordered sequence of values. Its external text form is a string wrapped 
 in square brackets with commas separating the values. The internal form is an object having 
 get and opt methods for accessing the values by index, and put methods for adding or replacing 
values. The values can be any of these types: Boolean, JSONArray, JSONObject, Number, String, 
or the JSONObject.NULL object.*/       
        String result_weather;
        JSONArray JSONArray_weather = jsonObject.getJSONArray("weather");
        if(JSONArray_weather.length() > 0){
            JSONObject JSONObject_weather = JSONArray_weather.getJSONObject(0);
            int result_id = JSONObject_weather.getInt("id");
            String result_main = JSONObject_weather.getString("main");
            String result_description = JSONObject_weather.getString("description");
            String result_icon = JSONObject_weather.getString("icon");
         
            result_weather = "weather\nid: " + result_id +"\nmain: " + result_main + "\ndescription: " + result_description + "\nicon: " + result_icon;
        }else{
            result_weather = "weather empty!";
        }
         
        
        String result_base = jsonObject.getString("base");
         
        
        JSONObject JSONObject_main = jsonObject.getJSONObject("main");
        Double result_temp = JSONObject_main.getDouble("temp");
        Double result_pressure = JSONObject_main.getDouble("pressure");
        Double result_humidity = JSONObject_main.getDouble("humidity");
        Double result_temp_min = JSONObject_main.getDouble("temp_min");
        Double result_temp_max = JSONObject_main.getDouble("temp_max");
         
        
        JSONObject JSONObject_wind = jsonObject.getJSONObject("wind");
        Double result_speed = JSONObject_wind.getDouble("speed");
        
        Double result_deg = JSONObject_wind.getDouble("deg");
        String result_wind = "wind\nspeed: " + result_speed + "\ndeg: " + result_deg;
         
        
        JSONObject JSONObject_clouds = jsonObject.getJSONObject("clouds");
        int result_all = JSONObject_clouds.getInt("all");
         
        
        int result_dt = jsonObject.getInt("dt");
         
        
        int result_id = jsonObject.getInt("id");
         
        
        String result_name = jsonObject.getString("name");
         
        
        int result_cod = jsonObject.getInt("cod");
         
        return
            "coord\nlon: " + result_lon + "\nlat: " + result_lat + "\n" +
            "sys\ncountry: " + result_country + "\nsunrise: " + result_sunrise + "\nsunset: " + result_sunset + "\n" +
            result_weather + "\n"+
            "base: " + result_base + "\n" +
            "main\ntemp: " + result_temp + "\nhumidity: " + result_humidity + "\npressure: " + result_pressure + "\ntemp_min: " + result_temp_min + "\ntemp_max: " + result_temp_min + "\n" +
            result_wind + "\n" +
            "clouds\nall: " + result_all + "\n" +
            "dt: " + result_dt + "\n" +
            "id: " + result_id + "\n" +
            "name: " + result_name + "\n" +
            "cod: " + result_cod + "\n" +
            "\n";
    }
}
