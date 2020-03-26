package com.lifeiscoding.spring.core.type.classreading;

import com.lifeiscoding.spring.core.type.ClassMetadata;
import com.lifeiscoding.spring.util.ClassUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;


public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {

    private String className;
    private boolean isInterface;
    private boolean isAbstract;
    private boolean isFinal;
    private String superClassName;
    private String[] interfaces;

    public ClassMetadataReadingVisitor() {
        super(SpringAsmInfo.ASM_VERSION);
    }

    public boolean isInterface() {
        return isInterface;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public boolean hasSuperClass() {
        return superClassName != null;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    @Override
    public String[] getInterfaceNames() {
        return this.interfaces;
    }

    public String[] getInterfaceName() {
        return interfaces;
    }

    @Override
    public void visit(int version, int access, String name, String singnature, String supername, String[] interfaces) {
        this.className = ClassUtils.convertResourcePathToClassName(name);
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
        this.isInterface = ((access & Opcodes.ACC_ABSTRACT) != 0);
        this.isInterface = ((access & Opcodes.ACC_FINAL) != 0);
        if (supername != null) {
            this.superClassName = ClassUtils.convertResourcePathToClassName(supername);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertClassNameToResourcePath(interfaces[i]);
        }
    }

    public String getClassName() {
        return this.className;
    }
}
