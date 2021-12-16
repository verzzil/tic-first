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

            HashMap<Character, Integer> freq = huffmanPrepare.getFrequency(source);

            Tree tree = huffman.buildTree(freq);

            HashMap<Character, String> codes = tree.buildCodesFromTree();

            String encode = huffman.encode(source, codes);

            huffman.writeToFile("./coderResult.txt", freq + "-----" + encode);
        } else if (mode == 2) {
            Pair<HashMap<Character, Integer>, String> data = huffmanDecode.readFile("./coderResult.txt");

            HashMap<Character, Integer> frequencies = data.getKey();
            String encoded = data.getValue();

            Tree tree = huffmanDecode.buildTree(frequencies);

            HashMap<String, Character> codes = tree.buildCodesFromTreeForDecode();

            String decode = huffmanDecode.decode(encoded, codes);

            huffmanDecode.writeToFile("./decoderResult.txt", decode);
        }


    }
}
