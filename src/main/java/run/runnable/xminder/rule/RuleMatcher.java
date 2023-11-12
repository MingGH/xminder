/*
 * Copyright (c) 2023 Asher
 *
 * Based on open-source software licensed under the MIT License.
 * Original license information can be found in the LICENSE file.
 * Original repository: https://github.com/diduweiwu/xminder
 */
package run.runnable.xminder.rule;

import cn.hutool.core.collection.CollUtil;
import run.runnable.xminder.vo.Node;
import run.runnable.xminder.vo.Topic;

import java.util.List;

/**
 * @author test
 */
public interface RuleMatcher {
    /**
     * 执行校验逻辑
     *
     * @param node        带校验的节点
     * @param isRecursion 是否需要递归校验该节点后面的子节点
     * @return
     */
    default boolean match(Node node, Boolean isRecursion) {
        // 不需要递归调用,那么直接返回结果
        if (!isRecursion) {
            return Boolean.TRUE;
        }

        List<? extends Topic> children = node.getTopicChildren();
        if (CollUtil.isNotEmpty(children)) {
            for (Topic child : children) {
                this.match(child);
            }
        }
        return Boolean.TRUE;
    }

    default boolean match(Node node) {
        return this.match(node, Boolean.TRUE);
    }

    /**
     * 执行构建逻辑
     */
    default void build() {
    }
}
