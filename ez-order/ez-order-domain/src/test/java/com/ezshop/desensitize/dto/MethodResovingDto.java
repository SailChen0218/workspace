package com.ezshop.desensitize.dto;

import com.ezshop.desensitize.util.ClassPropertyTreeNode;

import java.io.Serializable;
import java.util.List;

public class MethodResovingDto implements Serializable {
    private static final long serialVersionUID = 1697485830340289540L;
    private ClassPropertyTreeNode treeNode;
    private List<String> parameters;

    public MethodResovingDto() {
    }

    public ClassPropertyTreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(ClassPropertyTreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "MethodResovingDto{" +
                "treeNode=" + treeNode +
                ", parameters=" + parameters +
                '}';
    }
}
