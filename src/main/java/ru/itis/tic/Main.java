package ru.itis.tic;

import javafx.util.Pair;
import ru.itis.tic.algorithm.Huffman;
import ru.itis.tic.model.Tree;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private final static Huffman huffman = new Huffman();
    private final static Huffman.Prepare huffmanPrepare = new Huffman.Prepare();
    private final static Huffman.Decode huffmanDecode = new Huffman.Decode();

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("========== Режим работы ===========");
        System.out.println("1: Кодирование \t ----- \t  2: Декодирование");
        int mode = scan.nextInt();

        if (mode == 1) {
            System.out.println("Введите путь до файла с данными: ");
            String path = scan.next();

            String source = huffmanPrepare.readFile(path);

            HashMap<String, Integer> freq = huffmanPrepare.getFrequency(source);

            Tree tree = huffman.buildTree(freq);

            HashMap<String, String> codes = tree.buildCodesFromTree();

            String encode = huffman.encode(source, codes);

            huffman.writeToFile("./coderResult.txt", freq + "-----" + encode);

            System.out.println("Коды");

            System.out.println(codes);

            scan.nextLine();
            scan.nextLine();
        } else if (mode == 2) {
            Pair<HashMap<String, Integer>, String> data = huffmanDecode.readFile("./coderResult.txt");

            HashMap<String, Integer> frequencies = data.getKey();
            String encoded = data.getValue();

            Tree tree = huffmanDecode.buildTree(frequencies);

            HashMap<String, String> codes = tree.buildCodesFromTreeForDecode();

            String decode = huffmanDecode.decode(encoded, codes);

            huffmanDecode.writeToFile("./decoderResult.txt", decode);
        }
    }
}
