package com.twilio.oai;

import com.twilio.oai.api.ApiResources;
import com.twilio.oai.api.PythonApiResourceBuilder;
import com.twilio.oai.common.EnumConstants;
import com.twilio.oai.common.Utility;
import com.twilio.oai.resolver.IConventionMapper;
import com.twilio.oai.resolver.LanguageConventionResolver;
import com.twilio.oai.resolver.LanguageParamResolver;
import com.twilio.oai.resolver.LanguagePropertyResolver;
import com.twilio.oai.resolver.common.CodegenModelResolver;
import com.twilio.oai.resolver.python.PythonCaseResolver;
import com.twilio.oai.resource.IResourceTree;
import com.twilio.oai.resource.ResourceMap;
import com.twilio.oai.template.PythonApiActionTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.models.OpenAPI;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.languages.PythonClientCodegen;
import org.openapitools.codegen.model.ModelMap;
import org.openapitools.codegen.model.ModelsMap;
import org.openapitools.codegen.model.OperationsMap;

import static com.twilio.oai.common.ApplicationConstants.CONFIG_PYTHON_JSON_PATH;

public class TwilioPythonGenerator extends PythonClientCodegen {
    private final TwilioCodegenAdapter twilioCodegen;
    private final PythonApiActionTemplate actionTemplate = new PythonApiActionTemplate(this);
    private final IResourceTree resourceTree = new ResourceMap(new Inflector());
    private final DirectoryStructureService directoryStructureService = new DirectoryStructureService(
        additionalProperties,
        resourceTree,
        new PythonCaseResolver());
    private final List<CodegenModel> allModels = new ArrayList<>();
    private final Map<String, String> modelFormatMap = new HashMap<>();

    public TwilioPythonGenerator() {
        super();
        twilioCodegen = new TwilioCodegenAdapter(this, getName());
    }

    @Override
    public void processOpts() {
        twilioCodegen.processOpts();
    }

    @Override
    public void processOpenAPI(final OpenAPI openAPI) {
        final String domain = twilioCodegen.getDomainFromOpenAPI(openAPI);
        twilioCodegen.setDomain(StringHelper.camelize(domain, true));

        openAPI.getPaths().forEach(resourceTree::addResource);
        resourceTree.getResources().forEach(resource -> resource.updateFamily(resourceTree));

        directoryStructureService.configure(openAPI);
    }

    @Override
    public String apiFilename(final String templateName, final String tag) {
        return actionTemplate.apiFilename(templateName, super.apiFilename(templateName, tag));
    }

    @Override
    public String toApiFilename(final String name) {
        return directoryStructureService.toApiFilename(name);
    }

    @Override
    public Map<String, ModelsMap> postProcessAllModels(final Map<String, ModelsMap> allModels) {
        final Map<String, ModelsMap> results = super.postProcessAllModels(allModels);

        Utility.addModelsToLocalModelList(results, this.allModels);
        directoryStructureService.postProcessAllModels(results, modelFormatMap);

        // Return an empty collection so no model files get generated.
        return new HashMap<>();
    }

    @Override
    public OperationsMap postProcessOperationsWithModels(final OperationsMap objs, List<ModelMap> allModels) {
        final List<CodegenOperation> opList = directoryStructureService.processOperations(objs);
        objs.put("resources", generateResources(opList));
        return objs;
    }

    private ApiResources generateResources(final List<CodegenOperation> opList) {
        final IConventionMapper conventionMapper = new LanguageConventionResolver(CONFIG_PYTHON_JSON_PATH);
        final CodegenModelResolver codegenModelResolver = new CodegenModelResolver(conventionMapper,
                                                                                   modelFormatMap,
                                                                                   List.of(EnumConstants.NodeDataTypes.values()));

        return new PythonApiResourceBuilder(actionTemplate, opList, allModels, directoryStructureService)
            .updateApiPath()
            .updateTemplate()
            .updateOperations(new LanguageParamResolver(conventionMapper))
            .updateResponseModel(new LanguagePropertyResolver(conventionMapper), codegenModelResolver)
            .build();
    }

    @Override
    public String getName() {
        return EnumConstants.Generator.TWILIO_PYTHON.getValue();
    }
}
