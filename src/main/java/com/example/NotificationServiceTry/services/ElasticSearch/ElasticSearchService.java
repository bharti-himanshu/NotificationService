package com.example.NotificationServiceTry.services.ElasticSearch;

import com.example.NotificationServiceTry.data.dto.request.searchSms.SearchSmsReq;
import com.example.NotificationServiceTry.exceptions.InvalidScrollIdException;
import com.example.NotificationServiceTry.exceptions.NoSearchResultException;
import com.example.NotificationServiceTry.data.dto.response.searchSms.SearchSmsRes;
import com.example.NotificationServiceTry.data.entities.SmsRequest;
import com.example.NotificationServiceTry.services.SmsCrudService;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ElasticSearchService {
    @Autowired
    private RestHighLevelClient client;

    private static final Logger log = LoggerFactory.getLogger(ElasticSearchService.class);

    @Autowired
    private SmsCrudService smsCrudService;

    public void saveSmsElastic(SmsRequest sms_request) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("Id", sms_request.getId());
        jsonMap.put("phone_number", sms_request.getPhoneNumber());
        jsonMap.put("message", sms_request.getMessage());
        jsonMap.put("created_at", sms_request.getCreatedAt());
        jsonMap.put("updated_at", sms_request.getUpdatedAt());

        IndexRequest indexRequest = new IndexRequest("smselastic", "SmsElasticData")
                                        .source(jsonMap);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

        log.info(indexResponse.toString());
    }


    public SearchSmsRes getAllSms(SearchSmsReq searchSmsReq) throws IOException, NoSearchResultException {
        String prevScrollId = searchSmsReq.getScrollId();
        int limit = Integer.parseInt(searchSmsReq.getLimit());

        if(prevScrollId!=null) {
            try {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(prevScrollId);
                final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
                scrollRequest.scroll(scroll);
                SearchResponse searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);

                return fetchHelper(searchResponse);
            }
            catch(ElasticsearchException e){
                throw new InvalidScrollIdException(e);
            }
        }
        else{
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(10));
            SearchRequest searchRequest = new SearchRequest("smselastic");

            QueryBuilder match = new MatchAllQueryBuilder();
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            searchSourceBuilder.query(match)
                            .size(limit);
            searchRequest.source(searchSourceBuilder)
                            .scroll(scroll);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            return fetchHelper(searchResponse);
        }

    }


    public SearchSmsRes getSmsInterval(SearchSmsReq searchSmsReq) throws IOException, NoSearchResultException {
        String phoneNumber = searchSmsReq.getPhoneNumber();
        LocalDateTime start = searchSmsReq.getStartTime();
        LocalDateTime end = searchSmsReq.getEndTime();
        String prevScrollId = searchSmsReq.getScrollId();

        if(prevScrollId!=null) {
            try {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(prevScrollId);
                final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
                scrollRequest.scroll(scroll);
                SearchResponse searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);

                return fetchHelper(searchResponse);
            }
            catch(ElasticsearchException e){
                throw new InvalidScrollIdException(e);
            }
        }
        else{
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));

            SearchRequest searchRequest = new SearchRequest("smselastic");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("created_at");
            rangeQueryBuilder.gte(start);
            rangeQueryBuilder.lte(end);

            QueryBuilder termQuery = new TermQueryBuilder("phone_number", phoneNumber);

            QueryBuilder boolQuery = new BoolQueryBuilder().must(rangeQueryBuilder).must(termQuery);

            searchSourceBuilder.query(boolQuery);

            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            return fetchHelper(searchResponse);
        }
    }

    public SearchSmsRes getSmsContainingText(SearchSmsReq searchSmsReq) throws IOException, NoSearchResultException {

        String prevScrollId = searchSmsReq.getScrollId();
        String text = searchSmsReq.getText();

        if(prevScrollId!=null) {
            try {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(prevScrollId);
                final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
                scrollRequest.scroll(scroll);
                SearchResponse searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);


                return fetchHelper(searchResponse);
            }
            catch(ElasticsearchException e){
                throw new InvalidScrollIdException(e);
            }
        }
        else{
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
            SearchRequest searchRequest = new SearchRequest("smselastic");
            searchRequest.scroll(scroll);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            QueryBuilder queryBuilder = new MatchQueryBuilder("message", text);
            searchSourceBuilder.query(queryBuilder);

            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return fetchHelper(searchResponse);
        }
    }

    public SearchSmsRes fetchHelper(SearchResponse searchResponse) throws NoSearchResultException {
        String scrollId = searchResponse.getScrollId();
        SearchHits hits = searchResponse.getHits();

        ArrayList<Integer> smsIds = new ArrayList<>();
        SearchHit[] searchHits = hits.getHits();
        if(searchHits.length == 0){
            throw new NoSearchResultException();
        }
        for (SearchHit hit : searchHits) {
            Map<String, Object> source = hit.getSourceAsMap();
            Integer id = (Integer) source.get("Id");
            smsIds.add(id);
        }
        ArrayList<SmsRequest> smsList = smsCrudService.getAllSmsById(smsIds);

        return new SearchSmsRes(scrollId,smsList);
    }
}
