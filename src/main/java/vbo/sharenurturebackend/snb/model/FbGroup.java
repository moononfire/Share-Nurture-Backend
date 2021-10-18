package vbo.sharenurturebackend.snb.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
public class FbGroup {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    String id;
    String name;
    int size;

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
}
