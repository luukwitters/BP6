package Controller;

import APICalls.APICalls;
import AStar.CalcRoute;
import AStar.Node;
import Model.ModelData;
import TI.BoeBot;
import TI.Servo;
import java.util.ArrayList;

public class MoveController {

    public static void forward(Servo Links, Servo Rechts){

        //update snelheid naar deze waarden (rij naar voren)
        Rechts.update(1550);
        Links.update(1450);
    }

    public static void turnRight(int turningSpeed, Servo Links, Servo Rechts) {

        Boolean state =  true;
        BoeBot.digitalWrite(2, state);
        // Calculate the time to turn per Degree
        int timePerDegree = 400 / turningSpeed;

        // Boebot laten draaien
        Links.update(1500 + turningSpeed);
        Rechts.update(1500 + turningSpeed);
        // Boebot de berekende tijd laten wachten
        BoeBot.wait(timePerDegree * 93);

        // Boebot stoppen
        emergencyBrake(Links, Rechts);

        BoeBot.digitalWrite(2, !state);
        String richting = ModelData.getRichting();
        switch(richting) {
            case "noord":
                ModelData.setRichting("oost");
                break;
            case "oost":
                ModelData.setRichting("zuid");
                break;
            case "zuid":
                ModelData.setRichting("west");
                break;
            case "west":
                ModelData.setRichting("noord");
                break;
            default:
                System.out.println("error");
        }
    }

    public static void turnLeft(int turningSpeed, Servo Links, Servo Rechts) {

        Boolean state =  true;
        BoeBot.digitalWrite(11, state);
//         Calculate the time to turn per Degree
        int timePerDegree = 400 / turningSpeed;

        // Boebot laten draaien
        Links.update(1500 - turningSpeed);
        Rechts.update(1500 - turningSpeed);
        // Boebot de berekende tijd laten wachten
        BoeBot.wait(timePerDegree * 90);

//         Boebot stoppen
        emergencyBrake(Links, Rechts);
        BoeBot.digitalWrite(11, !state);

        String richting = ModelData.getRichting();
        switch(richting) {
            case "noord":
                ModelData.setRichting("west");
                break;
            case "oost":
                ModelData.setRichting("noord");
                break;
            case "zuid":
                ModelData.setRichting("oost");
                break;
            case "west":
                ModelData.setRichting("zuid");
                break;
            default:
                System.out.println("error");
        }

    }

    public static void emergencyBrake(Servo Links, Servo Rechts) {
        // Boebot stil laten staan
        Links.update(1500);
        Rechts.update(1500);
        BoeBot.wait(250);
    }

    public static void lineFollow(Servo Links, Servo Rechts) {

        int analogeWaardeR = BoeBot.analogRead(0);
        int analogeWaardeM = BoeBot.analogRead(1);
        int analogeWaardeL = BoeBot.analogRead(2);

        if (analogeWaardeM > 60 && analogeWaardeL > 60 && analogeWaardeR > 60) {
            Rechts.update(1525);
            Links.update(1475);
            if(ModelData.getTestCount() == 1){
                ModelData.setCurrentY(ModelData.getNextY());
                ModelData.setCurrentX(ModelData.getNextX());
            }
            detectObstacle(Links, Rechts);
            ModelData.setTestCount(1);
            BoeBot.wait(1150);
            CalcRoute.driveRoute(Links, Rechts);

            //Naar voren
        } else if (analogeWaardeR > 60 && analogeWaardeM < 60 && analogeWaardeL > 60) {
            Rechts.update(1525);
            Links.update(1475);
            lineFollow(Links, Rechts);
            //Naar voren
        } else if (analogeWaardeR < 60 && analogeWaardeM > 60 && analogeWaardeL < 60) {
            Rechts.update(1525);
            Links.update(1475);
            lineFollow(Links, Rechts);
            //Naar rechts corrigeren
        } else if (analogeWaardeR > 60 && analogeWaardeL < 60) {
            Rechts.update(1500);
            Links.update(1500);
            Rechts.update(1525);
            Links.update(1500);
            lineFollow(Links, Rechts);
            //Naar links corrigeren
        } else if (analogeWaardeL > 60 && analogeWaardeR < 60) {
            Rechts.update(1500);
            Links.update(1500);
            Rechts.update(1500);
            Links.update(1475);
            lineFollow(Links, Rechts);
        } else if (analogeWaardeM < 60 && analogeWaardeL < 60 && analogeWaardeR < 60) {
            Rechts.update(1525);
            Links.update(1475);
            lineFollow(Links, Rechts);
        }
        BoeBot.wait(50);
    }

