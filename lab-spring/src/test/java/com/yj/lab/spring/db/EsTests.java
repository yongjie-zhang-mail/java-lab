package com.yj.lab.spring.db;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTests {

    private static final String ES_ERROR_DELETE_SCROLL_ID = "Delete es scroll id '%s' failed.";

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Test
    public void testEs2() throws IOException {

        SearchSourceBuilder builder = createSearchSourceBuilder2();
        SearchResponse response = scrollSearch(builder);
        long count = response.getHits().getTotalHits().value;
    }

    private SearchSourceBuilder createSearchSourceBuilder2() {
        var pcSeriesKey = "100400000400001";
        var pcSeriesValueList = Arrays.asList(
                "00021",
                "00088"
        );
        var genderKey = "100010000200003";
        var genderValue = Collections.singletonList(
                "00002"
        );

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termsQuery(pcSeriesKey + ".value", pcSeriesValueList))
                .must(QueryBuilders.termsQuery(genderKey + ".value", genderValue));

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(boolQuery);
        builder.size(1).trackTotalHits(true);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        builder.fetchSource("cp", null);
        return builder;
    }

    @Test
    public void testEs() {

        SearchSourceBuilder builder = createSearchSourceBuilder();
        SearchResponse response = scrollSearch(builder);
        Long count = response.getHits().getTotalHits().value;

    }

    private SearchSourceBuilder createSearchSourceBuilder() {
        BoolQueryBuilder level1BoolQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder level21BoolQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder level22BoolQuery = QueryBuilders.boolQuery();

        var pcSeriesKey = "100400000400001";
        var pcSeriesValueList = Arrays.asList(
                "00021",
                "00088"
        );
        var genderKey = "100010000200003";
        var genderValue = "00002";

        level21BoolQuery.must(QueryBuilders.termsQuery(pcSeriesKey + ".value", pcSeriesValueList));
        level22BoolQuery.must(QueryBuilders.termsQuery(genderKey + ".value", genderValue));
        level1BoolQuery.must(level21BoolQuery);
        level1BoolQuery.must(level22BoolQuery);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(level1BoolQuery);
        builder.size(1).trackTotalHits(true);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        return builder;
    }

    @SneakyThrows
    private SearchResponse scrollSearch(@NonNull SearchSourceBuilder builder) {
        Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest request = new SearchRequest("gucp_profile");
        request.source(builder);
        request.scroll(scroll);

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        clearScroll(response);
        return response;
    }

    @SneakyThrows
    private void clearScroll(@NonNull SearchResponse response) {
        String scrollId = response.getScrollId();
        // 清除滚屏
        ClearScrollRequest clearRequest = new ClearScrollRequest();
        clearRequest.addScrollId(scrollId);// 也可以选择setScrollIds()将多个scrollId一起使用

        ClearScrollResponse clearResponse = restHighLevelClient.clearScroll(clearRequest,
                RequestOptions.DEFAULT);
        boolean result = clearResponse.isSucceeded();
    }

}




















































