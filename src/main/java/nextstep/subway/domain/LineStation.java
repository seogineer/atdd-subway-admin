package nextstep.subway.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class LineStation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long distance;

    @ManyToOne
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    protected LineStation() {
    }

    public LineStation(Long distance, Station upStation, Station downStation) {
        this(null, distance, upStation, downStation);
    }

    public LineStation(Long id, Long distance, Station upStation, Station downStation) {
        this.id = id;
        this.distance = distance;
        this.upStation = upStation;
        this.downStation = downStation;
    }

    public Long getDistance() {
        return distance;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public void updateUpStation(Long distance, Station upStation) {
        this.distance = distance;
        this.upStation = upStation;
    }

    public void updateDownStation(Long distance, Station downStation) {
        this.distance = distance;
        this.downStation = downStation;
    }

    public boolean equalsUpStation(Station station) {
        return upStation.equals(station);
    }

    public boolean equalsDownStation(Station station) {
        return downStation.equals(station);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineStation that = (LineStation) o;
        return Objects.equals(id, that.id) && Objects.equals(distance, that.distance) && Objects.equals(upStation, that.upStation) && Objects.equals(downStation, that.downStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, distance, upStation, downStation);
    }
}
