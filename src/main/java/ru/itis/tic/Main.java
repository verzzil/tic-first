package ru.itis.tic;

import ru.itis.tic.algorithm.Huffman;
import ru.itis.tic.model.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    private final static Huffman huffman = new Huffman();
    private final static Huffman.Prepare huffmanPrepare = new Huffman.Prepare();
    private final static Huffman.Decode huffmanDecode = new Huffman.Decode();

    public static void main(String[] args) {

        String readFile = huffmanPrepare.readFile("D:\\Another\\Univercity\\Тесты\\tic\\test.txt");

        HashMap<Character, Integer> symbolsFrequency = huffmanPrepare.getFrequency(readFile);

        ArrayList<TreeNode> treeNodes = huffmanPrepare.getTreeNodes(symbolsFrequency);

        TreeNode rootNode = huffman.algorithm(treeNodes);

        HashMap<Character, String> codesForCharacters = rootNode.getCodesForCharacters(symbolsFrequency);

        String encoded = huffman.encode(readFile, codesForCharacters);

        System.out.println("Размер исходной строки " + readFile.getBytes().length * 8);
        System.out.println("Размер исходной строки " + encoded.length());
        System.out.println(encoded);

        System.out.println(huffmanDecode.decode(encoded, codesForCharacters));
    }
}
