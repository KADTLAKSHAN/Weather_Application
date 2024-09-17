import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class WeatherAppGui  extends JFrame {

    private JSONObject weatherData;

    public WeatherAppGui(){

        //set up our gui and add a tittle
        super("Weather App");

        //configure gui to end the program's process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set the size of our gui (in pixels)
        setSize(450, 650);

        //load our gui at the center of the screen
        setLocationRelativeTo(null);

        //make our layout manager null to manually position our components within the gui
        setLayout(null);

        //prevent any size of our gui
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents(){

        //search field
        JTextField searchTextField = new JTextField();

        //set the location and size of our components
        searchTextField.setBounds(15,15,351,45);

        //change the font style and size
        searchTextField.setFont(new Font("Dialog",Font.PLAIN,24));

        add(searchTextField);




        //Weather Image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assests/cloudy.png"));
        weatherConditionImage.setBounds(0,125,450,217);
        add(weatherConditionImage);

        //temperature text
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0,350,450,54);
        temperatureText.setFont(new Font("Dialog",Font.BOLD,48));
        //Center the text
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        //weather condition description
        JLabel weatherConditionDec = new JLabel("Cloudy");
        weatherConditionDec.setBounds(0,405,450,36);
        weatherConditionDec.setFont(new Font("Dialog",Font.PLAIN,32));
        weatherConditionDec.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDec);

        //humidity image
        JLabel humidityImage = new JLabel(loadImage("src/assests/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        //humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90,500,85,55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);


        //windSpeed image
        JLabel windSpeedImage = new JLabel(loadImage("src/assests/windspeed.png"));
        windSpeedImage.setBounds(220,500,74,66);
        add(windSpeedImage);

        //windSpeed text
        JLabel windSpeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windSpeedText.setBounds(310,500,88,55);
        windSpeedText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(windSpeedText);


        //search button
        JButton searchButton = new JButton(loadImage("src/assests/search.png"));

        //change the cursor to a hand cursor when hovering over this button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get location from user
                String userInput = searchTextField.getText();

                //validate Input - remove whitespace to ensure non-empty text
                if(userInput.replaceAll("\\s","").length() <= 0){
                    return;
                }

                //retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                //update gui


                //update weather image

                String weatherCondition = (String) weatherData.get("weather_condition");

                //depending on the condition, we will update the weather image that corresponds with the condition

                switch (weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assests/clear.png"));
                        break;

                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assests/cloudy.png"));
                        break;

                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assests/rain.png"));
                        break;

                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assests/snow.png"));
                        break;
                }

                //update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " \u2103");

                //update weather condition text
                weatherConditionDec.setText(weatherCondition);

                //update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                //update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");

            }
        });

        add(searchButton);

    }


    //used to create images in our GUI components
    private ImageIcon loadImage(String resourcePath){
        try {
            //read the image file from the path given
            BufferedImage image = ImageIO.read(new File(resourcePath));

            //returns on image icon so that our component can render it
            return new ImageIcon(image);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        System.out.println("Could not find the resource");
        return null;
    }

}
