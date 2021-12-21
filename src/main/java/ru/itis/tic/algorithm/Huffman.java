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

    public Tree buildTree(HashMap<String, Integer> freq) {

        ArrayList<TreeNode> treeNodes = getArrayFromFreq(freq);
        Tree tree = new Tree();

        while (treeNodes.size() > 1) {
            Collections.sort(treeNodes, Collections.reverseOrder());

            TreeNode leftNode = treeNodes.get(treeNodes.size() - 1);
            TreeNode rightNode = treeNodes.get(treeNodes.size() - 2);

            TreeNode newNode = new TreeNode(
                    "",
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

    public String encode(String sourceData, HashMap<String, String> codes) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < sourceData.length();) {
            int utf8Code = sourceData.codePointAt(i);
            result.append(codes.get(sourceData.substring(i, i + Character.charCount(utf8Code))));
            i += Character.charCount(utf8Code);
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

    private ArrayList<TreeNode> getArrayFromFreq(HashMap<String, Integer> freq) {
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (String symb : freq.keySet()) {
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

        public HashMap<String, Integer> getFrequency(String text) {
            HashMap<String, Integer> freq = new HashMap<>();

            for (int i = 0; i < text.length();) {
                int utf8Code = text.codePointAt(i);
                String character = text.substring(i, i + Character.charCount(utf8Code));
                Integer count = freq.get(character);
                freq.put(character, count != null ? count + 1 : 1);
                i += Character.charCount(utf8Code);
            }

            return freq;
        }

    }

    public static class Decode {

        public Tree buildTree(HashMap<String, Integer> freq) {

            ArrayList<TreeNode> treeNodes = getArrayFromFreq(freq);
            Tree tree = new Tree();

            while (treeNodes.size() > 1) {
                Collections.sort(treeNodes, Collections.reverseOrder());

                TreeNode leftNode = treeNodes.get(treeNodes.size() - 1);
                TreeNode rightNode = treeNodes.get(treeNodes.size() - 2);

                TreeNode newNode = new TreeNode(
                        "",
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


        public String decode(String encode, HashMap<String, String> codesForCharacters) {
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

        public void writeToFile(String path, String data) {
            try(FileWriter writer = new FileWriter(path, false)) {
                writer.write(data);
                writer.flush();
            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }
        }

        public Pair<HashMap<String, Integer>, String> readFile(String path) {
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

        private Pair<HashMap<String, Integer>, String> getDataFromRawData(String rawData) {
            HashMap<String, Integer> frequencies = new HashMap<>();
            String encoded;

            String[] splitFrequenciesWithEncoded = rawData.split("-----");
            String[] rawFrequencies = splitFrequenciesWithEncoded[0]
                    .replaceAll("[}{](?!=)", "")
                    .split("(?<=,) ");

            for (int i = 0; i < rawFrequencies.length; i++) {
                String[] temp = rawFrequencies[i].split("=(?=\\d)");
                frequencies.put(
                        temp[0].replaceAll("\r\n", "\n"),
                        Integer.parseInt(temp[1].replaceAll(",", ""))
                );
            }

            encoded = splitFrequenciesWithEncoded[1];

            return new Pair<>(frequencies, encoded);
        }

        private ArrayList<TreeNode> getArrayFromFreq(HashMap<String, Integer> freq) {
            ArrayList<TreeNode> treeNodes = new ArrayList<>();
            for (String symb : freq.keySet()) {
                treeNodes.add(new TreeNode(symb, freq.get(symb)));
            }
            return treeNodes;
        }
    }
}
