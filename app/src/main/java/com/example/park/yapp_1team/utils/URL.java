package com.example.park.yapp_1team.utils;

/**
 * Created by Park on 2017-08-19.
 */

public class URL {
    public static final String NAVER_URL = "http://movie.naver.com/movie/running/current.nhn";
    public static final String NAVER_SELECT = "div.thumb a img";

    public static final String LOTTE_THEATER_MOVIE_INFO = "http://www.lottecinema.co.kr/LCWS/Ticketing/TicketingData.aspx";

    public static final String CGV_THEATER_MOVIE_INFO = "http://www.cgv.co.kr/common/showtimes/iframeTheater.aspx";
    public static final String CGV_SELECT = "div.col-times";

    public static final String MEGABOX_THEADER_URL = "http://image2.megabox.co.kr/mop/base/footer_theater.html"; // 영화관 리스트 크롤링
    public static final String MEAGBOX_THEADER_MOVIE_INFO = "http://www.megabox.co.kr/pages/theater/Theater_Schedule.jsp?";
    public static final String MEGABOX_SELECT_THEADER = ".footer_theater .wrap .clearfix dd a";
    public static final String MEGABOX_SELECT_MOVIE_INFO = ".timetable_container .movie_time_table.v2 tbody .lineheight_80";

}