    public static void detectObstacle(Servo Links, Servo Rechts){
        System.out.println("Detecting");
        // Geluid versturen
        BoeBot.digitalWrite(0, true);
        BoeBot.wait(1);
        BoeBot.digitalWrite(0, false);

        // Afstand berekenen in cm
        int afstand = BoeBot.pulseIn(1, true, 10000) / 58;
//        Kijken of er een object in de buurt is
        if (afstand <= 20) {
            zoomer();
            int currentX = ModelData.getCurrentX();
            int currentY = ModelData.getCurrentY();

            ArrayList<Node> obstacleList = ModelData.getBlocksArray();

            switch(ModelData.getRichting()) {
                case "noord":
                    Node detectedObstacleN = new Node(currentX, currentY +1);
                    Boolean newObstacleN = checkObstacles(obstacleList, detectedObstacleN, 0);
                    if(newObstacleN == true){
                        emergencyBrake(Links, Rechts);
                        CalcRoute.reCalcRoute();
                    }

                    else{
                        CalcRoute.driveRoute(Links, Rechts);
                    }
                    break;

                case "oost":
                    Node detectedObstacleO = new Node(currentX +1, currentY);
                    Boolean newObstacleO = checkObstacles(ModelData.getBlocksArray(), detectedObstacleO, 0);
                    if(newObstacleO == true){
                        emergencyBrake(Links, Rechts);
                        CalcRoute.reCalcRoute();
                    }
                    else{
                        CalcRoute.driveRoute(Links, Rechts);
                    }
                    break;

                case "zuid":
                    Node detectedObstacleZ = new Node(currentX, currentY -1);
                    Boolean newObstacleZ = checkObstacles(ModelData.getBlocksArray(), detectedObstacleZ, 0);
                    if(newObstacleZ == true){
                        emergencyBrake(Links, Rechts);
                        CalcRoute.reCalcRoute();
                    }

                    else{
                        CalcRoute.driveRoute(Links, Rechts);
                    }
                    break;

                case "west":
                    Node detectedObstacleW = new Node(currentX-1, currentY);
                    Boolean newObstacleW = checkObstacles(ModelData.getBlocksArray(), detectedObstacleW, 0);
                    if(newObstacleW == true){
                        emergencyBrake(Links, Rechts);
                        CalcRoute.reCalcRoute();

                    }

                    else{
                        CalcRoute.driveRoute(Links, Rechts);
                    }
                    break;

                default:
                    System.out.println("Geen richting, dit is een fout");
            }
        }
        BoeBot.wait(50);
    }

    public static boolean checkObstacles(ArrayList<Node> obstacleList, Node detectedObstacle, int offset){

        if (offset <= obstacleList.size()){
            Node obstacle = obstacleList.get(offset);
            if (obstacle.getRow() != detectedObstacle.getRow() && obstacle.getCol() != detectedObstacle.getCol()){
                obstacleList.add(detectedObstacle);
                ModelData.setBlocksArray(obstacleList);
                ModelData.setsRow(ModelData.getCurrentX());
                ModelData.setsCol(ModelData.getCurrentY());
                return true;
            }

            offset++;
            return checkObstacles(obstacleList, detectedObstacle, offset);
        }

        else{
            return false;
        }
    }

    public static void diagnose(Servo Links, Servo Rechts){
        int count = 0;
        Links.update(1500 - 50);
        Rechts.update(1500 - 50);
        BoeBot.wait(2000);
        Links.update(1500 + 50);
        Rechts.update(1500 + 50);
        BoeBot.wait(2000);
        emergencyBrake(Links, Rechts);
        for(int i = 0; i<6; i++){
            knipper();
        }
        zoomer();
        APICalls.setStatus("STANDBY");
    }

    public static void knipper(){
        if (BoeBot.digitalRead(11) == true && BoeBot.digitalRead(2) == true){
            BoeBot.digitalWrite(11, false);
            BoeBot.digitalWrite(2, false);
            BoeBot.wait(500);
        }
        else{
            BoeBot.digitalWrite(11, true);
            BoeBot.digitalWrite(2, true);
            BoeBot.wait(500);
        }
    }

    public static void zoomer(){
        for(int freq = 1000; freq<2000; freq++){
            BoeBot.freqOut(14,freq,1);
        }
    }
}


