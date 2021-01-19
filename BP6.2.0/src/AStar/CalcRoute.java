package AStar;

import APICalls.Models.GridValues;
import APICalls.Models.SEcoords;
import Controller.MoveController;
import APICalls.APICalls;
import Model.ModelData;
import TI.Servo;

import java.util.ArrayList;
import java.util.List;

public class CalcRoute {

    public static ArrayList<Node> calcRoute() {
        SEcoords nodes = APICalls.getSENode();

        Node initialNode = new Node(nodes.getStartRow(), nodes.getStartColumn());
        ModelData.setCurrentX(ModelData.getsRow());
        ModelData.setCurrentY(ModelData.getsCol());
        int endX = nodes.getEndRow();
        int endY = nodes.getEndColumn();
        Node finalNode = new Node(endX, endY);
        ModelData.setEindX(endX);
        ModelData.setEindY(endY);

        GridValues grids = APICalls.GetGrid();
        int rows = grids.getGX();
        int cols = grids.getGY();
        ModelData.setGridX(rows);
        ModelData.setGridY(cols);
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        ArrayList<Node> blocksArray = APICalls.getObstacles();
        ModelData.setBlocksArray(blocksArray);
        aStar.setBlocks(ModelData.getBlocksArray());
        List<Node> path = aStar.findPath();
        ArrayList<Node> routeList = new ArrayList<>();


        for (Node node : path) {
            System.out.println(node);
            routeList.add(new Node(node.getRow(), node.getCol()));
        }

        ModelData.setRouteList(routeList);
        APICalls.setStatus("STANDBY");
        return routeList;

    }

    public static ArrayList<Node> reCalcRoute() {
        ModelData.setOffset(1);
        ModelData.setsRow(ModelData.getCurrentX());
        ModelData.setsCol(ModelData.getCurrentY());
        Node initialNode = new Node(ModelData.getsRow(), ModelData.getsCol());
        Node finalNode = new Node(ModelData.getEindX(), ModelData.getEindY());
        int rows = ModelData.getGridX();
        int cols = ModelData.getGridY();
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        aStar.setBlocks(ModelData.getBlocksArray());
        List<Node> path = aStar.findPath();
        ArrayList<Node> routeList = new ArrayList<>();


        for (Node node : path) {
            System.out.println(node);
            routeList.add(new Node(node.getRow(), node.getCol()));
        }

        ModelData.setRouteList(routeList);
        return routeList;
    }

    public static void driveRoute(Servo Links, Servo Rechts) {
        int turningspeed = 34;
        ArrayList<Node> routeList = ModelData.getRouteList();

        if (ModelData.getOffset() < routeList.size()) {
            ModelData.setNextX(routeList.get(ModelData.getOffset()).getRow());
            ModelData.setNextY(routeList.get(ModelData.getOffset()).getCol());

            //X-as omhoog
            if (ModelData.getCurrentX() < ModelData.getNextX()) {
                switch (ModelData.getRichting()) {
                    case "noord":
                        MoveController.turnRight(turningspeed, Links, Rechts);
                        break;
                    case "oost":
                        MoveController.forward(Links, Rechts);
                        break;
                    case "zuid":
                        MoveController.turnLeft(turningspeed, Links, Rechts);
                        break;
                    case "west":
                        break;
                    default:
                        System.out.println("error");
                }
                ModelData.setCurrentX(ModelData.getNextX());

            } else if (ModelData.getCurrentX() > ModelData.getNextX()) {
                //X-as omlaag

                switch (ModelData.getRichting()) {
                    case "noord":
                        MoveController.turnLeft(turningspeed, Links, Rechts);
                        break;
                    case "oost":
                        break;
                    case "zuid":
                        MoveController.turnRight(turningspeed, Links, Rechts);
                        break;
                    case "west":
                        MoveController.forward(Links, Rechts);
                        break;
                    default:
                        System.out.println("error");
                }
                ModelData.setCurrentX(ModelData.getNextX());

            } else if (ModelData.getCurrentY() < ModelData.getNextY()) {
                //Y-as omhoog

                switch (ModelData.getRichting()) {
                    case "noord":
                        MoveController.forward(Links, Rechts);
                        break;
                    case "oost":
                        MoveController.turnLeft(turningspeed, Links, Rechts);
                        break;
                    case "zuid":
                        break;
                    case "west":
                        MoveController.turnRight(turningspeed, Links, Rechts);
                        break;
                    default:
                        System.out.println("error");
                }
                ModelData.setCurrentY(ModelData.getNextY());

            } else if (ModelData.getCurrentY() > ModelData.getNextY()) {
                //Y-as omlaag

                switch (ModelData.getRichting()) {
                    case "noord":
                        break;
                    case "oost":
                        MoveController.turnRight(turningspeed, Links, Rechts);
                        break;
                    case "zuid":
                        MoveController.forward(Links, Rechts);
                        break;
                    case "west":
                        MoveController.turnLeft(turningspeed, Links, Rechts);
                        break;
                    default:
                        System.out.println("error");
                }
                ModelData.setCurrentY(ModelData.getNextY());
            }


            ModelData.setOffset(ModelData.getOffset()+1);
            MoveController.emergencyBrake(Links, Rechts);
            APICalls.setLocation(ModelData.getCurrentX(), ModelData.getCurrentY());
            MoveController.lineFollow(Links, Rechts);
        }

        else {
            MoveController.emergencyBrake(Links, Rechts);
            MoveController.knipper();
            APICalls.setStatus("STANDBY");
        }
    }

}
