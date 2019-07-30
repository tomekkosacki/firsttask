package com.comarch.tomasz.kosacki.tags;

import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface TagClient {

    @RequestLine("GET /user/{id}")
    List<TagDto> getTagByUserId(@Param("id") String userId);


}
