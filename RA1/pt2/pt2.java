package RA1.pt2;

import java.io.*;

public class pt2 {
    public static void main(String[] args) throws IOException {
        int clau = 3;
        try{
        BufferedReader br = new BufferedReader(new FileReader("RA1/pt2/entrada.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("RA1/pt2/xifrat.txt"));
        String linea;
        while((linea = br.readLine()) !=null){
            StringBuilder sb = new StringBuilder(linea);
            sb.reverse();
            String resultado = sb.toString();
            StringBuilder xifrat = new StringBuilder();
            for (int i = 0; i < resultado.length(); i++) {
                char c = resultado.charAt(i);
                xifrat.append( (char)(c + clau) );
            }
            System.out.println(xifrat.toString());
            bw.write(xifrat.toString());
            bw.newLine();
        }
        br.close();
        bw.close();
        desxifrar("RA1/pt2/xifrat.txt", "RA1/pt2/desxifrat.txt", clau);
    } catch (FileNotFoundException e){
        System.out.println("error: fichero no torbat"+e.getMessage());
    }catch (IOException e){
        System.out.println("error de entrada/salida" + e.getMessage());
    }
    }
    public static void desxifrar(String fitxerEntrada, String fitxerSortida, int clau) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(fitxerEntrada));
    BufferedWriter bw = new BufferedWriter(new FileWriter(fitxerSortida));
    String linea;
    
    while((linea=br.readLine()) !=null){
    StringBuilder desxifrat = new StringBuilder();

    for (int i = 0; i <linea.length(); i++) {
        char c = linea.charAt(i);
        desxifrat.append((char)(c-clau));
    }
    StringBuilder sb2 = new StringBuilder(desxifrat.toString());
    sb2.reverse();
    String original = sb2.toString();
    System.out.println(original);
    bw.write(original);
    bw.newLine();
    }
    br.close();
    bw.close();
    
    }
    
}