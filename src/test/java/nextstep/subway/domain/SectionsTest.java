package nextstep.subway.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.exception.ImpossibleDeleteException;
import nextstep.subway.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SectionsTest extends SectionsTestFixture {

    @DisplayName("구간들 사이에 새로운 구간 등록 테스트 (상행역 일치)")
    @Test
    void addSectionIfEqualUpStation() {
        Section newSection = Section.builder(양재역, 판교역, Distance.valueOf(5))
                .build();
        sections1.addSection(newSection);
        List<String> stationNames = sections1.orderedStations().stream()
                .map(Station::name)
                .collect(Collectors.toList());
        assertThat(stationNames).containsOnly("강남역", "양재역", "판교역", "양재시민의숲역");
    }

    @DisplayName("구간들 사이에 새로운 구간 등록 테스트 (하행역 일치)")
    @Test
    void addSectionIfEqualDownStation() {
        Section newSection = Section.builder(판교역, 양재시민의숲역, Distance.valueOf(4))
                .build();
        sections1.addSection(newSection);
        List<String> stationNames = sections1.orderedStations().stream()
                .map(Station::name)
                .collect(Collectors.toList());
        assertThat(stationNames).containsOnly("강남역", "양재역", "판교역", "양재시민의숲역");
    }

    @DisplayName("구간들 길이(합) 테스트")
    @Test
    void getDistance() {
        assertThat(sections1.distance()).isEqualTo(Distance.valueOf(20));
    }

    @DisplayName("구간들에 포함된 역 정보들 조회 테스트")
    @Test
    void getStations() {
        assertThat(sections1.orderedStations()).containsOnly(강남역, 양재역, 양재시민의숲역);
    }

    @DisplayName("구간들에 있는 하행역이 일치하는 구간 사이에 구간 추가")
    @Test
    void addSectionInside() {
        Section newSection = Section.builder(판교역, 양재시민의숲역, Distance.valueOf(4))
                .build();
        sections1.addSection(newSection);
        assertThat(sections1.orderedStations()).containsOnly(강남역, 양재역, 양재시민의숲역, 판교역);
    }

    @DisplayName("구간들에 하행 종점에 구간 추가")
    @Test
    void addSectionAtDownStation() {
        Section newSection = Section.builder(양재시민의숲역, 판교역, Distance.valueOf(12))
                .build();
        sections1.addSection(newSection);
        assertThat(sections1.orderedStations()).containsOnly(강남역, 양재역, 양재시민의숲역, 판교역);
    }

    @DisplayName("구간들에 있는 하행역이 일치하는 구간 사이에 구간길이가 같거나 큰 구간 추가하면 예외 발생")
    @ParameterizedTest(name = "구간들에 있는 하행역이 일치하는 구간 사이에 {0}의 구간길이가 같거나 큰 구간 추가하면 예외 발생")
    @ValueSource(ints = {10, 12})
    void addSectionInsideByEqualOrLongerDistance(int input) {
        Section newSection = Section.builder(판교역, 양재시민의숲역, Distance.valueOf(input))
                .build();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sections1.addSection(newSection))
                .withMessage("구간 길이는 0 이하가 될 수 없습니다.");
    }

    @DisplayName("구간들에 구간 등록시 상행역과 하행역이 이미 구간들에 모두 등록되어 있는 경우 예외")
    @Test
    void addSectionContainsUpStationAndDownStation() {
        Section newSection = Section.builder(양재역, 양재시민의숲역, Distance.valueOf(3))
                .build();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sections1.addSection(newSection))
                .withMessage("이미 등록된 구간 요청입니다.");
    }

    @DisplayName("구간들에 구간 등록시 상행역과 하행역 둘 중 하나도 구간들에 포함되어있지 않으면 예외 발생")
    @Test
    void addSectionContainsNoneOfUpStationAndDownStation() {
        Section newSection = Section.builder(판교역, 미정역, Distance.valueOf(3))
                .build();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sections1.addSection(newSection))
                .withMessage("등록을 위해 필요한 상행역과 하행역이 모두 등록되어 있지 않습니다.");
    }

    @DisplayName("구간들로부터 상행종점부터 하행종점까지 정렬된 지하철역 반환 테스트")
    @Test
    void orderedStations() {
        Section newSection = Section.builder(판교역, 양재시민의숲역, Distance.valueOf(3))
                .build();
        sections1.addSection(newSection);
        assertThat(sections1.orderedStations()).containsExactly(강남역, 양재역, 판교역, 양재시민의숲역);
    }

    @DisplayName("종점(상행)을 제거하면 구간을 제거하고 다음으로 오던 역이 종점이 된다.")
    @Test
    void deleteSectionFirst() {
        sections1.deleteSection(강남역);
        assertThat(sections1.orderedStations()).containsExactly(양재역, 양재시민의숲역);
    }

    @DisplayName("중간역을 제거하면 중간역이 제거되고 재배치가 된다.")
    @Test
    void deleteSectionInSide() {
        sections1.deleteSection(양재역);
        assertThat(sections1.orderedStations()).containsExactly(강남역, 양재시민의숲역);
    }

    @DisplayName("구간들에 없는 역을 제거하면 구간 제거에 실패한다.")
    @Test
    void deleteSectionNotAdded() {
        assertThatThrownBy(() -> sections1.deleteSection(미정역))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("일치하는 상행역이 없습니다.");
    }

    @DisplayName("구간이 하나인 구간들에서 마지막 구간을 제거하면 구간 제거에 실패한다.")
    @Test
    void deleteSectionLeftAlone() {
        sections1.deleteSection(강남역);
        assertThatThrownBy(() -> sections1.deleteSection(양재시민의숲역))
                .isInstanceOf(ImpossibleDeleteException.class)
                .hasMessage("제거 가능한 구간이 없습니다.");
    }
}