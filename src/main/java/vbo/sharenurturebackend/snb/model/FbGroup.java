package vbo.sharenurturebackend.snb.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class FbGroup {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    String id;
    String name;
    int size;

    public FbGroup(String id, String name, int size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public FbGroup(String fbGroupName, int fbGroupSize) {
        this.name = fbGroupName;
        this.size = fbGroupSize;
    }

    public FbGroup() {

    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FbGroup fbGroup = (FbGroup) o;
        return size == fbGroup.size && id.equals(fbGroup.id) && name.equals(fbGroup.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, size);
    }
}
