package com.liuyang.tray.translate;

import lombok.Data;

/**
 * ollama的请求体
 *
 * @author liuyang
 * @version 1.0
 * @since 2025/07/14
 */
@Data
public class OllamaRequestBody {

    private String model = "deepseek-r1:14b";
    private String prompt = "英中翻译 ";
    private Boolean stream = false;

    public OllamaRequestBody(String prompt) {
        this.prompt = this.getPrompt()+prompt;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }
}
