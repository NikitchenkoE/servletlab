package com.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class PageGenerator {
    private final static String PATH_TO_PAGE = "src/main/resources/html";
    private final Map<String, Object> emptyMap = Collections.emptyMap();

    private static PageGenerator pageGenerator;
    private final Configuration configuration;

    private PageGenerator() {
        this.configuration = new Configuration(Configuration.VERSION_2_3_31);
    }

    public static PageGenerator init() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String fileName){
        log.info("find file whit name {}", fileName);
        Writer writer = new StringWriter();
        try {
            configuration.setDirectoryForTemplateLoading(new File(PATH_TO_PAGE));
            Template template = configuration.getTemplate(fileName);
            template.process(emptyMap, writer);
        } catch (IOException | TemplateException exception) {
            throw new RuntimeException(exception);
        }
        return writer.toString();
    }

    public String getPage(String fileName, Map<String, Object> data) {
        log.info("find file whit name {}", fileName);
        Writer writer = new StringWriter();
        try {
            configuration.setDirectoryForTemplateLoading(new File(PATH_TO_PAGE));
            Template template = configuration.getTemplate(fileName);
            template.process(data, writer);
        } catch (IOException | TemplateException exception) {
            throw new RuntimeException(exception);
        }
        return writer.toString();
    }


}
