package com.wildfit.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {
    private String thumb; //  "https://nix-tag-images.s3.amazonaws.com/1763_thumb.jpg"
}
