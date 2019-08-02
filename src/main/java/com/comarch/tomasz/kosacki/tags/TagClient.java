package com.comarch.tomasz.kosacki.tags;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface TagClient {

    @RequestLine("GET ?userId={userId}&limit={limit}&skip={skip}")
    List<TagDto> getTagByUserId(@Param("userId") String userId,
                                @Param("limit") int limit,
                                @Param("skip") int skip);

    @RequestLine("GET ?userId={userId}&tagName={tagName}&limit={limit}&skip={skip}")
    List<TagEntity> getTagBy(@Param("userId") String userId,
                             @Param("tagName") String tagName,
                             @Param("limit") int limit,
                             @Param("skip") int skip);

    @RequestLine("POST /add")
    @Headers("Content-Type: application/json")
    void createTag(TagEntity newTag);
}
