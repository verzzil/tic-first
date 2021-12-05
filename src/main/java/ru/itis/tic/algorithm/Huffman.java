package ru.itis.tic.algorithm;

import ru.itis.tic.model.TreeNode;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Huffman {

    public TreeNode algorithm(ArrayList<TreeNode> treeNodes) {
        while (treeNodes.size() > 1) {
            Collections.sort(treeNodes);
            TreeNode left = treeNodes.remove(treeNodes.size() - 1);
            TreeNode right = treeNodes.remove(treeNodes.size() - 1);

            TreeNode parent = new TreeNode(null, right.getWeight() + left.getWeight(), left, right);
            treeNodes.add(parent);
        }
        return treeNodes.get(0);
    }

    public String encode(String text, HashMap<Character, String> codes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(codes.get(text.charAt(i)));
        }
        return result.toString();
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

        public HashMap<Character, Integer> getFrequency(String text) {
            HashMap<Character, Integer> freqMap = new HashMap<>();
            for (int i = 0; i < text.length(); i++) {
                Character character = text.charAt(i);
                Integer count = freqMap.get(character);
                freqMap.put(character, count != null ? count + 1 : 1);
            }
            return freqMap;
        }

        public ArrayList<TreeNode> getTreeNodes(HashMap<Character, Integer> freqMap) {
            ArrayList<TreeNode> treeNodes = new ArrayList<>();
            for (Character character : freqMap.keySet()) {
                treeNodes.add(new TreeNode(character, freqMap.get(character)));
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
