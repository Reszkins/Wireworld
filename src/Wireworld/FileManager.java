package Wireworld;

import Wireworld.Components.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {
    static World world = new World();

    public static void ReadFromFile(String path){
        String line;
        BufferedReader fileReader = null;
        String filePath = path;

        world.Fill();
        for( int i = 0 ; i < 10 ; i++){
            for( int j = 0 ; j < 10 ; j++){
                System.out.print(world.wireworld[j][i]+" ");
            }
            System.out.println();
        }

        try {
            fileReader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("Błąd podczas próby otwarcia pliku!");
            System.exit(1);
        }

        try {
            while((line = fileReader.readLine()) != null){
                System.out.println(line);
                String[] arguments = line.split(",");
                String element = arguments[0];
                String position, direction, x, y, type;

                switch (element){
                    case "Diode":
                        position = arguments[1].substring(1);
                        direction = arguments[2].substring(1);
                        x = arguments[3].substring(1);
                        y = arguments[4].substring(1);
                        world = Diode.Diode(position,direction,Integer.parseInt(x),Integer.parseInt(y),world);
                        break;
                    case "OrGate":
                        position = arguments[1].substring(1);
                        direction = arguments[2].substring(1);
                        x = arguments[3].substring(1);
                        y = arguments[4].substring(1);
                        OrGate.OrGate(position,direction,Integer.parseInt(x),Integer.parseInt(y));
                        break;
                    case "AndNoGate":
                        position = arguments[1].substring(1);
                        direction = arguments[2].substring(1);
                        x = arguments[3].substring(1);
                        y = arguments[4].substring(1);
                        AndNoGate.AndNoGate(position,direction,Integer.parseInt(x),Integer.parseInt(y));
                        break;
                    case "FlipFlop":
                        position = arguments[1].substring(1);
                        direction = arguments[2].substring(1);
                        x = arguments[3].substring(1);
                        y = arguments[4].substring(1);
                        FlipFlop.FlipFlop(position,direction,Integer.parseInt(x),Integer.parseInt(y));
                        break;
                    case "Generator":
                        position = arguments[1].substring(1);
                        direction = arguments[2].substring(1);
                        x = arguments[3].substring(1);
                        y = arguments[4].substring(1);
                        Generator.Generator(position,direction,Integer.parseInt(x),Integer.parseInt(y));
                        break;
                    case "Wire":
                        x = arguments[1].substring(1);
                        y = arguments[2].substring(1);
                        Wire.Wire(Integer.parseInt(x),Integer.parseInt(y));
                        break;
                    case "Electron":
                        type = arguments[1].substring(1);
                        x = arguments[2].substring(1);
                        y = arguments[3].substring(1);
                        Electron.Electron(type,Integer.parseInt(x),Integer.parseInt(y));
                        break;
                    default:
                        System.out.println("Błąd danych wejściowych!");
                        System.exit(4);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Błąd podczas odczytywania pliku!");
            System.exit(2);
        }

        try {
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Błąd podczas zamykania pliku!");
            System.exit(3);
        }

        for( int i = 0 ; i < 10 ; i++){
            for( int j = 0 ; j < 10 ; j++){
                System.out.print(world.wireworld[j][i]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ReadFromFile(args[0]);
    }
}
