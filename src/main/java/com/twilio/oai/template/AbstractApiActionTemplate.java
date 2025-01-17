package com.twilio.oai.template;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.openapitools.codegen.CodegenConfig;
import org.openapitools.codegen.SupportingFile;

public abstract class AbstractApiActionTemplate implements IApiActionTemplate {
    public static final String API_TEMPLATE = "api";
    public static final String VERSION_TEMPLATE = "version";

    private final Map<String, List<String>> templates = mapping();
    protected final CodegenConfig codegen;

    protected AbstractApiActionTemplate(CodegenConfig defaultCodegen) {
        this.codegen = initialise(defaultCodegen);
    }

    private CodegenConfig initialise(CodegenConfig codegen) {
        codegen.apiTemplateFiles().clear();
        codegen.apiTestTemplateFiles().clear();
        codegen.modelTestTemplateFiles().clear();
        codegen.apiDocTemplateFiles().clear();
        codegen.modelDocTemplateFiles().clear();
        return codegen;
    }

    @Override
    public void clean() {
        for (final List<String> entry : templates.values()) {
            codegen.apiTemplateFiles().remove(entry.get(0));
        }
    }

    @Override
    public void add(String template) {
        List<String> templateStrings = templates.get(template);
        codegen.apiTemplateFiles().put(templateStrings.get(0), templateStrings.get(1));
    }

    @Override
    public void addSupportVersion() {
        final List<String> templateStrings = templates.get(VERSION_TEMPLATE);
        final String apiVersionClass = codegen.additionalProperties().get("apiVersionClass").toString();

        if (apiVersionClass.startsWith("V")) {
            codegen
                .supportingFiles()
                .add(new SupportingFile(templateStrings.get(0),
                                        ".." + File.separator + getVersionFilename(apiVersionClass) +
                                            templateStrings.get(1)));
        } else {
            codegen.apiTemplateFiles().put(templateStrings.get(0), templateStrings.get(1));
        }
    }

    protected String getVersionFilename(final String apiVersionClass) {
        return apiVersionClass;
    }

    public String apiFilename(final String templateName, final String filename) {
        final List<String> templateStrings = templates.get(VERSION_TEMPLATE);
        final String apiVersionClass = codegen.additionalProperties().get("apiVersionClass").toString();

        if (apiVersionClass != null && templateName.equals(templateStrings.get(0))) {
            return codegen.apiFileFolder() + File.separator + apiVersionClass + templateStrings.get(1);
        }

        return filename;
    }

    protected abstract Map<String, List<String>> mapping();
}
