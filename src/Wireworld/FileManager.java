package Wireworld;

import Wireworld.Components.*;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    static ArrayList<String> list = new ArrayList<String>();
    static World world = new World();

    public static World ReadFromFile(String path){
        String line;
        BufferedReader fileReader = null;
        String filePath = path;

        world.Fill();
      /*  for( int i = 0 ; i < 100 ; i++){
            for( int j = 0 ; j < 100 ; j++){
                System.out.print(world.wireworld[j][i]+" ");
            }
            System.out.println();
        }*/

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
                String position, direction, x, y, x2, y2, type;

                switch (element){
                    case "Diode":
                        list.add(line);
                        position = arguments[1].substring(1);
                        direction = arguments[2].substring(1);
                        x = arguments[3].substring(1);
                        y = arguments[4].substring(1);
                        world = Diode.Diode(position,direction,Integer.parseInt(x),Integer.parseInt(y),world);
                        break;
                    case "OrGate":
                        list.add(line);
                        position = arguments[1].substring(1);
                        direction = arguments[2].substring(1);
                        x = arguments[3].substring(1);
                        y = arguments[4].substring(1);
                        world = OrGate.OrGate(position,direction,Integer.parseInt(x),Integer.parseInt(y),world);
                        break;
                    case "AndNoGate":
                        list.add(line);
                        position = arguments[1].substring(1);
                        direction = arguments[2].substring(1);
                        x = arguments[3].substring(1);
                        y = arguments[4].substring(1);
                        world = AndNoGate.AndNoGate(position,direction,Integer.parseInt(x),Integer.parseInt(y),world);
                        break;
                    case "FlipFlop":
                        list.add(line);
                        position = arguments[1].substring(1);
                        direction = arguments[2].substring(1);
                        x = arguments[3].substring(1);
                        y = arguments[4].substring(1);
                        world = FlipFlop.FlipFlop(position,direction,Integer.parseInt(x),Integer.parseInt(y),world);
                        break;
                    case "Generator":
                        list.add(line);
                        position = arguments[1].substring(1);
                        x = arguments[2].substring(1);
                        y = arguments[3].substring(1);
                        world = Generator.Generator(position,Integer.parseInt(x),Integer.parseInt(y),world);
                        break;
                    case "Wire":
                        list.add(line);
                        x = arguments[1].substring(1);
                        y = arguments[2].substring(1);
                        x2 = arguments[3].substring(1);
                        y2 = arguments[4].substring(1);
                        world = Wire.Wire(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(x2),Integer.parseInt(y2),world);
                        break;
                    case "Electron":
                        type = arguments[1].substring(1);
                        x = arguments[2].substring(1);
                        y = arguments[3].substring(1);
                        world = Electron.Electron(type,Integer.parseInt(x),Integer.parseInt(y),world);
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

       /* for( int i = 0 ; i < 100 ; i++){
            for( int j = 0 ; j < 100 ; j++){
                System.out.print(world.wireworld[j][i]+" ");
            }
            System.out.println();
        }*/
        return world;
    }

    public static void WriteToFile(String path, World world) {
        BufferedWriter writer = null;

        try{
            writer = new BufferedWriter(new FileWriter(path));
        } catch (IOException e){
            System.out.println("Błąd podczas tworzenia pliku!");
            System.exit(5);
        }
        try{
            for(String line : list){
                writer.write(line);
                writer.write("\n");
            }

        } catch (IOException e){
            System.out.println("Błąd podczas tworzenia pliku!");
            System.exit(5);
        }

        for(int i=0;i<100;++i){
            for(int j=0;j<100;++j){
                if(world.wireworld[i][j] == Cells.Case.ELECTRON_HEAD) {
                    try {
                        String line = "Electron, Head, " + i + ", " + j;
                        writer.write(line);
                        writer.write("\n");
                    } catch (IOException e) {
                        System.out.println("Błąd podczas tworzenia pliku! (ElectronH)");
                        System.exit(5);
                    }
                }
                if(world.wireworld[i][j] == Cells.Case.ELECTRON_TAIL) {
                    try {
                        String line = "Electron, Tail, " + i + ", " + j;
                        writer.write(line);
                        writer.write("\n");
                    } catch (IOException e) {
                        System.out.println("Błąd podczas tworzenia pliku! (ElectronT)");
                        System.exit(5);
                    }
                }

            }
        }
        try{
            writer.close();
        } catch (IOException e) {
            System.out.println("Błąd podczas tworzenia pliku!");
            System.exit(5);
        }

    }

}
