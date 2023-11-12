/*
 * Copyright (c) 2023 Asher
 *
 * Based on open-source software licensed under the MIT License.
 * Original license information can be found in the LICENSE file.
 * Original repository: https://github.com/diduweiwu/xminder
 */
package run.runnable.xminder.parser;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TopicLabelParser {

    /**
     * 解析不同文件类型的xmind的节点label数据
     *
     * @param labelNode
     * @return
     */
    public static String parse(JsonNode labelNode) {
        if (Objects.isNull(labelNode)) {
            return StrUtil.EMPTY;
        }

        if (labelNode.isArray()) {
            return JSONUtil.parseArray(labelNode.toPrettyString()).stream().map(Objects::toString).collect(Collectors.joining(","));
        }

        JsonNode label = labelNode.get("label");
        if (labelNode.isObject() && Objects.nonNull(label)) {
            return label.toString();
        }

        return StrUtil.EMPTY;
    }
}
