package com.wildfit.server.model.mapper;


import com.wildfit.server.domain.PhotoDigest;
import com.wildfit.server.model.Photo;

public final class PhotoDigestMapper {
    private PhotoDigestMapper() {
    }

    public static PhotoDigest map(Photo photo) {
        final var builder = PhotoDigest.builder();

        if (photo != null) {
            builder.withThumb(photo.getThumb());
        }
        return builder.build();
    }

    public static PhotoDigest map(String thumbnail) {
        final var builder = PhotoDigest.builder();

        if (thumbnail != null) {
            builder.withThumb(thumbnail);
        }
        return builder.build();
    }
}
