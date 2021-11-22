package com.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class PageGenerator {
    private final Map<String, Object> emptyMap = Collections.emptyMap();

    private static PageGenerator pageGenerator;
    private final Configuration configuration;

    private PageGenerator() {
        this.configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(true);
        configuration.setWrapUncheckedExceptions(true);
    }

    public static PageGenerator init() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    @SneakyThrows
    public String getPage(String fileName) {
        log.info("find file whit name {}", fileName);
        Writer writer = new StringWriter();
        Template template = configuration.getTemplate(fileName);
        template.process(emptyMap, writer);
        return writer.toString();
    }

    @SneakyThrows
    public String getPage(String fileName, Map<String, Object> data) {
        log.info("find file whit name {}", fileName);
        Writer writer = new StringWriter();
        Template template = configuration.getTemplate(fileName);
        template.process(data, writer);
        return writer.toString();
    }


}
