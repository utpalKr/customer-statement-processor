package com.rabobank.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

@JsonDeserialize(builder = AutoValue_ErrorMessage.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AutoValue
public abstract class ErrorMessage {

    public abstract String getError();

    @Nullable
    public abstract String getErrorKey();

    public static Builder builder() {
        return new AutoValue_ErrorMessage.Builder();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder error(final String error);

        public abstract Builder errorKey(final String errorKey);

        public abstract ErrorMessage build();
    }
}
