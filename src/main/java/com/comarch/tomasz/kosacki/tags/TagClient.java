package com.comarch.tomasz.kosacki.tags;

import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface TagClient {

    @RequestLine("GET ?userId={userId}")
    List<TagDto> getTagBy(@Param("userId") String userId);

}
