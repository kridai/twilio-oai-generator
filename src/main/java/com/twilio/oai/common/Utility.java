package com.twilio.oai.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenProperty;
import org.openapitools.codegen.model.ModelMap;
import org.openapitools.codegen.model.ModelsMap;

import static com.twilio.oai.common.ApplicationConstants.ARRAY;
import static com.twilio.oai.common.ApplicationConstants.OBJECT;

@UtilityClass
public class Utility {

    private static final int OVERFLOW_CHECKER = 32;
    public static final int BASE_SIXTEEN = 16;
    private static final int BIG_INTEGER_CONSTANT = 1;

    public void setComplexDataMapping(final List<CodegenModel> allModels, Map<String, String> modelFormatMap) {
        allModels.forEach(item -> {
            if (item.getFormat() != null && OBJECT.equalsIgnoreCase(item.getDataType())) {
                modelFormatMap.put(item.classname, item.getFormat());
            }
        });
    }

    public static Map<String, Map<String, Object>> getConventionalMap() {
        try {
            return new ObjectMapper().readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream(ApplicationConstants.CONFIG_CSHARP_JSON_PATH), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    public void addModelsToLocalModelList(final Map<String, ModelsMap> modelMap, List<CodegenModel> localModels){
        for (final ModelsMap mods : modelMap.values()) {
            final List<ModelMap> modList = mods.getModels();
            modList
                    .stream()
                    .map(ModelMap::getModel)
                    .map(CodegenModel.class::cast)
                    .collect(Collectors.toCollection(() -> localModels));
        }
    }

    public String removeEnumName(final String dataType) {
        return dataType == null ? null : dataType.replace("Enum", "");
    }

    @SuppressWarnings("unchecked")
    public List<CodegenOperation> getOperations(final Map<String, Object> resource) {
        return (ArrayList<CodegenOperation>) resource.computeIfAbsent(
                "operations",
                k -> new ArrayList<>());
    }

    public String getMd5(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger bigInteger = new BigInteger(BIG_INTEGER_CONSTANT, messageDigest);
            String hashText = bigInteger.toString(BASE_SIXTEEN);
            while (hashText.length() < OVERFLOW_CHECKER) {
                hashText = "0" + hashText;
            }
            return hashText;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String populateCrudOperations(final CodegenOperation operation) {
        final EnumConstants.Operation method = Arrays
            .stream(EnumConstants.Operation.values())
            .filter(item -> operation.operationId.toLowerCase().startsWith(item.getValue().toLowerCase()))
            .findFirst()
            .orElse(EnumConstants.Operation.READ);

        operation.vendorExtensions.put("x-is-" + method.name().toLowerCase() + "-operation", true);

        String summary = operation.notes;
        if (summary == null || summary.isEmpty()) {
            summary = method.name().toLowerCase();
        }

        operation.vendorExtensions.put("x-generate-comment", summary);

        return method.name();
    }

    public String getRecordKey(final List<CodegenModel> models, final List<CodegenOperation> codegenOperationList) {
        return codegenOperationList
            .stream()
            .filter(co -> co.operationId.toLowerCase().startsWith("list"))
            .map(co -> getModelByClassname(models, co.returnBaseType).orElse(null))
            .filter(Objects::nonNull)
            .map(CodegenModel::getAllVars)
            .flatMap(Collection::stream)
            .filter(v -> v.openApiType.equals(ARRAY))
            .map(v -> v.baseName)
            .findFirst()
            .orElse(null);
    }

    public Optional<CodegenModel> getModel(final List<CodegenModel> models,
                                           final String className,
                                           final String recordKey,
                                           final CodegenOperation codegenOperation) {
        if (recordKey != null &&
            (boolean) codegenOperation.vendorExtensions.getOrDefault("x-is-read-operation", false)) {
            return models
                .stream()
                .filter(model -> model.getClassname().equals(className))
                .map(CodegenModel::getVars)
                .flatMap(Collection::stream)
                .filter(prop -> prop.baseName.equals(recordKey))
                .map(CodegenProperty::getComplexType)
                .map(classname -> getModelByClassname(models, classname))
                .findFirst()
                .orElseThrow();
        }

        return getModelByClassname(models, className);
    }

    public Optional<CodegenModel> getModelByClassname(final List<CodegenModel> models, final String classname) {
        return models.stream().filter(model -> model.classname.equals(classname)).findFirst();
    }

    public void addModel(final List<CodegenModel> allModels, final Map<String, CodegenModel> models, final String complexType, final String dataType) {
        getModelByClassname(allModels, complexType != null ? complexType : dataType).ifPresent(model -> {
            if (models.putIfAbsent(model.getClassname(), model) == null) {
                model.getVars().forEach(property -> addModel(allModels, models, property.complexType, property.dataType));
            }
        });
    }
}
