import APICalls.APICalls;
import AStar.CalcRoute;
import Controller.MoveController;
import Model.ModelData;
import TI.BoeBot;
import TI.Servo;


public class RobotMain {

    public static Servo Rechts = new Servo(12);
    public static Servo Links = new Servo(13);

    public static void main(String[] args) {
        APICalls apiCalls = new APICalls();
        BoeBot.digitalWrite(11, false);
        BoeBot.digitalWrite(2, false);

        while (true){
//
            switch(apiCalls.getStatus().getStatus().toUpperCase()) {
                case "DRIVE":
                    System.out.println("Hier crashed die niet 3");
                    Controller.MoveController.lineFollow(Links, Rechts);
                    break;
                case "CALCULATE":
                    CalcRoute.calcRoute();
                    System.out.println("Calc locatie = " + ModelData.getCurrentX()+","+ ModelData.getCurrentY());
                    break;
                case "DIAGNOSE":
                    Controller.MoveController.diagnose(Links, Rechts);
                    break;
                case "STANDBY":
                    System.out.println("Ik ben in standby");
                    MoveController.knipper();
                    BoeBot.wait(5000);
                    break;
                default:
                    BoeBot.wait(50);
            }

            }
        }

//

//
//        while (ModelData.getWhiles() == "Test") {
//            System.out.println(ModelData.getBlocksArray().toString());
//            System.out.println(moveController.checkObstacles(ModelData.getBlocksArray(), new Node(4, 4), 0));
//            BoeBot.wait(1000);
//            System.out.println(ModelData.getBlocksArray().toString());
//            ModelData.setWhiles("Stop");
//        }
//
//        while (ModelData.getWhiles() == "Diagnose") {
//            MoveController.diagnose(Links, Rechts);
//        }
//
//    }

//    static void mainCaller() {
//        if(ModelData.getWhiles() != "Stop"){
//            main(null);
//        }
//
//    }
}
