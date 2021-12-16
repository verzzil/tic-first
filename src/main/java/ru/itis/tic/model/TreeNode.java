package ru.itis.tic.model;

public class TreeNode implements Comparable<TreeNode> {

    private Character symbol;
    private Integer frequency;
    private TreeNode leftNode;
    private TreeNode rightNode;

    public TreeNode(Character symbol, Integer frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public TreeNode(Character s, Integer f, TreeNode l, TreeNode r) {
        symbol = s;
        frequency = f;
        leftNode = l;
        rightNode = r;
    }

    @Override
    public int compareTo(TreeNode o) {
        if (frequency > o.frequency) {
            return 1;
        } else if (frequency.equals(o.frequency)) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "symbol=" + symbol +
                ", weight=" + frequency +
                '}';
    }

    public Character getSymbol() {
        return symbol;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public TreeNode getLeftNode() {
        return leftNode;
    }

    public TreeNode getRightNode() {
        return rightNode;
    }
}
