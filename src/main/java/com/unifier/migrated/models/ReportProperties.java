package com.unifier.migrated.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * Created by bplies on 9/28/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//@ValidMetrics
public class ReportProperties  implements Serializable {
    @NotEmpty
    private String sheetName;

    @NotNull
    @Size(min = 1)
    private List<String> dimensions;
    private List<String> metrics;
    private Set<String> countryFilters;
    private Set<String> deviceFilters;
    private Set<String> mediaFormatFilters;
    @Valid
    private List<DimensionFilter> dimensionFilters;
    @Valid
    private List<MetricFilter> metricFilters;
    @Valid
    private List<Order> orderBy;
    private Map<String, String> fieldMapping;
    private DateBreakout dateBreakout;
    private boolean breakoutAdUnitPath;
    @NotNull
    private ReportType reportType;
    private EnumMap<ReportParameter, Object> parameters;
    private List<CustomMetric> customMetrics;

    public List<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }

    public List<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }

    public Set<String> getCountryFilters() {
        return countryFilters;
    }

    public void setCountryFilters(Set<String> countryFilters) {
        this.countryFilters = countryFilters;
    }

    public Set<String> getDeviceFilters() {
        return deviceFilters;
    }

    public void setDeviceFilters(Set<String> deviceFilters) {
        this.deviceFilters = deviceFilters;
    }

    public Set<String> getMediaFormatFilters() {
        return mediaFormatFilters;
    }

    public void setMediaFormatFilters(Set<String> mediaFormatFilters) {
        this.mediaFormatFilters = mediaFormatFilters;
    }

    public List<DimensionFilter> getDimensionFilters() {
        return dimensionFilters;
    }

    public void setDimensionFilters(List<DimensionFilter> dimensionFilters) {
        this.dimensionFilters = dimensionFilters;
    }

    public List<MetricFilter> getMetricFilters() {
        return metricFilters;
    }

    public void setMetricFilters(List<MetricFilter> metricFilters) {
        this.metricFilters = metricFilters;
    }

    public List<Order> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<Order> orderBy) {
        this.orderBy = orderBy;
    }

    public Map<String, String> getFieldMapping() {
        return fieldMapping;
    }

    public void setFieldMapping(Map<String, String> fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    public DateBreakout getDateBreakout() {
        return dateBreakout;
    }

    public void setDateBreakout(DateBreakout dateBreakout) {
        this.dateBreakout = dateBreakout;
    }

    public boolean isBreakoutAdUnitPath() {
        return breakoutAdUnitPath;
    }

    public void setBreakoutAdUnitPath(boolean breakoutAdUnitPath) {
        this.breakoutAdUnitPath = breakoutAdUnitPath;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public EnumMap<ReportParameter, Object> getParameters() {
        return parameters;
    }

    public void setParameters(EnumMap<ReportParameter, Object> parameters) {
        this.parameters = parameters;
    }

    public List<CustomMetric> getCustomMetrics() {
        return customMetrics;
    }

    public void setCustomMetrics(List<CustomMetric> customMetrics) {
        this.customMetrics = customMetrics;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DimensionFilter implements Serializable {
        @NotNull
        @NotEmpty
        private List<String> field;
        @NotNull
        private DimensionFilterOperator operator;
        @NotNull
        private List<String> value;

        public List<String> getField() {
            return field;
        }

        public void setField(List<String> field) {
            this.field = field;
        }

        public DimensionFilterOperator getOperator() {
            return operator;
        }

        public void setOperator(DimensionFilterOperator operator) {
            this.operator = operator;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }

        public enum DimensionFilterOperator {
            CONTAINS,
            NOT_CONTAINS,
            STARTS_WITH,
            EQUALS,
            NOT_EQUALS
        }

        private boolean hasField(String fieldName) {
            return field.stream().anyMatch(_fieldName -> _fieldName.equals(fieldName));
        }

        public boolean hasCampaignStatusField() {
            return hasField("campaignStatus");
        }

        private boolean hasOperator(DimensionFilterOperator dfo) {
            return operator.equals(dfo);
        }

        public boolean hasEqualsOperator() {
            return operator.equals(DimensionFilterOperator.EQUALS);
        }

        private boolean hasValue(String valueToFind) {
            return value.stream().anyMatch(_value -> _value.equals(valueToFind));
        }

        public boolean hasValueActive() {
            return hasValue("active");
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetricFilter implements Serializable {
        @NotNull
        private String field;
        @NotNull
        private MetricFilterOperator operator;
        @NotNull
        private Long value;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public MetricFilterOperator getOperator() {
            return operator;
        }

        public void setOperator(MetricFilterOperator operator) {
            this.operator = operator;
        }

        public Long getValue() {
            return value;
        }

        public void setValue(Long value) {
            this.value = value;
        }

        public enum MetricFilterOperator {
            GREATER_THAN,
            LESS_THAN
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Order implements Serializable {

        @NotNull
        private String field;
        @NotNull
        private OrderDirection direction;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public OrderDirection getDirection() {
            return direction;
        }

        public void setDirection(OrderDirection direction) {
            this.direction = direction;
        }

        public enum OrderDirection {
            ASC,
            DESC

        }
    }

    public enum ReportParameter {
        mrcAccreditedViewabilityDataOnly
    }

    public enum ReportType {
        IAS_PERFORMANCE, YOUTUBE_PERFORMANCE, YOUTUBE_BRAND_SAFETY, DEVICE_PERFORMANCE
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomMetric implements Serializable {
        private String metric;
        private Long customMetricId;

        public String getMetric() {
            return metric;
        }

        public void setMetric(String metric) {
            this.metric = metric;
        }

        public Long getCustomMetricId() {
            return customMetricId;
        }

        public void setCustomMetricId(Long customMetricId) {
            this.customMetricId = customMetricId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CustomMetric that = (CustomMetric) o;
            return metric.equals(that.metric) &&
                    customMetricId.equals(that.customMetricId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(metric, customMetricId);
        }
    }
}
