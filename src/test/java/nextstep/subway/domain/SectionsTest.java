package nextstep.subway.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SectionsTest {
    public static final int DEFAULT_DISTANCE = 10;
    private Station firstStation;
    private Station lastStation;
    private Station newStation;
    private Section defaultSection;

    @BeforeEach
    void initialize(){
        firstStation = new Station("first");
        lastStation = new Station("last");
        newStation = new Station("new");
        defaultSection = new Section(firstStation, lastStation, DEFAULT_DISTANCE);
    }

    @Test
    @DisplayName("기존 존재하는 구간 사이에 새로운 구간 등록 : [상행역 - 신규역] 구간 등록")
    void 상행역_신규역_구간_등록(){
        //given
        Sections sections = new Sections(new ArrayList<>(Arrays.asList(defaultSection)));
        sections.addSection(new Section(firstStation, newStation, 5));

        //when
        List<Station> sortedStations = sections.getSortedStations();

        //then
        assertThat(sortedStations).containsExactly(firstStation, newStation, lastStation);
    }

    @Test
    @DisplayName("기존 존재하는 구간 사이에 새로운 구간 등록 : [신규역 - 하행역] 구간 등록")
    void 신규역_하행역_구간_등록(){
        //given
        Sections sections = new Sections(new ArrayList<>(Arrays.asList(defaultSection)));
        sections.addSection(new Section(newStation, lastStation, 5));

        //when
        List<Station> sortedStations = sections.getSortedStations();

        //then
        assertThat(sortedStations).containsExactly(firstStation, newStation, lastStation);
    }

    @Test
    @DisplayName("새로운 상행종점 구간 등록 : [신규역 - 상행역] 구간 등록")
    void 신규역_상행역_구간_등록(){
        //given
        Sections sections = new Sections(new ArrayList<>(Arrays.asList(defaultSection)));
        sections.addSection(new Section(newStation, firstStation, 5));

        //when
        List<Station> sortedStations = sections.getSortedStations();

        //then
        assertThat(sortedStations).containsExactly(newStation, firstStation, lastStation);
    }

    @Test
    @DisplayName("새로운 하행종점 구간 등록 : [하행역 - 신규역] 구간 등록")
    void 하행역_신규역_구간_등록(){
        //given
        Sections sections = new Sections(new ArrayList<>(Arrays.asList(defaultSection)));
        sections.addSection(new Section(lastStation, newStation, 5));

        //when
        List<Station> sortedStations = sections.getSortedStations();

        //then
        assertThat(sortedStations).containsExactly(firstStation, lastStation, newStation);
    }

    @Test
    @DisplayName("예외처리 : 역 사이에 새로운 역을 등록할 경우, 기존 역 사이 길이보다 크거나 같으면 등록을 할 수 없다")
    void Exception_Invalid_Distance(){
        //given
        Sections sections = new Sections(new ArrayList<>(Arrays.asList(defaultSection)));

        //then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> sections.addSection(new Section(firstStation, newStation, DEFAULT_DISTANCE)));
    }

    @Test
    @DisplayName("예외처리 : 상행역과 하행역이 이미 노선에 모두 등록되어 있다면 추가할 수 없다")
    void Exception_Already_Exist_Section(){
        //given
        Sections sections = new Sections(new ArrayList<>(Arrays.asList(defaultSection)));

        //then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> sections.addSection(new Section(firstStation, lastStation, 5)));

    }

    @Test
    @DisplayName("예외처리 : 상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가할 수 없다")
    void Exception_Unknown_Stations(){
        //given
        Sections sections = new Sections(new ArrayList<>(Arrays.asList(defaultSection)));
        Station newStation2 = new Station("new2");

        //then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> sections.addSection(new Section(newStation, newStation2, 5)));

    }
}