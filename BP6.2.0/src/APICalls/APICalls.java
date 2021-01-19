package APICalls;

import APICalls.Models.*;
import AStar.Node;
import json.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class APICalls {

    public static SEcoords getSENode() {
        HttpURLConnection uc = null;

        try {
            URL apiLink = new URL("https://bp6.adainforma.tk/bugbot/bugbot/Functies/Data/API/Startcoordinate/getSE?selector=62898fa258197b90&validator=2a922ef9a227aceeac1cadafb7ef3a8d094b71a8345125610bef760f23ba9d29");
            // Opening the file
            uc = (HttpURLConnection) apiLink.openConnection();
            // Reading the file
            BufferedReader result = new BufferedReader(new InputStreamReader(uc.getInputStream()));

            // String for the given JSON
            String inputLine;
            // Looping through the JSON
            if ((inputLine = result.readLine()) != null) {
                // Creating an array of the result
                JsonArray json = Json.parse(inputLine).asArray();

                // Looping through the result
                for (Object o : json) {
                    // Converting the array to an Object
                    JsonObject obj = (JsonObject) o;

                    // Getting the values
                    int startX = Integer.parseInt(removeQuotes(obj.get("startX")));
                    int startY = Integer.parseInt(removeQuotes(obj.get("startY")));
                    int endX = Integer.parseInt(removeQuotes(obj.get("endX")));
                    int endY = Integer.parseInt(removeQuotes(obj.get("endY")));
                    System.out.print("Loopt SE?");
                    SEcoords seCoords = new SEcoords(startX, startY, endX, endY);

                    return seCoords;

                }
            }
        } catch (Exception e) {
            // Printing the error
            e.printStackTrace();
        }
        finally {
            Objects.requireNonNull(uc).disconnect();
        }

        return null;
    }

    public void setSC(int SX, int SY) {
        HttpURLConnection uc = null;

        try {
            URL apiLink = new URL("https://bp6.adainforma.tk/bugbot/bugbot/Functies/Data/API/Startcoordinate/putSC?selector=62898fa258197b90&validator=2a922ef9a227aceeac1cadafb7ef3a8d094b71a8345125610bef760f23ba9d29&SX="+ SX + "&SY="+ SY);
            // Opening the file
            uc = (HttpURLConnection) apiLink.openConnection();
            // Reading the file
            BufferedReader result = new BufferedReader(new InputStreamReader(uc.getInputStream()));

        } catch (Exception e) {
            // Printing the error
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(uc).disconnect();
        }

    }

    public static GridValues GetGrid() {
        HttpURLConnection uc = null;

        try {
            URL apiLink = new URL("https://bp6.adainforma.tk/bugbot/bugbot/Functies/Data/API/Grid/getGrid?");
            // Opening the file
            uc = (HttpURLConnection) apiLink.openConnection();
            // Reading the file
            BufferedReader result = new BufferedReader(new InputStreamReader(uc.getInputStream()));

            // String for the given JSON
            String inputLine;
            // Looping through the JSON
            while ((inputLine = result.readLine()) != null) {
                // Creating an array of the result
                JsonArray json = Json.parse(inputLine).asArray();

                // Looping through the result
                for (Object o : json) {
                    // Converting the array to an Object
                    JsonObject obj = (JsonObject) o;

                    // Getting the values
                    int GY = Integer.parseInt(removeQuotes(obj.get("GY")));
                    int GX = Integer.parseInt(removeQuotes(obj.get("GX")));

                    GridValues gridvalues = new GridValues(GX, GY);

                    return gridvalues;
                }

            }
        } catch (Exception e) {
            // Printing the error
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(uc).disconnect();
        }
        return null;
    }

    public static final ArrayList<Node> getObstacles() {
        HttpURLConnection uc = null;

        ArrayList<Node> obstacleList = new ArrayList<>();

        try {
            URL apiLink = new URL("https://bp6.adainforma.tk/bugbot/bugbot/Functies/Data/API/Obstacles/getObstacle?selector=62898fa258197b90&validator=2a922ef9a227aceeac1cadafb7ef3a8d094b71a8345125610bef760f23ba9d29");
            // Opening the file
            uc = (HttpURLConnection) apiLink.openConnection();
            // Reading the file
            BufferedReader result = new BufferedReader(new InputStreamReader(uc.getInputStream()));

            // String for the given JSON
            String inputLine;
            // Looping through the JSON
            while ((inputLine = result.readLine()) != null) {
                // Creating an array of the result
                JsonArray json = Json.parse(inputLine).asArray();

                // Looping through the result
                for (Object o : json) {
                    // Converting the array to an Object
                    JsonObject obj = (JsonObject) o;

                    // Getting the values
                    int OSX = Integer.parseInt(removeQuotes(obj.get("OSX")));
                    int OSY = Integer.parseInt(removeQuotes(obj.get("OSY")));

                    obstacleList.add(new Node(OSX, OSY));

                }
            }

        } catch (Exception e) {
            // Printing the error
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(uc).disconnect();
        }
        return obstacleList;
    }

    public final void setObstacle(int var1, int var2, int var3, int var4) {
        HttpURLConnection uc = null;

        try {
            URL apiLink = new URL("https://bp6.adainforma.tk/bugbot/bugbot/Functies/Data/API/Obstacles/postObstacle?selector=62898fa258197b90&validator=2a922ef9a227aceeac1cadafb7ef3a8d094b71a8345125610bef760f23ba9d29&OSX="+ var1 + "&OSY="+ var2 + "&OEX=" + var3 + "&OEY=" + var4);
            // Opening the file
            uc = (HttpURLConnection) apiLink.openConnection();
            // Reading the file
            BufferedReader result = new BufferedReader(new InputStreamReader(uc.getInputStream()));

        } catch (Exception e) {
            // Printing the error
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(uc).disconnect();
        }

    }

    public final LocationValues GetLocation() {
        HttpURLConnection uc = null;

        try {
            URL apiLink = new URL("https://bp6.adainforma.tk/bugbot/bugbot/Functies/Data/API/Route/getRoute?selector=62898fa258197b90&validator=2a922ef9a227aceeac1cadafb7ef3a8d094b71a8345125610bef760f23ba9d29");
            // Opening the file
            uc = (HttpURLConnection) apiLink.openConnection();
            // Reading the file
            BufferedReader result = new BufferedReader(new InputStreamReader(uc.getInputStream()));

            // String for the given JSON
            String inputLine;
            // Looping through the JSON
            while ((inputLine = result.readLine()) != null) {

                    // Converting the array to an Object
                    JsonObject obj = Json.parse(inputLine).asObject();

                    // Getting the values
                    int RX = Integer.parseInt(removeQuotes(obj.get("RX")));
                    int RY = Integer.parseInt(removeQuotes(obj.get("RY")));

                    LocationValues locationValues = new LocationValues(RX, RY);

                    return locationValues;

            }
        } catch (Exception e) {
            // Printing the error
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(uc).disconnect();
        }
        return null;
    }

    public static void setLocation(int RX, int RY) {
        HttpURLConnection uc = null;

        try {
//            URL apiLink = new URL("https://bp6.adainforma.tk/bugbot/bugbot/Functies/Data/API/Route/putRoute?selector=62898fa258197b90&validator=2a922ef9a227aceeac1cadafb7ef3a8d094b71a8345125610bef760f23ba9d29&RX="+ RX+ "&RY="+RY);
            URL apiLink = new URL("https://bp6.adainforma.tk/bugbot/bugbot/Functies/Data/API/Route/putRoute?RX="+RX+"&RY="+RY);
            // Opening the file
            uc = (HttpURLConnection) apiLink.openConnection();
            // Reading the file
            //BufferedReader result = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            uc.getContent();
        } catch (Exception e) {
            // Printing the error
            e.printStackTrace();
        }
//        finally {
//            Objects.requireNonNull(uc).disconnect();
//        }

    }

    public final StatusValues getStatus() {
        HttpURLConnection uc = null;

        try {
            URL apiLink = new URL("https://bp6.adainforma.tk/bugbot/bugbot/Functies/Data/API/Status/getStatus?");
            // Opening the file
            uc = (HttpURLConnection) apiLink.openConnection();
            // Reading the file
            BufferedReader result = new BufferedReader(new InputStreamReader(uc.getInputStream()));

            // String for the given JSON
            String inputLine;
            // Looping through the JSON
            if ((inputLine = result.readLine()) != null) {
                // Creating an array of the result
                JsonObject json = Json.parse(inputLine).asObject();

                // Looping through the result
//                for (Object o : json) {
                    // Converting the array to an Object
//                    JsonObject obj = (JsonObject) o

                    // Getting the values
                    String status = removeQuotes(json.get("status"));

                    StatusValues statusValues = new StatusValues(status);
//                    statusValues.toString();

                    return statusValues;
//                }
            }

        } catch (Exception e) {
            // Printing the error
            e.printStackTrace();
        }
        finally {
            assert uc != null;
            uc.disconnect();
        }
        return null;
    }

    public static final void setStatus(String status) {
        HttpURLConnection uc = null;

        try {
            URL apiLink = new URL("https://bp6.adainforma.tk/bugbot/bugbot/Functies/Data/API/Status/update_status?selector=62898fa258197b90&validator=2a922ef9a227aceeac1cadafb7ef3a8d094b71a8345125610bef760f23ba9d29&status="+ status);
            // Opening the file
            uc = (HttpURLConnection) apiLink.openConnection();
            // Reading the file
            BufferedReader result = new BufferedReader(new InputStreamReader(uc.getInputStream()));

        } catch (Exception e) {
            // Printing the error
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(uc).disconnect();
        }

    }

    private static String removeQuotes(JsonValue sJsonValue) {
        // Removing the quotes from the JsonValue
        String sStringJsonValue = sJsonValue.toString().replace("\"", "");

        return sStringJsonValue;
    }

}
