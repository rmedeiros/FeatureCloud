package com.onekin.featurecloud.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "developer_group")
public class DeveloperGroup {

    @Id
    private Integer idDeveloperGroup;
    @Transient
    private List<Developer> developers;

    public DeveloperGroup(Integer idDeveloperGroup, List<Developer> developers) {
        super();
        this.idDeveloperGroup = idDeveloperGroup;
        this.developers = developers;
    }

    public DeveloperGroup() {
        super();
    }

    public DeveloperGroup(int id) {
        this.idDeveloperGroup = id;
    }

    public Integer getIdDeveloperGroup() {
        return idDeveloperGroup;
    }

    public void setIdDeveloperGroup(Integer idDeveloperGroup) {
        this.idDeveloperGroup = idDeveloperGroup;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idDeveloperGroup == null) ? 0 : idDeveloperGroup.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeveloperGroup other = (DeveloperGroup) obj;
        if (idDeveloperGroup == null) {
            if (other.idDeveloperGroup != null)
                return false;
        } else if (!idDeveloperGroup.equals(other.idDeveloperGroup))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DeveloperGroup [idDeveloperGroup=");
        builder.append(idDeveloperGroup);
        builder.append(", developers=");
        builder.append(developers);
        builder.append("]");
        return builder.toString();
    }

}
