package si.fri.demo.is.core.jpa.entities.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.lang.reflect.Field;

@MappedSuperclass
public abstract class BaseEntityVersion<T> extends BaseEntity<T> {

    @Column(name = "origin_id")
    protected Integer originId;

    @Column(name = "version_order", nullable = false)
    protected Integer versionOrder;

    @Column(name = "is_latest", nullable = false)
    protected Boolean isLatest;


    @JsonIgnore
    @Override
    public void prePersist() {
        super.prePersist();
        this.isLatest = true;
        this.versionOrder = 1;
    }

    @JsonIgnore
    public void prePersist(int versionOrder) {
        super.prePersist();
        this.isLatest = true;
        this.versionOrder = versionOrder;
    }

    @JsonIgnore
    public void setAllBaseVersionPropertiesToNull() {
        originId = null;
        versionOrder = null;
        isLatest = null;
    }

    @JsonIgnore
    private boolean versionBaseSkip(Field field){
        switch (field.getName()){
            case "originId":
            case "versionOrder":
            case "isLatest":
                return true;
            default:
                return false;
        }
    }

    @JsonIgnore
    @Override
    protected boolean genericUpdateSkip(Field field){
        boolean skip = super.genericUpdateSkip(field);
        if(skip){
            return true;
        } else {
            return versionBaseSkip(field);
        }
    }

    @JsonIgnore
    @Override
    protected boolean genericPatchSkip(Field field){
        boolean skip = super.genericPatchSkip(field);
        if(skip){
            return true;
        } else {
            return versionBaseSkip(field);
        }
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public Integer getVersionOrder() {
        return versionOrder;
    }

    public void setVersionOrder(int versionOrder) {
        this.versionOrder = versionOrder;
    }

    public Boolean getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(Boolean latest) {
        isLatest = latest;
    }

    public Integer getId(){
        return id;
    }
}

