package org.avenue1.target.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.avenue1.target.domain.enumeration.TargetTypeEnum;

/**
 * A Target.
 */
@Document(collection = "target")
public class Target implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("active")
    private Boolean active;

    @Field("start_date")
    private LocalDate startDate;

    @Field("end_date")
    private LocalDate endDate;

    @NotNull
    @Field("target_type")
    private TargetTypeEnum targetType;

    @Field("parent")
    private String parent;

    @NotNull
    @Field("instrument_type")
    private String instrumentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Target name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Target description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public Target active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Target startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Target endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TargetTypeEnum getTargetType() {
        return targetType;
    }

    public Target targetType(TargetTypeEnum targetType) {
        this.targetType = targetType;
        return this;
    }

    public void setTargetType(TargetTypeEnum targetType) {
        this.targetType = targetType;
    }

    public String getParent() {
        return parent;
    }

    public Target parent(String parent) {
        this.parent = parent;
        return this;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public Target instrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
        return this;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Target target = (Target) o;
        if (target.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), target.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Target{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + isActive() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", targetType='" + getTargetType() + "'" +
            ", parent='" + getParent() + "'" +
            ", instrumentType='" + getInstrumentType() + "'" +
            "}";
    }
}
