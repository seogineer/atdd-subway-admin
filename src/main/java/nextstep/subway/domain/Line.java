package nextstep.subway.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private LineName name;

    @Embedded
    private LineColor color;

    @Embedded
    private Sections stations;

    protected Line() {

    }

    public Line(String name, String color, Station upStation, Station downStation) {
        this.name = new LineName(name);
        this.color = new LineColor(color);
        this.stations = new Sections(upStation, downStation);
    }

    public void modifyName(String name) {
        this.name.modify(name);
    }

    public void modifyColor(String color) {
        this.color.modify(color);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getColor() {
        return color.getColor();
    }

    public Station getUpStation() {
        return stations.getUpStation();
    }

    public Station getDownStation() {
        return stations.getDownStation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        if (getId() != null && Objects.equals(getId(), line.getId())) return true;
        return Objects.equals(getId(), line.getId()) && Objects.equals(getName(), line.getName()) && Objects.equals(getColor(), line.getColor()) && Objects.equals(getUpStation(), line.getUpStation()) && Objects.equals(getDownStation(), line.getDownStation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getColor(), getUpStation(), getDownStation());
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
