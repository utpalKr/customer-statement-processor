package com.rabobank.customer.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.List;

@AutoValue
public abstract class StatementProcessResponse {

    @Nullable
    public abstract List<ValidationResult> getValidationResults();

    public abstract String getMessage();

    public static Builder builder() {
        return new AutoValue_StatementProcessResponse.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder validationResults(final List<ValidationResult> validationResults);

        public abstract Builder message(final String message);

        public abstract StatementProcessResponse build();
    }
}
