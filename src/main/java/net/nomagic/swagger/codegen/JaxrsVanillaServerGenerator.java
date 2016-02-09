package net.nomagic.swagger.codegen;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.codegen.CliOption;
import io.swagger.codegen.CodegenConstants;
import io.swagger.codegen.CodegenOperation;
import io.swagger.codegen.languages.AbstractJavaJAXRSServerCodegen;
import io.swagger.models.Operation;


public class JaxrsVanillaServerGenerator extends AbstractJavaJAXRSServerCodegen {

	private static final Logger LOGGER = LoggerFactory.getLogger(JaxrsVanillaServerGenerator.class);
	
	public JaxrsVanillaServerGenerator()
	{
        super();

        sourceFolder = "src/gen/java";
        invokerPackage = "io.swagger.api";
        artifactId = "swagger-jaxrs-server";
        outputFolder = "generated-code/JavaJaxRS-Vanilla";

        modelTemplateFiles.put("model.mustache", ".java");
        apiTemplateFiles.put("api.mustache", ".java");
        apiPackage = "io.swagger.api";
        modelPackage = "io.swagger.model";

        additionalProperties.put("title", title);

        super.embeddedTemplateDir = templateDir = JAXRS_TEMPLATE_DIRECTORY_NAME + File.separator + "vanilla";

        for ( int i = 0; i < cliOptions.size(); i++ ) {
            if ( CodegenConstants.LIBRARY.equals(cliOptions.get(i).getOpt()) ) {
                cliOptions.remove(i);
                break;
            }
        }
        
        cliOptions.add(new CliOption(CodegenConstants.IMPL_FOLDER, CodegenConstants.IMPL_FOLDER_DESC));
        cliOptions.add(new CliOption("title", "a title describing the application"));
	}

	@Override
	public void processOpts()
	{
		super.processOpts();
		sourceFolder = "gen" + File.separator + "java";

		modelTemplateFiles.clear();
		modelTemplateFiles.put("entityModel.mustache", ".java");

		supportingFiles.clear();
	} 

	@Override
	public String getName()
	{
		return "jaxrs-vanilla";
	}
	

    @Override
    public void addOperationToGroup(String tag, String resourcePath, Operation operation, CodegenOperation co, Map<String, List<CodegenOperation>> operations) {
        super.addOperationToGroup(tag, resourcePath, operation, co, operations);        
        co.subresourceOperation = !co.path.isEmpty();
    }

    @Override
    public String getHelp()
    {
        return "Generates a Java plain vanilla JAXRS Server application.";
    }
    @Override
    protected String getOrGenerateOperationId(Operation operation, String path, String httpMethod) {
        String operationId = operation.getOperationId();
        if (StringUtils.isBlank(operationId)) {
            String tmpPath = path;
            tmpPath = tmpPath.replaceAll("\\{", "");
            tmpPath = tmpPath.replaceAll("\\}", "");
            String[] parts = (httpMethod + "/" + tmpPath).split("/");
            StringBuilder builder = new StringBuilder();
            if ("/".equals(tmpPath)) {
                // must be root tmpPath
                builder.append("root");
            }
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                if (part.length() > 0) {
                    if (builder.toString().length() == 0) {
                        part = Character.toLowerCase(part.charAt(0)) + part.substring(1);
                    } else {
                        part = initialCaps(part);
                    }
                    builder.append(part);
                }
            }
            operationId = builder.toString();
            LOGGER.info("generated operationId " + operationId + "\tfor Path: " + httpMethod + " " + path);
        }
        return operationId;
    }
    
}