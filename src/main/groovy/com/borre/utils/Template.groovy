package com.borre.utils

import groovy.text.SimpleTemplateEngine

class Template {
    private ClassLoader classLoader
    private SimpleTemplateEngine engine
    private final static String templatesPath = "templates"

    Template() {
        classLoader = Thread.currentThread().getContextClassLoader()
        engine = new SimpleTemplateEngine()
    }

    String render(String template) {
        return getTemplate(template)
    }

    String render(String template, Map model) {
        String content = getTemplate(template)
        Writable result = engine.createTemplate(content).make(model)
        return result.toString()
    }

    private String getTemplate(String name) {
        URL resource = classLoader.getResource("${templatesPath}/${name}.html")
        return resource?.text
    }
}
