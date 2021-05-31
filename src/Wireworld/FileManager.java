package Wireworld;

import GUI.Controller;
import Wireworld.Components.*;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    public World world;

    public World ReadFromFile(String path){
        String line;
        BufferedReader fileReader = null;
        String filePath = path;

        world = new World();
        world.Fill();

        try {
            fileReader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            Controller.displayError("Błąd podczas próby otwarcia pliku!");
        }

        try {
            while((line = fileReader.readLine()) != null){
                System.out.println(line);
                line = line.replaceAll("\\s+","");
                String[] arguments = line.split(",");
                String element = arguments[0];
                String position, direction, x, y, x2, y2, type;
                Component c;
                switch (element){
                    case "Diode":
                        world.list.add(line);
                        position = arguments[1];
                        direction = arguments[2];
                        x = arguments[3];
                        y = arguments[4];
                        c = new Diode(position,direction,Integer.parseInt(x),Integer.parseInt(y));
                        world.Merge(c.element, Integer.parseInt(x)- c.startX, Integer.parseInt(y)- c.startY);
                        break;
                    case "OrGate":
                        world.list.add(line);
                        position = arguments[1];
                        direction = arguments[2];
                        x = arguments[3];
                        y = arguments[4];
                        c = new OrGate(position,direction,Integer.parseInt(x),Integer.parseInt(y));
                        world.Merge(c.element, Integer.parseInt(x)- c.startX, Integer.parseInt(y)- c.startY);
                        break;
                    case "AndNoGate":
                        world.list.add(line);
                        position = arguments[1];
                        direction = arguments[2];
                        x = arguments[3];
                        y = arguments[4];
                        c = new AndNoGate(position,direction,Integer.parseInt(x),Integer.parseInt(y));
                        world.Merge(c.element, Integer.parseInt(x)- c.startX, Integer.parseInt(y)- c.startY);
                        break;
                    case "FlipFlop":
                        world.list.add(line);
                        position = arguments[1];
                        direction = arguments[2];
                        x = arguments[3];
                        y = arguments[4];
                        c = new FlipFlop(position,direction,Integer.parseInt(x),Integer.parseInt(y));
                        world.Merge(c.element, Integer.parseInt(x)- c.startX, Integer.parseInt(y)- c.startY);
                        break;
                    case "Generator":
                        world.list.add(line);
                        position = arguments[1];
                        x = arguments[2];
                        y = arguments[3];
                        c = new Generator(position,"Normal",Integer.parseInt(x),Integer.parseInt(y));
                        world.Merge(c.element, Integer.parseInt(x)- c.startX, Integer.parseInt(y)- c.startY);
                        break;
                    case "Wire":
                        world.list.add(line);
                        x = arguments[1];
                        y = arguments[2];
                        x2 = arguments[3];
                        y2 = arguments[4];
                        Wire.Wire(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(x2),Integer.parseInt(y2),world);
                        break;
                    case "Electron":
                        type = arguments[1];
                        x = arguments[2];
                        y = arguments[3];
                        Electron.Electron(type,Integer.parseInt(x),Integer.parseInt(y),world);
                        break;
                    default:
                        Controller.displayError("Błedne dane wejściowe!");
                        break;
                }
            }
        } catch (IOException e) {
            Controller.displayError("Błąd podczas odczytywania pliku!");
        }

        try {
            fileReader.close();
        } catch (IOException e) {
            Controller.displayError("Błąd podczas zamykania pliku!");
        }

        return world;
    }

    public World AddComponentToWorld(String element, ArrayList<String> args, World world){
        String[] arguments = new String[5];
        String position, direction, x, y, line, x2, y2, type;
        Component c;
        int i;

        switch (element) {
            case "Diode":
                i=0;
                for(String tmp : args){
                    arguments[i] = tmp;
                    i++;
                }

                position = arguments[0];
                direction = arguments[1];
                x = arguments[2];
                y = arguments[3];
                line = "Diode, "+arguments[0]+", "+arguments[1]+", "+arguments[2]+", "+arguments[3];
                world.list.add(line);
                c = new Diode(position, direction, Integer.parseInt(x), Integer.parseInt(y));
                world.Merge(c.element, Integer.parseInt(x)- c.startX, Integer.parseInt(y)- c.startY);
                break;
            case "OrGate":
                i=0;
                for(String tmp : args){
                    arguments[i] = tmp;
                    i++;
                }

                position = arguments[0];
                direction = arguments[1];
                x = arguments[2];
                y = arguments[3];
                line = "OrGate, "+arguments[0]+", "+arguments[1]+", "+arguments[2]+", "+arguments[3];
                world.list.add(line);
                c = new OrGate(position, direction, Integer.parseInt(x), Integer.parseInt(y));
                world.Merge(c.element, Integer.parseInt(x)- c.startX, Integer.parseInt(y)- c.startY);
                break;
            case "AndNoGate":
                i=0;
                for(String tmp : args){
                    arguments[i] = tmp;
                    i++;
                }

                position = arguments[0];
                direction = arguments[1];
                x = arguments[2];
                y = arguments[3];
                line = "AndNoGate, "+arguments[0]+", "+arguments[1]+", "+arguments[2]+", "+arguments[3];
                world.list.add(line);
                c = new AndNoGate(position, direction, Integer.parseInt(x), Integer.parseInt(y));
                world.Merge(c.element, Integer.parseInt(x)- c.startX, Integer.parseInt(y)- c.startY);
                break;
            case "FlipFlop":
                i=0;
                for(String tmp : args){
                    arguments[i] = tmp;
                    i++;
                }

                position = arguments[0];
                direction = arguments[1];
                x = arguments[2];
                y = arguments[3];
                line = "FlipFlop, "+arguments[0]+", "+arguments[1]+", "+arguments[2]+", "+arguments[3];
                world.list.add(line);
                c = new FlipFlop(position, direction, Integer.parseInt(x), Integer.parseInt(y));
                world.Merge(c.element, Integer.parseInt(x)- c.startX, Integer.parseInt(y)- c.startY);
                break;
            case "Generator":
                i=0;
                for(String tmp : args){
                    arguments[i] = tmp;
                    i++;
                }

                position = arguments[0];
                x = arguments[1];
                y = arguments[2];
                line = "Generator, "+arguments[0]+", "+arguments[1]+", "+arguments[2]+", "+arguments[3];
                world.list.add(line);
                c = new Generator(position,"Normal", Integer.parseInt(x), Integer.parseInt(y));
                world.Merge(c.element, Integer.parseInt(x)- c.startX, Integer.parseInt(y)- c.startY);
                break;
            case "Wire":
                i=0;
                for(String tmp : args){
                    arguments[i] = tmp;
                    i++;
                }

                x = arguments[0];
                y = arguments[1];
                x2 = arguments[2];
                y2 = arguments[3];
                line = "Wire, "+arguments[0]+", "+arguments[1]+", "+arguments[2]+", "+arguments[3];
                System.out.println("Wire, "+arguments[0]+", "+arguments[1]+", "+arguments[2]+", "+arguments[3]);
                world.list.add(line);
                Wire.Wire(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(x2), Integer.parseInt(y2), world);
                break;
            case "Electron":
                i=0;
                for(String tmp : args){
                    arguments[i] = tmp;
                    i++;
                }
                System.out.println("Electron, "+arguments[0]+", "+arguments[1]+", "+arguments[2]);
                type = arguments[0];
                x = arguments[1];
                y = arguments[2];
                Electron.Electron(type, Integer.parseInt(x), Integer.parseInt(y), world);
                break;
        }
        return world;
    }

    public static void WriteToFile(String path, World world) {
        BufferedWriter writer = null;

        try{
            writer = new BufferedWriter(new FileWriter(path));
        } catch (IOException e){
            Controller.displayError("Błąd podczas tworzenia pliku!");
        }
        try{
            for(String line : world.list){
                writer.write(line);
                writer.write("\n");
            }

        } catch (IOException e){
            Controller.displayError("Błąd podczas tworzenia pliku!");
        }

        for(int i=0;i<100;++i){
            for(int j=0;j<100;++j){
                if(world.wireworld[i][j] == Cells.Case.ELECTRON_HEAD) {
                    try {
                        String line = "Electron, Head, " + i + ", " + j;
                        writer.write(line);
                        writer.write("\n");
                    } catch (IOException e) {
                        Controller.displayError("Błąd podczas tworzenia pliku!");
                    }
                }
                if(world.wireworld[i][j] == Cells.Case.ELECTRON_TAIL) {
                    try {
                        String line = "Electron, Tail, " + i + ", " + j;
                        writer.write(line);
                        writer.write("\n");
                    } catch (IOException e) {
                        Controller.displayError("Błąd podczas tworzenia pliku!");
                    }
                }

            }
        }
        try{
            writer.close();
        } catch (IOException e) {
            Controller.displayError("Błąd podczas tworzenia pliku!");
        }
    }
}
