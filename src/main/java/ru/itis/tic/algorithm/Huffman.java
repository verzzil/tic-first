package ru.itis.tic.algorithm;

import ru.itis.tic.model.Tree;
import ru.itis.tic.model.TreeNode;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Huffman {

    public Tree algorithm(ArrayList<TreeNode> treeNodes) {
        Tree tree = new Tree();

        while (treeNodes.size() > 1) {
            Collections.sort(treeNodes, Collections.reverseOrder());

            TreeNode rightNode = treeNodes.get(treeNodes.size() - 1);
            TreeNode leftNode = treeNodes.get(treeNodes.size() - 2);

            TreeNode newNode = new TreeNode(
                    null,
                    rightNode.getFrequency() + leftNode.getFrequency(),
                    leftNode,
                    rightNode
            );

            treeNodes.remove(leftNode);
            treeNodes.remove(rightNode);
            treeNodes.add(newNode);
        }

//        System.out.println(treeNodes.get(0).getLeftNode().getLeftNode().getLeftNode().getLeftNode().getLeftNode().getLeftNode());
        tree.setRoot(treeNodes.get(0));

        return tree;
    }

    public static class Prepare {

        public String readFile(String path) {
            StringBuilder result = new StringBuilder();
            try (FileReader reader = new FileReader(path)) {
                int c;
                while ((c = reader.read()) != -1) {
                    result.append((char) c);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            return result.toString();
        }

        public ArrayList<TreeNode> getFrequency(String text) {
            HashMap<Character, Integer> freqMap = new HashMap<>();
            ArrayList<TreeNode> treeNodes = new ArrayList<>();

            for (int i = 0; i < text.length(); i++) {
                Character character = text.charAt(i);
                Integer count = freqMap.get(character);
                freqMap.put(character, count != null ? count + 1 : 1);
            }

            for (Character symb : freqMap.keySet()) {
                treeNodes.add(new TreeNode(symb, freqMap.get(symb)));
            }
            return treeNodes;
        }

    }

    public static class Decode {

        public String decode(String encode, HashMap<Character, String> codesForCharacters) {
            StringBuilder result = new StringBuilder();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < encode.length(); i++) {
                stringBuilder.append(encode.charAt(i));
                for (Character character : codesForCharacters.keySet()) {
                    if (codesForCharacters.get(character).equals(stringBuilder.toString())) {
                        result.append(character);
                        stringBuilder.setLength(0);
                    }
                }
            }
            return result.toString();
         }
    }
}
