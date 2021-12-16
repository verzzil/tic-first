package ru.itis.tic.model;

import java.util.HashMap;

public class Tree {

    private TreeNode root;
    private HashMap<Character, String> codes;
    private HashMap<String, Character> codesForDecode = new HashMap<>();

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public HashMap<Character, String> buildCodesFromTree() {
        codes = new HashMap<>();
        getAllCodes(root, "");
        return codes;
    }

    public HashMap<String, Character> buildCodesFromTreeForDecode() {
        codesForDecode = new HashMap<>();
        getAllCodesForDecode(root, "");
        return codesForDecode;
    }

    private void getAllCodes(TreeNode node, String currentCode) {
        if (node.getLeftNode() == null && node.getRightNode() == null) {
            codes.put(node.getSymbol(), currentCode);
        } else {
            if (node.getLeftNode() != null) {
                getAllCodes(node.getLeftNode(), currentCode + "1");
            }
            if (node.getRightNode() != null) {
                getAllCodes(node.getRightNode(), currentCode + "0");
            }
        }
    }

    private void getAllCodesForDecode(TreeNode node, String currentCode) {
        if (node.getLeftNode() == null && node.getRightNode() == null) {
            codesForDecode.put(currentCode, node.getSymbol());
        } else {
            if (node.getLeftNode() != null) {
                getAllCodesForDecode(node.getLeftNode(), currentCode + "1");
            }
            if (node.getRightNode() != null) {
                getAllCodesForDecode(node.getRightNode(), currentCode + "0");
            }
        }
    }
}
