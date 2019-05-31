package com.ezddd.core.utils;

import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.annotation.EzVersion;

import java.lang.reflect.Field;
import java.util.List;

public class CommandUtil {
    public static Field getIdentifierField(List<Field> fieldList) {
        Field identifierFiled = null;
        if (fieldList.size() == 0) {
            throw new IllegalArgumentException("commandType should have identifier field.");
        } else {
            for (int i = 0; i < fieldList.size(); i++) {
                EzIdentifier ezIdentifier = fieldList.get(i).getAnnotation(EzIdentifier.class);
                if (ezIdentifier != null) {
                    if (identifierFiled != null) {
                        throw new IllegalArgumentException("command should have only one identifier field.");
                    } else {
                        identifierFiled = fieldList.get(i);
                    }
                }
            }
        }
        return identifierFiled;
    }

    public static Field getVersionField(List<Field> fieldList) {
        Field versionFiled = null;
        if (fieldList.size() == 0) {
            throw new IllegalArgumentException("commandType should have version filed field.");
        } else {
            for (int i = 0; i < fieldList.size(); i++) {
                EzVersion ezVersion = fieldList.get(i).getAnnotation(EzVersion.class);
                if (ezVersion != null) {
                    if (versionFiled != null) {
                        throw new IllegalArgumentException("command should have only one version field.");
                    } else {
                        versionFiled = fieldList.get(i);
                    }
                }
            }
        }
        return versionFiled;
    }
}
