package de.rwthaachen.webtech.rabatt.config;

import org.springframework.http.MediaType;

public class MediaTypes extends org.springframework.hateoas.MediaTypes {

    /**
     * A String equivalent of {@link MediaTypes#JSON}.
     */
    public static final String JSON_VALUE = "application/json";

    /**
     * Public constant media type for {@code application/json}.
     */
    public static final MediaType JSON = MediaType.valueOf(HAL_JSON_VALUE);

}
