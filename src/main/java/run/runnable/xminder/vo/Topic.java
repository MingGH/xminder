/*
 * Copyright (c) 2023 Asher
 *
 * Based on open-source software licensed under the MIT License.
 * Original license information can be found in the LICENSE file.
 * Original repository: https://github.com/diduweiwu/xminder
 */
package run.runnable.xminder.vo;

import cn.hutool.core.collection.CollUtil;
import run.runnable.xminder.parser.TopicChildrenParser;
import run.runnable.xminder.parser.TopicExtensionParser;
import run.runnable.xminder.parser.TopicLabelParser;
import run.runnable.xminder.parser.TopicMarkerParser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author test
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Topic implements Node {
    /**
     * 节点层级
     */
    private Integer level = 1;

    @JsonProperty("id")
    private String id;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("title")
    private String title;

    @JsonProperty(value = "children", access = JsonProperty.Access.WRITE_ONLY)
    private JsonNode children;

    @JsonProperty("labels")
    private JsonNode labels;

    @JsonProperty(value = "marker-refs", access = JsonProperty.Access.WRITE_ONLY)
    private JsonNode markerRefs;

    @JsonProperty(value = "extensions", access = JsonProperty.Access.WRITE_ONLY)
    private JsonNode extensions;

    @JsonProperty(value = "markers", access = JsonProperty.Access.WRITE_ONLY)
    private JsonNode markers;

    public List<String> getTopicMarkers() {
        return TopicMarkerParser.parse(this.markerRefs, this.markers);
    }

    public Map<String, String> getExtensions() {
        return TopicExtensionParser.parse(this.extensions);
    }

    private Integer fetchChildrenNodeLevel() {
        return this.level + 1;
    }

    public String getLabels() {
        return TopicLabelParser.parse(this.labels);
    }

    public List<? extends Topic> getCallout() {
        return TopicChildrenParser.parse(this.children, this.fetchChildrenNodeLevel(), "callout");
    }

    public List<? extends Topic> getTopicChildren() {
        return TopicChildrenParser.parse(this.children, this.fetchChildrenNodeLevel(), "attached");
    }

    /**
     * 判断节点是否拥有某类标记节点
     *
     * @param markerName 标记名称
     * @return boolean
     */
    public boolean hashMarker(String markerName) {
        return CollUtil.contains(this.getTopicMarkers(), markerName);
    }
}
