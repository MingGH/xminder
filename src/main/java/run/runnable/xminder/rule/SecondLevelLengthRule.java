/*
 * Copyright (c) 2023 Asher
 *
 * Based on open-source software licensed under the MIT License.
 * Original license information can be found in the LICENSE file.
 * Original repository: https://github.com/diduweiwu/xminder
 */
package run.runnable.xminder.rule;

import org.apache.commons.lang3.StringUtils;
import run.runnable.xminder.vo.Node;

public class SecondLevelLengthRule implements RuleMatcher {
    @Override
    public boolean match(Node node) {
        Integer level = node.getLevel();
        if (level == 2) {
            //第二层级的节点名称长度必须>=5
            assert StringUtils.length(node.getTitle()) >=5;
        }

        // 调用接口默认方法,或递归对所有子节点都执行校验
        return RuleMatcher.super.match(node);
    }
}
