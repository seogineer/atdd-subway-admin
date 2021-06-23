package nextstep.subway.line.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import nextstep.subway.exception.LimitDistanceException;
import nextstep.subway.station.domain.Station;

@Entity
public class Section {

	public static final int MIN_DISTANCE = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "up_station_id")
	private Station upStation;

	@OneToOne
	@JoinColumn(name = "down_station_id")
	private Station downStation;

	private int distance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_id")
	private Line line;

	protected Section() {
		/* Empty */
	}

	public Section(Station upStation, Station downStation, int distance) {
		if (distance <= MIN_DISTANCE) {
			throw new LimitDistanceException(MIN_DISTANCE);
		}

		this.upStation = upStation;
		this.downStation = downStation;
		this.distance = distance;
	}

	public Long getId() {
		return id;
	}

	public Station getUpStation() {
		return upStation;
	}

	public Station getDownStation() {
		return downStation;
	}

	public int getDistance() {
		return distance;
	}

	public Line getLine() {
		return line;
	}

	public void toLine(Line line) {
		this.line = line;
	}

	public boolean containStation(Station station) {
		return upStation == station || downStation == station;
	}

	public boolean equalsUpStation(Section s) {
		return upStation == s.getUpStation();
	}

	public boolean equalsDownStation(Section s) {
		return downStation == s.getDownStation();
	}
}