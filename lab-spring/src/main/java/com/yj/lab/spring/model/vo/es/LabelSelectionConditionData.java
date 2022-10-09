package com.yj.lab.spring.model.vo.es;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Zhang Yongjie
 */
@Setter
@Getter
public class LabelSelectionConditionData implements Serializable {
    private static final long serialVersionUID = -1144877110236676084L;
    private Map<String, List<String>> labelCondition = Maps.newHashMap();
    private List<NameValueAndChildren> orLabelIds = Lists.newArrayList();
    private List<NameValueAndChildren> notLabelIds = Lists.newArrayList();
    private List<NameValueAndChildren> labelClick = Lists.newArrayList();
    private Long startDate;
    private Long endDate;

    public void addLabelIds(List<NameValueAndChildren> conditon) {
        if (CollectionUtils.isNotEmpty(conditon)) {
            conditon.forEach(e -> {
                String name = e.getName();
                String label = e.getValue();
                if (label.length() == 20) {
                    labelClick.add(new NameValueAndChildren(name, label));
                    addLabelValue(label);
                } else if (label.length() == 21) {
                    String remove = StringUtils.remove(label, "-");
                    e.setValue(remove);
                    labelClick.add(new NameValueAndChildren(name, remove));
                    notLabelIds.add(e);
                } else if (label.length() == 15) {
                    labelClick.add(new NameValueAndChildren(name, label));
                    labelCondition.put(label, null);
                } else if (label.length() < 15) {
                    if (StringUtils.contains(label, "-")) {
                        String remove = StringUtils.remove(label, "-");
                        e.setValue(remove);
                        labelClick.add(new NameValueAndChildren(name, remove));
                        notLabelIds.add(e);
                    } else {
                        labelClick.add(new NameValueAndChildren(name, label));
                        labelCondition.put(label, null);
                    }
                }
            });
        }
    }

    public void addLabelValue(String label) {
        String name = label.substring(0, 15);
        String value = label.substring(15, 20);
        List<String> values = labelCondition.get(name);
        if (CollectionUtils.isNotEmpty(values)) {
            values.add(label.substring(15, 20));
        } else {
            labelCondition.put(name, Lists.newArrayList(value));
        }
    }

    public boolean isEmpty() {
        return MapUtils.isEmpty(labelCondition)
                && CollectionUtils.isEmpty(orLabelIds) && CollectionUtils.isEmpty(notLabelIds);
    }

    public List<NameValueAndChildren> getLabelNameAndValue() {
        labelClick.addAll(notLabelIds);
        labelClick.addAll(orLabelIds);
        return labelClick;
    }
}
