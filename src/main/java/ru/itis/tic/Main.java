package ru.itis.tic;

import ru.itis.tic.algorithm.Huffman;
import ru.itis.tic.model.Tree;
import ru.itis.tic.model.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {

    private final static Huffman huffman = new Huffman();
    private final static Huffman.Prepare huffmanPrepare = new Huffman.Prepare();
    private final static Huffman.Decode huffmanDecode = new Huffman.Decode();

    public static void main(String[] args) {

        String source = huffmanPrepare.readFile("D:\\Another\\Univercity\\Тесты\\tic\\test.txt");

        ArrayList<TreeNode> freq = huffmanPrepare.getFrequency(source);

        Tree tree = huffman.algorithm(freq);

        HashMap<Character, String> codes = tree.buildCodesFromTree();

        System.out.println(codes);
    }
}
