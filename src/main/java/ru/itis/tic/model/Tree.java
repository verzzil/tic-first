package ru.itis.tic.model;

import java.util.HashMap;

public class Tree {

    private TreeNode root;
    private HashMap<Character, String> codes = new HashMap<>();

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public HashMap<Character, String> buildCodesFromTree() {
        getAllCodes(root, "");
        return codes;
    }

    private void getAllCodes(TreeNode node, String currentCode) {
        if (node.getLeftNode() == null && node.getRightNode() == null) {
            codes.put(node.getSymbol(), currentCode);
        } else {
            if (node.getLeftNode() != null) {
                getAllCodes(node.getLeftNode(), currentCode + "0");
            }
            if (node.getRightNode() != null) {
                getAllCodes(node.getRightNode(), currentCode + "1");
            }
        }
    }
}
