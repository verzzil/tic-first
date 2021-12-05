package ru.itis.tic.model;

import java.util.HashMap;

public class TreeNode implements Comparable<TreeNode> {

    private Character symbol;
    private Integer weight;
    private TreeNode leftNode;
    private TreeNode rightNode;

    public TreeNode(Character symbol, Integer weight) {
        this.symbol = symbol;
        this.weight = weight;
    }

    public TreeNode(Character symbol, Integer weight, TreeNode leftNode, TreeNode rightNode) {
        this.symbol = symbol;
        this.weight = weight;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    @Override
    public int compareTo(TreeNode o) {
        return o.weight - weight;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "symbol=" + symbol +
                ", weight=" + weight +
                '}';
    }

    public Character getSymbol() {
        return symbol;
    }

    public Integer getWeight() {
        return weight;
    }

    public TreeNode getLeftNode() {
        return leftNode;
    }

    public TreeNode getRightNode() {
        return rightNode;
    }

    public HashMap<Character, String> getCodesForCharacters(HashMap<Character, Integer> freq) {
        HashMap<Character, String> result = new HashMap<>();
        for (Character c : freq.keySet()) {
            result.put(c, this.deepSearchInTree(c, ""));
        }
        return result;
    }

    private String deepSearchInTree(Character character, String currentPath) {
        if (symbol == character) {
            return currentPath;
        } else {
            if (leftNode != null) {
                String path = leftNode.deepSearchInTree(character, currentPath + 0);
                if (path != null) {
                    return path;
                }
            }
            if (rightNode != null) {
                String path = rightNode.deepSearchInTree(character, currentPath + 1);
                if (path != null) {
                    return path;
                }
            }
        }
        return null;
    }
}
