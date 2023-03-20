package util;

import java.util.ArrayList;
import java.util.List;

public class File {

    private final String name;
    private int size;
    private final Types type;
    private final File parent;

    private int ownIndexAmongSiblings;
    private final List<File> children = new ArrayList<>();

    public File(String name, int size, Types type, File parent) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public File getParent() {
        return parent;
    }

    public List<File> getChildren() {
        return children;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setOwnIndexAmongSiblings(int index) {
        this.ownIndexAmongSiblings = index;
    }

    public boolean isFile() {
        return type == Types.FILE;
    }

    public boolean isDir() {
        return type == Types.DIR;
    }

    public void addChild(File child) {
        child.setOwnIndexAmongSiblings(this.children.size());
        children.add(child);
    }

    public File getChildByName(String searchedName) {
        return children.stream()
                .filter(file -> file.getName().equals(searchedName))
                .findFirst()
                .orElse(null);
    }

    public File leftmostChild() {
        if (isFile())
            return null;

        return children.get(0);
    }

    public File rightSibling() {
        int rightSiblingIndex = ownIndexAmongSiblings + 1;

        if (parent == null || parent.getChildren().size() == rightSiblingIndex) {
            return null;
        }

        return parent.getChildren().get(rightSiblingIndex);
    }

    public enum Types {
        DIR, FILE
    }
}
