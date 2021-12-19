package ru.itis.tic.algorithm;

import javafx.util.Pair;
import ru.itis.tic.model.Tree;
import ru.itis.tic.model.TreeNode;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Huffman {

    public Tree buildTree(HashMap<Character, Integer> freq) {

        ArrayList<TreeNode> treeNodes = getArrayFromFreq(freq);
        Tree tree = new Tree();

        while (treeNodes.size() > 1) {
            Collections.sort(treeNodes, Collections.reverseOrder());

            TreeNode leftNode = treeNodes.get(treeNodes.size() - 1);
            TreeNode rightNode = treeNodes.get(treeNodes.size() - 2);

            TreeNode newNode = new TreeNode(
                    ' ',
                    rightNode.getFrequency() + leftNode.getFrequency(),
                    leftNode,
                    rightNode
            );

            treeNodes.remove(leftNode);
            treeNodes.remove(rightNode);
            treeNodes.add(newNode);
        }

        tree.setRoot(treeNodes.get(0));

        return tree;
    }

    public String encode(String sourceData, HashMap<Character, String> codes) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < sourceData.length(); i++) {
            result.append(codes.get(sourceData.charAt(i)));
        }

        return result.toString();
    }

    public void writeToFile(String path, String encoded) {
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(encoded);
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    private ArrayList<TreeNode> getArrayFromFreq(HashMap<Character, Integer> freq) {
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (Character symb : freq.keySet()) {
            treeNodes.add(new TreeNode(symb, freq.get(symb)));
        }
        return treeNodes;
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
            HashMap<Character, Integer> freq = new HashMap<>();

            for (int i = 0; i < text.length(); i++) {
                Character character = text.charAt(i);
                Integer count = freq.get(character);
                freq.put(character, count != null ? count + 1 : 1);
            }

            return freq;
        }

    }

    public static class Decode {

        public Tree buildTree(HashMap<Character, Integer> freq) {

            ArrayList<TreeNode> treeNodes = getArrayFromFreq(freq);
            Tree tree = new Tree();

            while (treeNodes.size() > 1) {
                Collections.sort(treeNodes, Collections.reverseOrder());

                TreeNode leftNode = treeNodes.get(treeNodes.size() - 1);
                TreeNode rightNode = treeNodes.get(treeNodes.size() - 2);

                TreeNode newNode = new TreeNode(
                        ' ',
                        rightNode.getFrequency() + leftNode.getFrequency(),
                        leftNode,
                        rightNode
                );

                treeNodes.remove(leftNode);
                treeNodes.remove(rightNode);
                treeNodes.add(newNode);
            }

            tree.setRoot(treeNodes.get(0));

            return tree;
        }


        public String decode(String encode, HashMap<String, Character> codesForCharacters) {
            StringBuilder result = new StringBuilder();

            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < encode.length(); i++) {
                temp.append(encode.charAt(i));

                if (codesForCharacters.containsKey(temp.toString())) {
                    result.append(codesForCharacters.get(temp.toString()));
                    temp.setLength(0);
                }
            }

            return result.toString();
         }

        private Pair<HashMap<Character, Integer>, String> getDataFromRawData(String rawData) {
            HashMap<Character, Integer> frequencies = new HashMap<>();
            String encoded;

            String[] splitFrequenciesWithEncoded = rawData.split("-----");
            String[] rawFrequencies = splitFrequenciesWithEncoded[0]
                    .replaceAll("[}{](?!=)", "")
                    .split("(?<=,) ");

            for (int i = 0; i < rawFrequencies.length; i++) {
                String[] temp = rawFrequencies[i].split("=(?=\\d)");
                frequencies.put(
                        temp[0].replaceAll("\r\n", "\n").charAt(0),
                        Integer.parseInt(temp[1].replaceAll(",", ""))
                );
            }

            encoded = splitFrequenciesWithEncoded[1];

            return new Pair<>(frequencies, encoded);
        }

        public void writeToFile(String path, String data) {
            try(FileWriter writer = new FileWriter(path, false)) {
                writer.write(data);
                writer.flush();
            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }
        }

        public Pair<HashMap<Character, Integer>, String> readFile(String path) {
            StringBuilder result = new StringBuilder();
            try (FileReader reader = new FileReader(path)) {
                int c;
                while ((c = reader.read()) != -1) {
                    result.append((char) c);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            return getDataFromRawData(result.toString());
        }

        private ArrayList<TreeNode> getArrayFromFreq(HashMap<Character, Integer> freq) {
            ArrayList<TreeNode> treeNodes = new ArrayList<>();
            for (Character symb : freq.keySet()) {
                treeNodes.add(new TreeNode(symb, freq.get(symb)));
            }
            return treeNodes;
        }
    }
}
