package com.ezshop.test;

import com.ezshop.desensitize.DesensitizedField;
import com.ezshop.desensitize.util.ReflectionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassPropertyTreeNode {
    private String name;
    private String nameCHS;
    private String path;
    private String pathCHS;
    private List<ClassPropertyTreeNode> childrenNodeList;
    private Class<?> nodeType;
    private boolean desensitizeField;

    public static ClassPropertyTreeNode build(Class<?> rootNodeType) {
        ApiModel apiModel = rootNodeType.getAnnotation(ApiModel.class);
        if (apiModel == null || "".equals(apiModel.value())) {
            return null;
        }
        ClassPropertyTreeNode rootNode = new ClassPropertyTreeNode();
        rootNode.setName("ROOT");
        rootNode.setNameCHS(apiModel.value());
        rootNode.setPath("ROOT");
        rootNode.setPathCHS(apiModel.value());
        rootNode.setNodeType(rootNodeType);
        ClassPropertyTreeNode.buildChildren(rootNode);
        return rootNode;
    }

    private static void buildChildren(ClassPropertyTreeNode node) {
        List<Field> fields = ReflectionUtils.getPropertyFieldsFrom(node.getNodeType());
        if (fields != null && fields.size() > 0) {
            for (int i = 0; i < fields.size(); i++) {
                Class<?> fieldType = ReflectionUtils.getActualType(fields.get(i).getType());
                ClassPropertyTreeNode fieldNode =
                        ClassPropertyTreeNode.createChildrenNode(node, fields.get(i), fieldType);
                if (fieldNode != null) {
                    node.addChildren(fieldNode);
                    if (ReflectionUtils.isReferenceType(fieldType)) {
                        ClassPropertyTreeNode.buildChildren(fieldNode);
                    }
                }
            }
        }
    }

    private static ClassPropertyTreeNode createChildrenNode(
            ClassPropertyTreeNode parentNode, Field field, Class<?> fieldActualType) {
        ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
        if (apiModelProperty == null || "".equals(apiModelProperty.value())) {
            return null;
        }
        ClassPropertyTreeNode classPropertyTreeNode = new ClassPropertyTreeNode();
        classPropertyTreeNode.setName(field.getName());
        classPropertyTreeNode.setNameCHS(apiModelProperty.value());
        classPropertyTreeNode.setPath(parentNode.getPath() + "/" + field.getName());
        classPropertyTreeNode.setPathCHS(parentNode.getPathCHS() + "/" + apiModelProperty.value());
        classPropertyTreeNode.setNodeType(fieldActualType);

        DesensitizedField desensitizedField = field.getAnnotation(DesensitizedField.class);
        if (desensitizedField != null) {
            classPropertyTreeNode.setDesensitizeField(true);
        }
        return classPropertyTreeNode;
    }

    public void addChildren(ClassPropertyTreeNode classPropertyTreeNode) {
        if (childrenNodeList == null) {
            childrenNodeList = new ArrayList<>(8);
        }
        childrenNodeList.add(classPropertyTreeNode);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNameCHS() {
        return nameCHS;
    }

    public void setNameCHS(String nameCHS) {
        this.nameCHS = nameCHS;
    }

    public String getPathCHS() {
        return pathCHS;
    }

    public void setPathCHS(String pathCHS) {
        this.pathCHS = pathCHS;
    }

    public List<ClassPropertyTreeNode> getChildrenNodeList() {
        return childrenNodeList;
    }

    public void setChildrenNodeList(List<ClassPropertyTreeNode> childrenClassPropertyTreeNodeList) {
        this.childrenNodeList = childrenClassPropertyTreeNodeList;
    }

    public Class<?> getNodeType() {
        return nodeType;
    }

    public void setNodeType(Class<?> nodeType) {
        this.nodeType = nodeType;
    }

    public boolean getDesensitizeField() {
        return desensitizeField;
    }

    public void setDesensitizeField(boolean desensitizeField) {
        this.desensitizeField = desensitizeField;
    }
}
